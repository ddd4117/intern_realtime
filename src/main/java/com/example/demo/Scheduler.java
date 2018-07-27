package com.example.demo;

import com.example.demo.common.DomParser;
import com.example.demo.common.OpenAPI;
import com.example.demo.common.item.Communication;
import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.dao.NodeDao;
import com.example.demo.manager.DataManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    NodeDao nodeDao;

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;


    //ms단위

//    @Scheduled(initialDelay = 2000, fixedDelay = 60 * 1000)
//    public void DaeguTrafficScheduled() {
//        OpenAPI api = new OpenAPI();
//        /* Daegu Traffic Infomation */
//        DataManager.getInstance().setDaeguTrafficHashMap((HashMap<String, DaeguTraffic>) DomParser.getParseingList(4, api.getOPENAPI(4)));
//        /* Daegu Incident Infomation */
//        DataManager.getInstance().setIncidientHashMap((HashMap<String, DaeguIncidient>) DomParser.getParseingList(5, api.getOPENAPI(5)));
////        this.brokerMessagingTemplate.convertAndSend("/topic/greetings", temp.toJSONString());
//    }

    @Scheduled(initialDelay = 2000, fixedDelay = 1000)
    public void GPSUpdate() { // 실행될 로직 }
        if (!DataManager.getInstance().isReady_to_start()) return;
        GPS gps = DataManager.getInstance().getCurrentGPS();
        DataManager.getInstance().updateGPS();
        JSONObject js = new JSONObject();
        js.put("type", "marker_info");
        js.put("x", gps.getX());
        js.put("y", gps.getY());
        if (DataManager.getInstance().isReady_to_start()) {
            JSONArray jsonArray = new JSONArray();
            List<String> node_id_list = nodeDao.getNodeID(gps);
            ArrayList<Communication> communications = DataManager.getInstance().getCommunications();
            HashMap<String, DaeguTraffic> daeguTrafficHashMap = DataManager.getInstance().getDaeguTrafficHashMap();
            for (String str : node_id_list) {
                for (Communication communication : communications) {
                    if (communication.getEnd_node_id().equals(str) || communication.getStart_node_id().equals(str)) {
                        JSONObject object = communication.convertJSON();
                        DaeguTraffic traffic = daeguTrafficHashMap.get(communication.getRoad_section_id());
                        object.put("sectionNm", traffic.getSectionNm());
                        object.put("roadNm", traffic.getRoadNm());
                        jsonArray.add(object);
                    }
                }
            }
            js.put("close_data", jsonArray);
        }
        HashMap<String, DaeguIncidient> incidientHashMap = DataManager.getInstance().getIncidientHashMap();
        JSONArray incidient_json_array = new JSONArray();
        for (String key : incidientHashMap.keySet()) {
            DaeguIncidient incidient = incidientHashMap.get(key);
            double dis = distance(incidient.getCoordy(), incidient.getCoordx(), gps.getY(), gps.getX());
            if (dis < 1) {
                JSONObject jsonObject = incidient.convertJsonInfo();
                incidient_json_array.add(jsonObject);
            }
        }
        js.put("incident", incidient_json_array);
        System.out.println(js.toJSONString());
        this.brokerMessagingTemplate.convertAndSend("/topic/greetings", js.toString());
    }


    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}

/* 도로값 받아오기 */
//    @Scheduled(initialDelay = 2500, fixedDelay = 200000)
//    public void getAddress(){
//        String clientId = "wCf7K1RJbecDH5erbGoD";//애플리케이션 클라이언트 아이디값";
//        String clientSecret = "KkMHColpnU";//애플리케이션 클라이언트 시크릿값";
//        try {
//            GPS gps = DataManager.getInstance().getGpsdata().get(7);
//            String addr = URLEncoder.encode(String.format("%.6f,%.6f",gps.getY(), gps.getX()), "UTF-8");
//            System.out.println("addr = " + addr);
//            String apiURL = "https://openapi.naver.com/v1/map/reversegeocode?encoding=utf-8&coordType=latlng&query=" + addr; //json
//            System.out.println(apiURL);
//            //String apiURL = "https://naveropenapi.apigw.ntruss.com/map/v1/geocode.xml?query=" + addr; // xml
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("X-Naver-Client-Id", clientId);
//            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//            int responseCode = con.getResponseCode();
//            System.out.println("RESPONSE CODE : " + responseCode);
//            BufferedReader br;
//            if(responseCode==200) { // 정상 호출
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//            br.close();
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }