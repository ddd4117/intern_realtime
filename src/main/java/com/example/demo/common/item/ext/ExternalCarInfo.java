package com.example.demo.common.item.ext;


public class ExternalCarInfo {
    private double x;
    private double y;
    private String id;
    private String type;
    private String info;

    @Override
    public String toString() {
        return "ExternalCarInfo{" +
                "x=" + x +
                ", y=" + y +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
