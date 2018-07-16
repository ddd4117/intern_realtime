package com.example.demo;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class Scheduler {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

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
