package com.example.demo;

import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.manager.DataManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

@Component
public class Scheduler {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;
    int gps_idx = 0;
    //ms단위
    @Scheduled(initialDelay = 2500, fixedDelay = 200000)
    public void getAddress(){
        String clientId = "wCf7K1RJbecDH5erbGoD";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "KkMHColpnU";//애플리케이션 클라이언트 시크릿값";
        try {
            GPS gps = DataManager.getInstance().getGpsdata().get(7);
            String addr = URLEncoder.encode(String.format("%.6f,%.6f",gps.getY(), gps.getX()), "UTF-8");
            System.out.println("addr = " + addr);
            String apiURL = "https://openapi.naver.com/v1/map/reversegeocode?encoding=utf-8&coordType=latlng&query=" + addr; //json
            System.out.println(apiURL);
            //String apiURL = "https://naveropenapi.apigw.ntruss.com/map/v1/geocode.xml?query=" + addr; // xml
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            System.out.println("RESPONSE CODE : " + responseCode);
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

    }
//    @Scheduled(initialDelay = 2000, fixedDelay = 120000)
//    public void DaeguTrafficScheduled()
//    {
//        OpenAPI api = new OpenAPI();
//        /* National Traffic Infomation */
////        ArrayList<Communication> communications = (ArrayList<Communication>) DomParser.getParseingList(2, api.getOPENAPI(2));
////        for (Communication com : communications){
////            System.out.println(com.getRoad_section_id() + " " + com.getRoad_name_text());
////        }
//        /* Daegu Traffic Infomation */
//        DataManager.getInstance().setDaeguTrafficHashMap((HashMap<String, DaeguTraffic>) DomParser.getParseingList(4,api.getOPENAPI(4)));
//        HashMap<String, DaeguTraffic> trafficeHashMap = DataManager.getInstance().getDaeguTrafficHashMap();
//        for(String key : trafficeHashMap.keySet()){
//            DaeguTraffic daeguTraffic = trafficeHashMap.get(key);
//        }
//
//        /* Daegu Incident Infomation */
//        DataManager.getInstance().setIncidientHashMap((HashMap<String, DaeguIncidient>) DomParser.getParseingList(5,api.getOPENAPI(5)));
//        HashMap<String, DaeguIncidient> incidientHashMap = DataManager.getInstance().getIncidientHashMap();
//        JSONArray incidient_json_array = new JSONArray();
//        for(String key : incidientHashMap.keySet()){
//            DaeguIncidient incidient = incidientHashMap.get(key);
//            JSONObject jsonObject = incidient.convertJsonInfo();
//            incidient_json_array.add(jsonObject);
//        }
//        JSONObject temp = new JSONObject();
//        temp.put("type", "info");
//        temp.put("data", incidient_json_array.toJSONString());
//        System.out.println(temp.toJSONString());
////        this.brokerMessagingTemplate.convertAndSend("/topic/greetings", temp.toJSONString());
//    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000)
    public void GPSUpdate() { // 실행될 로직 }
        if(gps_idx >= DataManager.getInstance().getGpsdata().size()) return;
        GPS gps = DataManager.getInstance().getGpsdata().get(gps_idx++);
        JSONObject js = new JSONObject();
        js.put("type", "marker");
        js.put("x", gps.getX());
        js.put("y", gps.getY());
//        this.brokerMessagingTemplate.convertAndSend("/topic/greetings", js.toString());
    }
}
