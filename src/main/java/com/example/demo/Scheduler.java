package com.example.demo;

import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.DaeguIncidient;
import com.example.demo.common.item.DaeguTraffic;
import com.example.demo.manager.DataManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;

@Component
public class Scheduler {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    //ms단위
    @Scheduled(initialDelay = 2000, fixedDelay = 120000)
    public void getAPI()
    {
        OpenAPI api = new OpenAPI();
        DataManager.getInstance().setTrafficHashMap((HashMap<String, DaeguTraffic>) DomParser.getParseingList(4,api.getOPENAPI(4)));
        System.out.println(DataManager.getInstance().getTrafficHashMap().size());
        DataManager.getInstance().setIncidientHashMap((HashMap<String, DaeguIncidient>) DomParser.getParseingList(5,api.getOPENAPI(5)));
        System.out.println(DataManager.getInstance().getIncidientHashMap().size());
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void otherJob() { // 실행될 로직 }
        System.out.println("send job");
        JSONObject js = new JSONObject();
//        js.put("content", "1234");
        js.put("x", 35.895000);
        js.put("y", 128.609000);
        this.brokerMessagingTemplate.convertAndSend("/topic/greetings", js.toString());
    }
}
