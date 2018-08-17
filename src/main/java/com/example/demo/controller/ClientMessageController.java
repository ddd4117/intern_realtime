package com.example.demo.controller;

import com.example.demo.Message;
import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.common.item.daoclass.Node;
import com.example.demo.common.item.ext.ExternalCarInfo;
import com.example.demo.dao.NodeDao;
import com.example.demo.manager.DataManager;
import com.example.demo.tcp_connection.External_Server_Connection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ClientMessageController {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @Autowired
    private NodeDao nodeDao;

    MyThread thread;
    GPS gps = DataManager.getInstance().getGpsdata().get(0);
    @MessageMapping("/init")
    public void init(String str){
        System.out.println(str);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "init");
        jsonObject.put("x", gps.getX());
        jsonObject.put("y", gps.getY());
        System.out.println(jsonObject.toString());
        brokerMessagingTemplate.convertAndSend("/topic/greetings", jsonObject.toJSONString());
    }
    @MessageMapping("/hello")
    public void greeting(Message message) {
        System.out.println(message.toString());
        double startx, starty, endx, endy;
        startx = gps.getX();
        starty = gps.getY();

        endx = message.getX2();
        endy = message.getY2();

        double temp;
        if (startx > endx) {
            temp = startx;
            startx = endx;
            endx = temp;
        }
        if (starty > endy) {
            temp = starty;
            starty = endy;
            endy = temp;
        }
        startx -= 0.02;
        starty -= 0.02;
        endx += 0.02;
        endy += 0.02;
        for (Node node : nodeDao.getNode()){
            double x = node.getX(); double y = node.getY();
            if(x >= startx && x <= endx
                    && y >= starty && y <= endy){
                DataManager.getInstance().getInit_infomation().add(node.getNode_id());
            }
        }
        System.out.println(DataManager.getInstance().getInit_infomation().size());
        DataManager.getInstance().setStartX(startx - 0.02);
        DataManager.getInstance().setEndX(endx + 0.02);
        DataManager.getInstance().setStartY(starty - 0.02);
        DataManager.getInstance().setEndY(endy + 0.02);


        thread = new MyThread(brokerMessagingTemplate);
        thread.setDaemon(true);
        thread.start();
        DataManager.getInstance().getExternalAccident().add(new ExternalCarInfo(128.605312, 35.884001, "1234", "accident", "car"));
        DataManager.getInstance().getExternalAccident().add(new ExternalCarInfo(128.595128, 35.899675, "5678", "sudden case", "what is it"));
//        return _obj.toJSONString();
    }
}

class MyThread extends Thread {
    @Autowired
    NodeDao nodeDao;
    boolean flag = false;
    private SimpMessagingTemplate simpMessagingTemplate;
    OpenAPI api = new OpenAPI();

    public MyThread(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void run() {
        while (true) {
//            /* Start to End area Information */
            ArrayList<Communication> com = (ArrayList<Communication>) DomParser.getParseingList(2, api.getOPENAPI(2));
            DataManager.getInstance().setCommunications(com);
            /* Daegu Traffic Information */
            DataManager.getInstance().setDaeguTrafficHashMap((HashMap<String, DaeguTraffic>) DomParser.getParseingList(4, api.getOPENAPI(4)));
            /* Daegu Incident Information */
            DataManager.getInstance().setIncidientHashMap((HashMap<String, DaeguIncidient>) DomParser.getParseingList(5, api.getOPENAPI(5)));


            if (flag == false) {
                DataManager.getInstance().setReady_to_start(true);
                flag = true;
            }

            HashMap<String, DaeguTraffic> hashMap = DataManager.getInstance().getDaeguTrafficHashMap();
            JSONArray jsonArray = new JSONArray();

//            /* only using daegu information */
//            for (String str : DataManager.getInstance().getInit_infomation()) {
//                if (hashMap.containsKey(str)) {
//                    DaeguTraffic traffic = hashMap.get(str);
//                    jsonArray.add(traffic.convertJsonInfo());
//                }
//            }

            /* using daegu information and its */
            for (Communication c : com) {
                JSONObject obj = c.convertJSON();
                if (hashMap.containsKey(c.getRoad_section_id())) {
                    DaeguTraffic traffic = hashMap.get(c.getRoad_section_id());
//                    System.out.println(traffic.toString());
//                    System.out.println(c.toString());
                    obj.put("sectionInfoCd",traffic.getSectionInfoCd());
                    obj.put("sectionNm", traffic.getSectionNm());
                    obj.put("roadNm", traffic.getRoadNm());
                }
                jsonArray.add(obj);
            }
            JSONObject _obj = new JSONObject();
            _obj.put("type", "info");
            _obj.put("total_data", jsonArray);
            System.out.println(_obj.toString());
            simpMessagingTemplate.convertAndSend("/topic/greetings", _obj.toString());
            try {
                Thread.sleep(60 * 1000);
                System.out.println("==========Data Gathering==========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}