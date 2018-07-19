package com.example.demo.common;

import com.example.demo.common.item.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DomParser {
    public static Object getParseingList(int flag, InputStream is)
    {
        switch(flag){
            case 1:
                return process_Contruction(is);
            case 2:
                return process_Communication(is);
            case 3:
                return process_Incidient(is);
            case 4:
                return process_DaeguTraffic(is);
            case 5:
                return process_DaeguIncidient(is);
        }
        return null;
    }

    public static ArrayList<Construction> process_Contruction(InputStream is){
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        System.out.println("DOMPARSER START");

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = getDocument(is, documentBuilderFactory, document);

        ArrayList<Construction> constructionArrayList = new ArrayList<>();
        NodeList item = document.getElementsByTagName("data");
        System.out.println(item.getLength());
        for(int idx = 0 ; idx < item.getLength(); idx++){
            Construction cons = new Construction();
            Node node = item.item(idx);
            Element element = (Element) node;
            cons.setLanes_block_type(element.getElementsByTagName("lanesblocktype").item(0).getTextContent());
            cons.setCoord_x(Double.parseDouble(element.getElementsByTagName("coordx").item(0).getTextContent()));
            cons.setCoord_y(Double.parseDouble(element.getElementsByTagName("coordy").item(0).getTextContent()));
            cons.setEvent_status_msg(element.getElementsByTagName("eventstatusmsg").item(0).getTextContent());
            cons.setEvent_end_time(element.getElementsByTagName("eventendday").item(0).getTextContent());
            cons.setEvent_start_time(element.getElementsByTagName("eventstartday").item(0).getTextContent());
            constructionArrayList.add(cons);
        }
        return constructionArrayList;
    }

    public static ArrayList<Communication> process_Communication(InputStream is){
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        System.out.println("DOMPARSER START");

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = getDocument(is, documentBuilderFactory, document);

        ArrayList<Communication> communicationArrayList = new ArrayList<>();
        NodeList item = document.getElementsByTagName("data");
        System.out.println(item.getLength());
        for(int idx = 0 ; idx < item.getLength(); idx++){
            Communication communication = new Communication();
            Node node = item.item(idx);
            Element element = (Element) node;
            communication.setRoad_section_id(element.getElementsByTagName("roadsectionid").item(0).getTextContent());
            communication.setAvg_speed(Integer.parseInt(element.getElementsByTagName("avgspeed").item(0).getTextContent()));
            communication.setStart_node_id(element.getElementsByTagName("startnodeid").item(0).getTextContent());
            communication.setRoad_name_text(element.getElementsByTagName("roadnametext").item(0).getTextContent());
            communication.setTravle_time(element.getElementsByTagName("traveltime").item(0).getTextContent());
            communication.setEnd_node_id(element.getElementsByTagName("endnodeid").item(0).getTextContent());
            communication.setGenerate_date(element.getElementsByTagName("generatedate").item(0).getTextContent());
            communicationArrayList.add(communication);
        }
        return communicationArrayList;
    }

    public static ArrayList<Incidient> process_Incidient(InputStream is){
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        System.out.println("DOMPARSER START");

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = getDocument(is, documentBuilderFactory, document);

        ArrayList<Incidient> incidientArrayList = new ArrayList<>();
        NodeList item = document.getElementsByTagName("data");
        System.out.println(item.getLength());
        for(int idx = 0 ; idx < item.getLength(); idx++){
            Incidient incidient = new Incidient();
            Node node = item.item(idx);
            Element element = (Element) node;
            incidient.setExpected_detour_msg(element.getElementsByTagName("expecteddetourmsg").item(0).getTextContent());
            incidient.setEvent_direction(element.getElementsByTagName("eventdirection").item(0).getTextContent());
            incidient.setIncident_duration(element.getElementsByTagName("incidentduration").item(0).getTextContent());
            incidient.setIncident_msg(element.getElementsByTagName("incidentmsg").item(0).getTextContent());
            incidient.setLanes_block_type(element.getElementsByTagName("lanesblocktype").item(0).getTextContent());
            incidient.setIncident_type(element.getElementsByTagName("incidenttype").item(0).getTextContent());
            incidient.setIncident_type(element.getElementsByTagName("ex").item(0).getTextContent());
            incidient.setExpected_cnt(Integer.parseInt(element.getElementsByTagName("expectedcnt").item(0).getTextContent()));
            incidient.setCoord_y(Double.parseDouble(element.getElementsByTagName("coordy").item(0).getTextContent()));
            incidientArrayList.add(incidient);
        }
        return incidientArrayList;
    }

    public static HashMap<String, DaeguTraffic> process_DaeguTraffic(InputStream is){
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        System.out.println("DOMPARSER START");

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = getDocument(is, documentBuilderFactory, document);

        HashMap<String, DaeguTraffic> daeguTraffics = new HashMap<>();
        NodeList item = document.getElementsByTagName("item");
        System.out.println(item.getLength());
        for(int idx = 0 ; idx < item.getLength(); idx++){
            DaeguTraffic daeguTraffic = new DaeguTraffic();
            Node node = item.item(idx);
            Element element = (Element) node;
            daeguTraffic.setAtmsTm(element.getElementsByTagName("atmsTm").item(0).getTextContent());
            daeguTraffic.setDist(Double.parseDouble(element.getElementsByTagName("dist").item(0).getTextContent()));
            daeguTraffic.setDsrcLinkSn(element.getElementsByTagName("dsrcLinkSn").item(0).getTextContent());
            daeguTraffic.setEndFacNm(element.getElementsByTagName("endFacNm").item(0).getTextContent());
            daeguTraffic.setLinkSpeed(Integer.parseInt(element.getElementsByTagName("linkSpeed").item(0).getTextContent()));
            daeguTraffic.setLinkTime(Double.parseDouble(element.getElementsByTagName("linkTime").item(0).getTextContent()));
            daeguTraffic.setRoadNm(element.getElementsByTagName("roadNm").item(0).getTextContent());
            daeguTraffic.setSectionInfoCd(element.getElementsByTagName("sectionInfoCd").item(0).getTextContent());
            daeguTraffic.setSectionNm(element.getElementsByTagName("sectionNm").item(0).getTextContent());
            daeguTraffic.setStartFacNm(element.getElementsByTagName("startFacNm").item(0).getTextContent());
            String key = element.getElementsByTagName("stdLinkId").item(0).getTextContent();
            daeguTraffic.setStdLinkId(key);
            if(daeguTraffics.keySet().contains(key)){
                System.out.println(key);
            }
            daeguTraffics.put(key, daeguTraffic);
        }
        return daeguTraffics;
    }

    public static HashMap<String, DaeguIncidient> process_DaeguIncidient(InputStream is){
        DocumentBuilderFactory documentBuilderFactory;
        DocumentBuilder documentBuilder;
        Document document = null;
        System.out.println("DOMPARSER START");

        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = getDocument(is, documentBuilderFactory, document);

        HashMap<String, DaeguIncidient> daeguIncidients = new HashMap<>();
        NodeList item = document.getElementsByTagName("item");
        System.out.println(item.getLength());
        for(int idx = 0 ; idx < item.getLength(); idx++){
            DaeguIncidient daeguIncidient = new DaeguIncidient();
            Node node = item.item(idx);
            Element element = (Element) node;
            daeguIncidient.setCoordx(Double.parseDouble(element.getElementsByTagName("coordx").item(0).getTextContent()));
            daeguIncidient.setCoordy(Double.parseDouble(element.getElementsByTagName("coordy").item(0).getTextContent()));
            daeguIncidient.setEnddate(element.getElementsByTagName("enddate").item(0).getTextContent());
            daeguIncidient.setIncidientcode(Integer.parseInt(element.getElementsByTagName("incidentcode").item(0).getTextContent()));

            daeguIncidient.setIncidentsubcode(Integer.parseInt(element.getElementsByTagName("incidentsubcode").item(0).getTextContent()));
            daeguIncidient.setIncidenttitle(element.getElementsByTagName("incidenttitle").item(0).getTextContent());
            daeguIncidient.setLinkid(element.getElementsByTagName("linkid").item(0).getTextContent());
            daeguIncidient.setLocation(element.getElementsByTagName("location").item(0).getTextContent());
            daeguIncidient.setLogdate(element.getElementsByTagName("logdate").item(0).getTextContent());
            daeguIncidient.setReportdate(element.getElementsByTagName("reportdate").item(0).getTextContent());
            daeguIncidient.setStartdate(element.getElementsByTagName("startdate").item(0).getTextContent());
            daeguIncidient.setTrafficgrade(element.getElementsByTagName("trafficgrade").item(0).getTextContent());
            daeguIncidient.setTroublegrade(Integer.parseInt(element.getElementsByTagName("troublegrade").item(0).getTextContent()));
            String key = element.getElementsByTagName("incidentid").item(0).getTextContent();
            daeguIncidient.setIncidentid(key);

            if(daeguIncidients.keySet().contains(key)){
                System.out.println(key);
            }
            daeguIncidients.put(key, daeguIncidient);
        }
        return daeguIncidients;
    }

    private static Document getDocument(InputStream is, DocumentBuilderFactory documentBuilderFactory, Document document) {
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(is);
            document.getDocumentElement().normalize();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }
}
