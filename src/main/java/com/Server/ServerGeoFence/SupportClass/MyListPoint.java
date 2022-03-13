package com.Server.ServerGeoFence.SupportClass;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

public class MyListPoint {
    private List<Point> points;

    public MyListPoint(@JsonProperty("points") Point[] points) {
        this.points = new ArrayList<>();
        for(int i = 0;i < points.length;i++){
            this.points.add(points[i]);
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
