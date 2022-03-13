package com.Server.ServerGeoFence.model;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vehicle {
    private final UUID id;
    private final String type;
    private Point curPoint;
    private List<UUID> listIdGeoFenceIn = new ArrayList<>();

    public Vehicle(@JsonProperty("id") UUID id, @JsonProperty("type") String type, @JsonProperty("point") Point curPoint) {
        this.id = id;
        this.type = type;
        this.curPoint = curPoint;
    }

    public void setListIdGeoFenceIn(List<UUID> listIdGeoFenceIn) {
        this.listIdGeoFenceIn = listIdGeoFenceIn;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Point getCurPoint(){
        return this.curPoint;
    }

    public void setCurPoint(Point curPoint) {
        this.curPoint = curPoint;
    }

    public List<UUID> getListIdGeoFenceIn() {
        return listIdGeoFenceIn;
    }
}
