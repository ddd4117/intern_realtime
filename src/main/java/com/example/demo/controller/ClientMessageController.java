package com.example.demo.controller;

import com.example.demo.Message;
import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.dao.NodeDao;
import com.example.demo.manager.DataManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ClientMessageController {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;
    MyThread thread;

    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    public void greeting(Message message) {
        System.out.println(message.toString());
        double startx, starty, endx, endy;
        startx = 128.63974000;
        starty = 35.86604833;
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
        DataManager.getInstance().setStartX(startx);
        DataManager.getInstance().setEndX(endx);
        DataManager.getInstance().setStartY(starty);
        DataManager.getInstance().setEndY(endy);
        thread = new MyThread(brokerMessagingTemplate);
        thread.setDaemon(true);
        thread.start();
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
            /* Start to End area Information */
            ArrayList<Communication> com = (ArrayList<Communication>) DomParser.getParseingList(2, api.getOPENAPI(2));
            /* Daegu Traffic Information */
            DataManager.getInstance().setDaeguTrafficHashMap((HashMap<String, DaeguTraffic>) DomParser.getParseingList(4, api.getOPENAPI(4)));
            /* Daegu Incident Information */
            DataManager.getInstance().setIncidientHashMap((HashMap<String, DaeguIncidient>) DomParser.getParseingList(5, api.getOPENAPI(5)));

            DataManager.getInstance().setCommunications(com);
            if (flag == false) {
                DataManager.getInstance().setReady_to_start(true);
                flag = true;
            }

            HashMap<String, DaeguTraffic> hashMap = DataManager.getInstance().getDaeguTrafficHashMap();
            JSONArray jsonArray = new JSONArray();
            for (Communication c : com) {
                JSONObject obj = c.convertJSON();
                if (hashMap.containsKey(c.getRoad_section_id())) {
                    DaeguTraffic traffic = hashMap.get(c.getRoad_section_id());
//                    System.out.println(traffic.toString());
//                    System.out.println(c.toString());
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
                System.out.println("Thread Sleep");
                Thread.sleep(60 * 1000);
                System.out.println("Thread unSleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}