package com.example.demo;

import com.example.demo.common.item.Communication;
import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.common.item.ext.ExternalCarInfo;
import com.example.demo.dao.NodeDao;
import com.example.demo.manager.DataManager;
import com.google.gson.Gson;
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

    /* repeat this method per 1 second */
    @Scheduled(initialDelay = 2000, fixedDelay = 2000)
    public void GPSUpdate() { // 실행될 로직 }
        if (!DataManager.getInstance().isReady_to_start()) return;
        GPS gps = DataManager.getInstance().getCurrentGPS();
        DataManager.getInstance().updateGPS();
        JSONObject js = new JSONObject();
        js.put("type", "marker_info");
        js.put("x", gps.getX());
        js.put("y", gps.getY());

        /* get closed node data */
        if (DataManager.getInstance().isReady_to_start()) {
            JSONArray jsonArray = new JSONArray();
            List<String> node_id_list = nodeDao.getNodeID(gps);
            ArrayList<Communication> communications = DataManager.getInstance().getCommunications();

            HashMap<String, DaeguTraffic> daeguTrafficHashMap = DataManager.getInstance().getDaeguTrafficHashMap();
//            for (String id : node_id_list) {
//                for (String init_id : DataManager.getInstance().getInit_infomation()) {
//                    if(id.equals(init_id) && daeguTrafficHashMap.containsKey(id)){
//
//                        DaeguTraffic traffic = daeguTrafficHashMap.get(id);
//                        jsonArray.add(traffic.convertJsonInfo());
//                    }
//                }
//            }


            for (String str : node_id_list) {
                for (Communication communication : communications) {
                    if (communication.getEnd_node_id().equals(str) || communication.getStart_node_id().equals(str)) {
                        JSONObject object = communication.convertJSON();

                        DaeguTraffic traffic = daeguTrafficHashMap.get(communication.getRoad_section_id());
                        if (traffic != null) {
//                            jsonArray.add(traffic.convertJsonInfo())
                            object.put("sectionNm", traffic.getSectionNm());
                            object.put("roadNm", traffic.getRoadNm());
                            object.put("sectionInfoCd",traffic.getSectionInfoCd());
                            jsonArray.add(object);
                        }
                    }
                }
            }
            js.put("close_data", jsonArray);
        }

        /* get Incident data */
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

        /* get external information */
        Gson gson = new Gson();
        JSONArray ext_array = new JSONArray();
        for (ExternalCarInfo ext : DataManager.getInstance().getExternalAccident()) {
            double dis = distance(ext.getY(), ext.getX(), gps.getY(), gps.getX());
            if (dis < 1) {
                ext_array.add(ext.convertJsonInfo());
            }
        }
        for (ExternalCarInfo ext : DataManager.getInstance().getExternalSuddenCase()) {
            double dis = distance(ext.getY(), ext.getX(), gps.getY(), gps.getX());
            if (dis < 1) {
                ext_array.add(ext.convertJsonInfo());
            }
        }
        js.put("ext_information", ext_array);
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