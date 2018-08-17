package com.example.demo.common;

import com.example.demo.common.item.GPS;
import com.example.demo.manager.DataManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

public class OpenAPI {
    final static String auth_key = "1531382613171"; // 1531382610296
    private static final String USER_AGENT = "Mozilla/5.0";
    private HttpURLConnection con;
    private String protocol = "GET";

    public InputStream getOPENAPI(int flag) {
        // 공사정보
        GPS gps = DataManager.getInstance().getCurrentGPS();
        // x y 바뀜
        double minY = gps.getX() - 0.01;
        double maxY = gps.getX() + 0.01;
        double minX = gps.getY() - 0.01;
        double maxX = gps.getY() + 0.01;
        String.format("%.6f", minY);
        String Address = "";
        switch (flag) {
            case 1:
                Address = "http://openapi.its.go.kr/api/NEventIdentity?key=" + auth_key + "%20&ReqType=2&MinX=" + minX + "&MaxX=" + maxX + "%20&MinY=" + minY + "&MaxY=" + maxY + "&type=its";
                break;
            case 2:
                Address = "http://openapi.its.go.kr/api/NTrafficInfo?key=" + auth_key + "&ReqType=2&MinX=" + String.format("%.6f", DataManager.getInstance().getStartX() -0.01) + "&MaxX="
                        + String.format("%.6f", DataManager.getInstance().getEndX() + 0.01) + "%20&MinY=" + String.format("%.6f", DataManager.getInstance().getStartY() - 0.01) + "&MaxY=" + String.format("%.6f", DataManager.getInstance().getEndY()+ 0.01);
                System.out.println(Address);
                break;
            case 3:
                Address = "http://openapi.its.go.kr/api/NIncidentIdentity?key=" + auth_key + "&ReqType=1&MinX=127.100000&MaxX=128.890000&MinY=34.100000%20&MaxY=39.100000&type=its";
                break;
            case 4:
                Address = "http://car.daegu.go.kr/openapi-data/service/rest/data/linkspeed?ServiceKey=VXjcyNgiB3jT3u1GwQo76CIXSIbi%2Fty4NiDr7mJB%2BQa6CpeYEJ70oapwcE5ZAS982tW%2BibrU1weq672NUwWFZQ%3D%3D&numOfRows=1400";
                break;
            case 5:
                Address = "http://car.daegu.go.kr/openapi-data/service/rest/data2/dgincident?serviceKey=VXjcyNgiB3jT3u1GwQo76CIXSIbi%2Fty4NiDr7mJB%2BQa6CpeYEJ70oapwcE5ZAS982tW%2BibrU1weq672NUwWFZQ%3D%3D&numOfRows=100&pageSize=100&pageNo=1&startPage=1";
                break;
            case 6:
                System.out.println("Exception");
                return null;
        }
        while (true) {
            try {
                URL Url = new URL(Address);
                con = (HttpURLConnection) Url.openConnection();
                con.setConnectTimeout(2000);
                con.setReadTimeout(5000);
                con.setRequestMethod(protocol);
                con.setRequestProperty("User-Agent", USER_AGENT);
                InputStream is = con.getInputStream();
                return is;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
