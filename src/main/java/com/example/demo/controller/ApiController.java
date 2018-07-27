package com.example.demo.controller;


import com.example.demo.dao.NodeDao;
import com.example.demo.manager.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {
    @Autowired
    NodeDao nodeDao;

    @RequestMapping("/main")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("main");
        nodeDao.getNodeID(DataManager.getInstance().getCurrentGPS());
//        OpenAPI api = new OpenAPI();
//        InputStream is = api.getOPENAPI(2);
//        ArrayList<Construction> consArray = (ArrayList<Construction>) DomParser.getParseingList(2,is);
//        ArrayList<Communication> commArray = (ArrayList<Communication>) DomParser.getParseingList(2, is);
//        for (Communication c : commArray){
//            System.out.println(c.toString());
//        }
//        for (int i = 4; i <= 4; i++) {
//            InputStream is = api.getOPENAPI(i);
//            switch (i) {
//                case 1:
//                    ArrayList<Construction> consArray = (ArrayList<Construction>) DomParser.getParseingList(1, is);
//                    for (Construction c : consArray) {
//                        System.out.println(c.toString());
//                    }
//                    break;
//                case 2:
//                    ArrayList<Communication> commArray = (ArrayList<Communication>) DomParser.getParseingList(2, is);
//                    for (Communication c : commArray) {
//                        System.out.println(c.toString());
//                    }
//                    break;
//                case 3:
//                    ArrayList<Incidient> inciArray = (ArrayList<Incidient>) DomParser.getParseingList(3, is);
//                    for (Incidient inci : inciArray) {
//                        System.out.println(inci.toString());
//                    }
//                    break;
//                case 4:
//                    ArrayList<DaeguTraffic> daeguTraffics = (ArrayList<DaeguTraffic>) DomParser.getParseingList(4,is);
//                    for (DaeguTraffic dt : daeguTraffics)
//                        System.out.println(dt.toString());
//            }
//        }
        return mav;
    }
}
