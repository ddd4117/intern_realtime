package com.example.demo.controller;

import com.example.demo.Greeting;
import com.example.demo.Message;
import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.manager.DataManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ClientMessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(Message message) throws Exception {
        Thread.sleep(1); // simulated delay
        System.out.println(message.toString());
        double startx, starty, endx, endy;
        startx = message.getX1();
        starty = message.getY1();
        endx = message.getX2();
        endy = message.getY2();
        double temp;
        if(startx > endx) {temp = startx; startx = endx; endx = temp;}
        if(starty > endy) {temp = starty; starty = endy; endy = temp;}
        DataManager.getInstance().setStartX(startx);
        DataManager.getInstance().setEndX(endx);
        DataManager.getInstance().setStartY(starty);
        DataManager.getInstance().setEndY(endy);
        OpenAPI api = new OpenAPI();
        ArrayList<Communication> com = (ArrayList<Communication>) DomParser.getParseingList(2,api.getOPENAPI(2));
        HashMap<String, DaeguTraffic> hashMap = DataManager.getInstance().getDaeguTrafficHashMap();
        int i = 0;
        JSONArray jsonArray = new JSONArray();
        for (Communication c: com){
            JSONObject obj = c.convertJSON();

            if(hashMap.containsKey(c.getRoad_section_id())){
                DaeguTraffic traffic = hashMap.get(c.getRoad_section_id());
                System.out.println("===key exist " + i++ + " " + "===");
                System.out.println(traffic.toString());
                System.out.println(c.toString());
                obj.put("sectionNm", traffic.getSectionNm());
                obj.put("roadNm", traffic.getRoadNm());
            }
            else{
                System.out.println("===key not exist " + i++ + " " + "===");
                System.out.println(c.toString());
            }
            jsonArray.add(obj);
        }
        JSONObject _obj = new JSONObject();
        _obj.put("type", "info");
        _obj.put("data", jsonArray);
        System.out.println(_obj.toString());
        return _obj.toJSONString();
    }
}