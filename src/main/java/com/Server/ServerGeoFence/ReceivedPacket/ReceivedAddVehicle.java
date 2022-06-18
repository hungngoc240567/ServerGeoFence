package com.Server.ServerGeoFence.ReceivedPacket;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceivedAddVehicle extends BaseReceivedMsg{
    private Point vehiclePoint;
    private double vx = 0;
    private double vy = 0;
    private String type;

    public ReceivedAddVehicle(@JsonProperty("point") Point vehiclePoint, @JsonProperty("vx") double vx, @JsonProperty("vy") double vy, @JsonProperty("type") String type) {
        this.vx = vx;
        this.vy = vy;
        this.type = type;
        this.vehiclePoint = vehiclePoint;
    }

    public double getVx() {
        return vx;
    }

    public String getType() {
        return type;
    }

    public double getVy() {
        return vy;
    }

    public Point getVehiclePoint() {
        return vehiclePoint;
    }
}
