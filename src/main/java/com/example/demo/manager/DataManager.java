package com.example.demo.manager;

import com.example.demo.common.item.DaeguIncidient;
import com.example.demo.common.item.DaeguTraffic;

import java.util.HashMap;

public class DataManager {
    private static DataManager ourInstance;
    private HashMap<String, DaeguTraffic> trafficHashMap;
    private HashMap<String, DaeguIncidient>  incidientHashMap;
    private DataManager() {
        trafficHashMap = new HashMap<>();
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            synchronized (DataManager.class) {
                if (ourInstance == null) {
                    ourInstance = new DataManager();
                }
            }
        }
        return ourInstance;
    }

    public HashMap<String, DaeguTraffic> getTrafficHashMap() {
        return trafficHashMap;
    }

    public void setTrafficHashMap(HashMap<String, DaeguTraffic> trafficHashMap) {
        this.trafficHashMap = trafficHashMap;
    }

    public HashMap<String, DaeguIncidient> getIncidientHashMap() {
        return incidientHashMap;
    }

    public void setIncidientHashMap(HashMap<String, DaeguIncidient> incidientHashMap) {
        this.incidientHashMap = incidientHashMap;
    }
}
