package com.Server.ServerGeoFence.ReceivedPacket;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceivedSelectGeoFence extends BaseReceivedMsg{
    Point point;

    public ReceivedSelectGeoFence(@JsonProperty("point") Point point){
        this.point = new Point(point);
    }

    public Point getPoint() {
        return point;
    }
}
