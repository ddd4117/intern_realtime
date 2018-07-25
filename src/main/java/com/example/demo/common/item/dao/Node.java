package com.example.demo.common.item.dao;

public class Node {
    private String node_id;
    private double x;
    private double y;
    private int node_type;
    private String node_name;
    private int stnl_reg;

    @Override
    public String toString() {
        return "Node{" +
                "node_id='" + node_id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", node_type=" + node_type +
                ", node_name='" + node_name + '\'' +
                ", stnl_reg=" + stnl_reg +
                '}';
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
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

    public int getNode_type() {
        return node_type;
    }

    public void setNode_type(int node_type) {
        this.node_type = node_type;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getStnl_reg() {
        return stnl_reg;
    }

    public void setStnl_reg(int stnl_reg) {
        this.stnl_reg = stnl_reg;
    }
}
