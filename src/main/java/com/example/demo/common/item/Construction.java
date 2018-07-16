package com.example.demo.common.item;

public class Construction {
    private double coord_y, coord_x; //위도 // 경도
    private String event_start_time, event_end_time; // 시작일시 / 종료일시
    private String event_status_msg; // 이벤트 메시지(내용)
    private String lanes_blocked;  // 공사로 인해 차단된 차로 수
    private String lanes_block_type; //공사로 인한 차로 차단방법
    private String type; //공사유형 return : its:국도 / ex:고속도로
    private String event_id; //공사 고유 식별번호
    private int expected_cnt; //우회정보개수
    private String expected_detour_msg; //우회정보 메시지

    public Construction() {
        coord_x = 0.0;
        coord_y = 0.0;
        event_start_time="";
        event_end_time="";
        event_status_msg="";
        lanes_blocked="";
        lanes_block_type="";
        type="";
        event_id="";
        expected_cnt=0;
        expected_detour_msg="";
    }

    @Override
    public String toString() {
        return "Construction{" +
                "coord_y=" + coord_y +
                ", coord_x=" + coord_x +
                ", event_start_time='" + event_start_time + '\'' +
                ", event_end_time='" + event_end_time + '\'' +
                ", event_status_msg='" + event_status_msg + '\'' +
                ", lanes_blocked='" + lanes_blocked + '\'' +
                ", lanes_block_type='" + lanes_block_type + '\'' +
                ", type='" + type + '\'' +
                ", event_id='" + event_id + '\'' +
                ", expected_cnt=" + expected_cnt +
                ", expected_detour_msg='" + expected_detour_msg + '\'' +
                '}';
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

    public String getEvent_start_time() {
        return event_start_time;
    }

    public void setEvent_start_time(String event_start_time) {
        this.event_start_time = event_start_time;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    public void setEvent_end_time(String event_end_time) {
        this.event_end_time = event_end_time;
    }

    public String getEvent_status_msg() {
        return event_status_msg;
    }

    public void setEvent_status_msg(String event_status_msg) {
        this.event_status_msg = event_status_msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
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

    public String getLanes_blocked() {
        return lanes_blocked;
    }

    public void setLanes_blocked(String lanes_blocked) {
        this.lanes_blocked = lanes_blocked;
    }

    public String getLanes_block_type() {
        return lanes_block_type;
    }

    public void setLanes_block_type(String lanes_block_type) {
        this.lanes_block_type = lanes_block_type;
    }
}
