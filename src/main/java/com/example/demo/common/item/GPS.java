package com.example.demo.common.item;

public class GPS {
    double x,y;

    public GPS() {
    }
    public GPS(String str){
        String tmp[] = str.split(" ");
        this.y = Double.parseDouble(tmp[0]);
        this.x = Double.parseDouble(tmp[1]);
    }

    public GPS(double x, double y) {
        this.x = x;
        this.y = y;
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
}
