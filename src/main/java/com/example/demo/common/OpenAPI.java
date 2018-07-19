package com.example.demo.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class OpenAPI {
    final static String auth_key ="1531382613171"; // 1531382610296
    private static final String USER_AGENT = "Mozilla/5.0";
    private HttpURLConnection con;
    private String protocol = "GET";

    public InputStream getOPENAPI(int flag)
    {
        // 공사정보
        String Address =  "";
        switch (flag){
            case 1:
                Address = "http://openapi.its.go.kr/api/NEventIdentity?key="+auth_key+"%20&ReqType=2&MinX=127.100000&MaxX=128.890000%20&MinY=34.100000&MaxY=39.100000&type=ex";
                break;
            case 2:
                Address = "http://openapi.its.go.kr/api/NTrafficInfo?key="+auth_key+"&ReqType=2&MinX=126.800000&MaxX=127.890000&MinY=34.900000%20&MaxY=35.100000";
                break;
            case 3:
                Address = "http://openapi.its.go.kr/api/NIncidentIdentity?key="+auth_key+"&ReqType=1&MinX=127.100000&MaxX=128.890000&MinY=34.100000%20&MaxY=39.100000&type=its";
                break;
            case 4:
                Address = "http://car.daegu.go.kr/openapi-data/service/rest/data/linkspeed?ServiceKey=VXjcyNgiB3jT3u1GwQo76CIXSIbi%2Fty4NiDr7mJB%2BQa6CpeYEJ70oapwcE5ZAS982tW%2BibrU1weq672NUwWFZQ%3D%3D&numOfRows=1400";
                break;
            case 5 :
                Address = "http://car.daegu.go.kr/openapi-data/service/rest/data2/dgincident?serviceKey=VXjcyNgiB3jT3u1GwQo76CIXSIbi%2Fty4NiDr7mJB%2BQa6CpeYEJ70oapwcE5ZAS982tW%2BibrU1weq672NUwWFZQ%3D%3D&numOfRows=100&pageSize=100&pageNo=1&startPage=1";
                break;
            case 6:
                System.out.println("Exception");
                return null;
        }
        try {
            URL Url = new URL(Address);
            con = (HttpURLConnection)Url.openConnection();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
