package com.Server.ServerGeoFence.SupportClass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {
    private double x;
    private double y;

    public Point(@JsonProperty("x") double x,@JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point getMiddlePoint(Point p2){
        double middleX = (this.x + p2.getX()) / 2;
        double middleY = (this.y + p2.getY()) / 2;
        return new Point(middleX, middleY);
    }

    public double getEuclid(Point p2){
        double dx = this.x - p2.getX();
        double dy = this.y - p2.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    public void swap(Point p2){
        double x = p2.getX();
        double y = p2.getY();
        p2.setX(this.x);
        p2.setY(this.y);
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
