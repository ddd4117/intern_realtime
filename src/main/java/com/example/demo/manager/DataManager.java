package com.example.demo.manager;

import com.example.demo.common.item.Communication;
import com.example.demo.common.item.GPS;
import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.daegu_info.DaeguTraffic;
import com.example.demo.common.item.ext.ExternalCarInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
    /* instance */
    private static DataManager ourInstance;

    /* gps info*/
    private int gps_idx = 0;
    private ArrayList<GPS> gpsdata;

    /* Hashmap of DaeguTraffic, DaeguIncident */
    private HashMap<String, DaeguTraffic> daeguTrafficHashMap;
    private HashMap<String, DaeguIncidient> incidientHashMap;
    private ArrayList<Communication> communications;

    /* Start and End coord */
    private double startX, startY, endX, endY;
    private boolean ready_to_start = false;

    /* External Car Informataion */
    private ArrayList<ExternalCarInfo> externalCarInfoArrayLIst;

    private DataManager() {
        daeguTrafficHashMap = new HashMap<>();
        incidientHashMap = new HashMap<>();
        gpsdata = new ArrayList<>();
        externalCarInfoArrayLIst = new ArrayList<>();
    }

    public void updateGPS() {
        this.gps_idx++;
    }

    public GPS getCurrentGPS() {
        if (gps_idx >= gpsdata.size()) return this.gpsdata.get(gpsdata.size() - 1);
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

    public ArrayList<Communication> getCommunications() {
        return communications;
    }

    public void setCommunications(ArrayList<Communication> communications) {
        this.communications = communications;
    }

    public boolean isReady_to_start() {
        return ready_to_start;
    }

    public void setReady_to_start(boolean ready_to_start) {
        this.ready_to_start = ready_to_start;
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

    public ArrayList<ExternalCarInfo> getExternalCarInfoArrayLIst() {
        return externalCarInfoArrayLIst;
    }

    public void setExternalCarInfoArrayLIst(ArrayList<ExternalCarInfo> externalCarInfoArrayLIst) {
        this.externalCarInfoArrayLIst = externalCarInfoArrayLIst;
    }
}
