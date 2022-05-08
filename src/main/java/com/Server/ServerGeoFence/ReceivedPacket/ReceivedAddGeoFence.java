package com.Server.ServerGeoFence.ReceivedPacket;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ReceivedAddGeoFence extends BaseReceivedMsg{
    private List<Point> points;

    public ReceivedAddGeoFence(@JsonProperty("points") Point []points){
        this.points = new ArrayList<>();
        for(int i = 0;i < points.length;i++){
            this.points.add(new Point(points[i]));
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
