package com.Server.ServerGeoFence.ReceivedPacket;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReceivedUpdateGeoFence extends BaseReceivedMsg{
    private List<Point> listPoint;
    private UUID id;
    public ReceivedUpdateGeoFence(@JsonProperty("id") UUID id, @JsonProperty("listPoint") Point[] listPoint){
        this.id = id;
        this.listPoint = new ArrayList();
        for(int i = 0;i < listPoint.length;i++){
            this.listPoint.add(new Point(listPoint[i]));
        }
    }

    public List<Point> getListPoint() {
        return listPoint;
    }

    public UUID getId() {
        return id;
    }
}
