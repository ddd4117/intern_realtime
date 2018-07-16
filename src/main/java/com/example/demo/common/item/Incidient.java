package com.example.demo.common.item;

public class Incidient {
    String event_direction;
    String incident_duration;
    String incident_msg;
    String lanes_block_type;
    String incident_type;
    double coord_y, coord_x;
    int expected_cnt;
    String expected_detour_msg;

    public Incidient() {
        event_direction = "";
        incident_duration = "";
        incident_msg = "";
        lanes_block_type = "";
        incident_type = "";
        coord_x = 0.0;
        coord_y = 0.0;
        expected_cnt = 0;
        expected_detour_msg = "";
    }

    @Override
    public String toString() {
        return "Incidient{" +
                "event_direction='" + event_direction + '\'' +
                ", incident_duration='" + incident_duration + '\'' +
                ", incident_msg='" + incident_msg + '\'' +
                ", lanes_block_type='" + lanes_block_type + '\'' +
                ", incident_type='" + incident_type + '\'' +
                ", coord_y=" + coord_y +
                ", coord_x=" + coord_x +
                ", expected_cnt=" + expected_cnt +
                ", expected_detour_msg='" + expected_detour_msg + '\'' +
                '}';
    }

    public String getEvent_direction() {
        return event_direction;
    }

    public void setEvent_direction(String event_direction) {
        this.event_direction = event_direction;
    }

    public String getIncident_duration() {
        return incident_duration;
    }

    public void setIncident_duration(String incident_duration) {
        this.incident_duration = incident_duration;
    }

    public String getIncident_msg() {
        return incident_msg;
    }

    public void setIncident_msg(String incident_msg) {
        this.incident_msg = incident_msg;
    }

    public String getLanes_block_type() {
        return lanes_block_type;
    }

    public void setLanes_block_type(String lanes_block_type) {
        this.lanes_block_type = lanes_block_type;
    }

    public String getIncident_type() {
        return incident_type;
    }

    public void setIncident_type(String incident_type) {
        this.incident_type = incident_type;
    }

    public double getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(double coord_y) {
        this.coord_y = coord_y;
    }

    public double getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(double coord_x) {
        this.coord_x = coord_x;
    }

    public int getExpected_cnt() {
        return expected_cnt;
    }

    public void setExpected_cnt(int expected_cnt) {
        this.expected_cnt = expected_cnt;
    }

    public String getExpected_detour_msg() {
        return expected_detour_msg;
    }

    public void setExpected_detour_msg(String expected_detour_msg) {
        this.expected_detour_msg = expected_detour_msg;
    }
}
