package com.example.demo.controller;

import com.example.demo.Greeting;
import com.example.demo.Message;
import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.manager.DataManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {
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
        for (Communication c: com){
            System.out.println(c.toString());
        }
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getText()) + "!");
    }
}