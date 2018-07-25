package com.example.demo.common.item.daegu_info;

import org.json.simple.JSONObject;

public class DaeguTraffic {
    private String atmsTm = ""; //정보발생일시
    private double dist = 0.0; // 구간거리
    private String dsrcLinkSn = ""; // 쓸모없음
    private String endFacNm = ""; //도착지점명
    private int linkSpeed = 0; // 링크속도
    private double linkTime = 0; // 링크통행시간
    private String roadNm = ""; //가로명
    private String sectionInfoCd = ""; // 01:소통원활, 02:서행, 03:정체
    private String sectionNm = ""; // 구간명
    private String startFacNm = ""; // 시작지점명
    private String stdLinkId = ""; // 표준링크ID

    public JSONObject convertJsonInfo()
    {
        JSONObject obj = new JSONObject();
        obj.put("roadNm", roadNm);
        obj.put("sectionInfoCd", sectionInfoCd);
        obj.put("sectionNm", sectionNm);
        obj.put("linkSpeed", linkSpeed);
        obj.put("linkTime", linkTime);
        return obj;
    }

    @Override
    public String toString() {
        return "DaeguTraffic{" +
                "atmsTm='" + atmsTm + '\'' +
                ", dist=" + dist +
                ", dsrcLinkSn='" + dsrcLinkSn + '\'' +
                ", endFacNm='" + endFacNm + '\'' +
                ", linkSpeed=" + linkSpeed +
                ", linkTime=" + linkTime +
                ", roadNm='" + roadNm + '\'' +
                ", sectionInfoCd='" + sectionInfoCd + '\'' +
                ", sectionNm='" + sectionNm + '\'' +
                ", startFacNm='" + startFacNm + '\'' +
                ", stdLinkId='" + stdLinkId + '\'' +
                '}';
    }

    public String getAtmsTm() {
        return atmsTm;
    }

    public void setAtmsTm(String atmsTm) {
        this.atmsTm = atmsTm;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getDsrcLinkSn() {
        return dsrcLinkSn;
    }

    public void setDsrcLinkSn(String dsrcLinkSn) {
        this.dsrcLinkSn = dsrcLinkSn;
    }

    public String getEndFacNm() {
        return endFacNm;
    }

    public void setEndFacNm(String endFacNm) {
        this.endFacNm = endFacNm;
    }

    public int getLinkSpeed() {
        return linkSpeed;
    }

    public void setLinkSpeed(int linkSpeed) {
        this.linkSpeed = linkSpeed;
    }

    public double getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(double linkTime) {
        this.linkTime = linkTime;
    }

    public String getRoadNm() {
        return roadNm;
    }

    public void setRoadNm(String roadNm) {
        this.roadNm = roadNm;
    }

    public String getSectionInfoCd() {
        return sectionInfoCd;
    }

    public void setSectionInfoCd(String sectionInfoCd) {
        this.sectionInfoCd = sectionInfoCd;
    }

    public String getSectionNm() {
        return sectionNm;
    }

    public void setSectionNm(String sectionNm) {
        this.sectionNm = sectionNm;
    }

    public String getStartFacNm() {
        return startFacNm;
    }

    public void setStartFacNm(String startFacNm) {
        this.startFacNm = startFacNm;
    }

    public String getStdLinkId() {
        return stdLinkId;
    }

    public void setStdLinkId(String stdLinkId) {
        this.stdLinkId = stdLinkId;
    }
}
