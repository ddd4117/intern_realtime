package com.example.demo.manager;

import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
    private int gps_idx = 0;
    private static DataManager ourInstance;
    private ArrayList<GPS> gpsdata;
    private HashMap<String, DaeguTraffic> daeguTrafficHashMap;
    private HashMap<String, DaeguIncidient> incidientHashMap;
    private double startX, startY, endX, endY;
    private DataManager() {
        daeguTrafficHashMap = new HashMap<>();
        incidientHashMap = new HashMap<>();
        gpsdata = new ArrayList<>();
    }

    public GPS getCurrentGPS() {
        if(gps_idx >= gpsdata.size()) return this.gpsdata.get(gpsdata.size()-1);
        GPS gps = this.gpsdata.get(gps_idx);
        return gps;
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

    public HashMap<String, DaeguIncidient> getIncidientHashMap() {
        return incidientHashMap;
    }

    public void setIncidientHashMap(HashMap<String, DaeguIncidient> incidientHashMap) {
        this.incidientHashMap = incidientHashMap;
    }

    public ArrayList<GPS> getGpsdata() {
        return gpsdata;
    }

    public void setGpsdata(ArrayList<GPS> gpsdata) {
        this.gpsdata = gpsdata;
    }

    public int getGps_idx() {
        return gps_idx;
    }

    public void setGps_idx(int gps_idx) {
        this.gps_idx = gps_idx;
    }

    public static DataManager getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(DataManager ourInstance) {
        DataManager.ourInstance = ourInstance;
    }

    public HashMap<String, DaeguTraffic> getDaeguTrafficHashMap() {
        return daeguTrafficHashMap;
    }

    public void setDaeguTrafficHashMap(HashMap<String, DaeguTraffic> daeguTrafficHashMap) {
        this.daeguTrafficHashMap = daeguTrafficHashMap;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
}
