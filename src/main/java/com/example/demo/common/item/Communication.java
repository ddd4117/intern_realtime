package com.example.demo.common.item;

public class Communication {
    String road_section_id;
    int avg_speed;
    String road_name_text;
    String start_node_id;
    String end_node_id;
    String travle_time;
    String generate_date;

    public Communication() {
        this.road_section_id = "";
        this.avg_speed = 0;
        this.road_name_text = "";
        this.start_node_id = "";
        this.end_node_id = "";
        this.travle_time = "";
        this.generate_date = "";
    }

    @Override
    public String toString() {
        return "Communication{" +
                "road_section_id='" + road_section_id + '\'' +
                ", avg_speed=" + avg_speed +
                ", road_name_text='" + road_name_text + '\'' +
                ", start_node_id='" + start_node_id + '\'' +
                ", end_node_id='" + end_node_id + '\'' +
                ", travle_time='" + travle_time + '\'' +
                ", generate_date='" + generate_date + '\'' +
                '}';
    }

    public String getRoad_section_id() {
        return road_section_id;
    }

    public void setRoad_section_id(String road_section_id) {
        this.road_section_id = road_section_id;
    }

    public int getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(int avg_speed) {
        this.avg_speed = avg_speed;
    }

    public String getRoad_name_text() {
        return road_name_text;
    }

    public void setRoad_name_text(String road_name_text) {
        this.road_name_text = road_name_text;
    }

    public String getStart_node_id() {
        return start_node_id;
    }

    public void setStart_node_id(String start_node_id) {
        this.start_node_id = start_node_id;
    }

    public String getEnd_node_id() {
        return end_node_id;
    }

    public void setEnd_node_id(String end_node_id) {
        this.end_node_id = end_node_id;
    }

    public String getTravle_time() {
        return travle_time;
    }

    public void setTravle_time(String travle_time) {
        this.travle_time = travle_time;
    }

    public String getGenerate_date() {
        return generate_date;
    }

    public void setGenerate_date(String generate_date) {
        this.generate_date = generate_date;
    }
}
