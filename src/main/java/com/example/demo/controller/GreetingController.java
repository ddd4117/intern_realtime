package com.example.demo.controller;

import com.example.demo.Greeting;
import com.example.demo.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1); // simulated delay
        System.out.println("When it starts");

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}