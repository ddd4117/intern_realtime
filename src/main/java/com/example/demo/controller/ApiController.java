package com.example.demo.controller;


import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.common.item.Construction;
import com.example.demo.common.item.Incidient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class ApiController {
    @RequestMapping("/main")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("main");
        OpenAPI api = new OpenAPI();
//        InputStream is = api.getOPENAPI(2);
//        ArrayList<Construction> consArray = (ArrayList<Construction>) DomParser.getParseingList(2,is);
//        ArrayList<Communication> commArray = (ArrayList<Communication>) DomParser.getParseingList(2, is);
//        for (Communication c : commArray){
//            System.out.println(c.toString());
//        }
        for (int i = 1; i <= 3; i++) {
            InputStream is = api.getOPENAPI(i);
            switch (i) {
                case 1:
                    ArrayList<Construction> consArray = (ArrayList<Construction>) DomParser.getParseingList(1, is);
                    for (Construction c : consArray) {
                        System.out.println(c.toString());
                    }
                    break;
                case 2:
                    ArrayList<Communication> commArray = (ArrayList<Communication>) DomParser.getParseingList(2, is);
                    for (Communication c : commArray) {
                        System.out.println(c.toString());
                    }
                    break;
                case 3:
                    ArrayList<Incidient> inciArray = (ArrayList<Incidient>) DomParser.getParseingList(3, is);
                    for (Incidient inci : inciArray) {
                        System.out.println(inci.toString());
                    }
                    break;
            }
        }
        return mav;
    }
}
