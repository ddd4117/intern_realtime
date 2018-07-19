package com.example.demo.common.item;

public class DaeguIncidient {
    private double coordx = 0.0;
    private double coordy = 0.0;
    private String enddate = "";
    private int incidientcode = 0;
    private String incidentid ="";
    private int incidentsubcode = 0;
    private String incidenttitle ="";
    private String linkid = "";
    private String location = "";
    private String logdate = "";
    private String reportdate = "";
    private String startdate = "";
    private String trafficgrade = "";
    private int troublegrade = 0;

    @Override
    public String toString() {
        return "DaeguIncidient{" +
                "coordx=" + coordx +
                ", coordy=" + coordy +
                ", enddate='" + enddate + '\'' +
                ", incidientcode=" + incidientcode +
                ", incidentid='" + incidentid + '\'' +
                ", incidentsubcode=" + incidentsubcode +
                ", incidenttitle='" + incidenttitle + '\'' +
                ", linkid='" + linkid + '\'' +
                ", location='" + location + '\'' +
                ", logdate='" + logdate + '\'' +
                ", reportdate='" + reportdate + '\'' +
                ", startdate='" + startdate + '\'' +
                ", trafficgrade='" + trafficgrade + '\'' +
                ", troublegrade=" + troublegrade +
                '}';
    }

    public double getCoordx() {
        return coordx;
    }

    public void setCoordx(double coordx) {
        this.coordx = coordx;
    }

    public double getCoordy() {
        return coordy;
    }

    public void setCoordy(double coordy) {
        this.coordy = coordy;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getIncidientcode() {
        return incidientcode;
    }

    public void setIncidientcode(int incidientcode) {
        this.incidientcode = incidientcode;
    }

    public String getIncidentid() {
        return incidentid;
    }

    public void setIncidentid(String incidentid) {
        this.incidentid = incidentid;
    }

    public int getIncidentsubcode() {
        return incidentsubcode;
    }

    public void setIncidentsubcode(int incidentsubcode) {
        this.incidentsubcode = incidentsubcode;
    }

    public String getIncidenttitle() {
        return incidenttitle;
    }

    public void setIncidenttitle(String incidenttitle) {
        this.incidenttitle = incidenttitle;
    }

    public String getLinkid() {
        return linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getTrafficgrade() {
        return trafficgrade;
    }

    public void setTrafficgrade(String trafficgrade) {
        this.trafficgrade = trafficgrade;
    }

    public int getTroublegrade() {
        return troublegrade;
    }

    public void setTroublegrade(int troublegrade) {
        this.troublegrade = troublegrade;
    }
}