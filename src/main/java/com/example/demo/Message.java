package com.example.demo;

public class Message {

    private String text;
    double x1;
    double x2;
    double y1;
    double y2;
    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", x1=" + x1 +
                ", y1=" + y2 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    public Message(String text, double x1, double y1, double x2, double y2) {
        this.text = text;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}