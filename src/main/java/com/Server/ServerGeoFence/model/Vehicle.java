package com.Server.ServerGeoFence.model;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class Vehicle {
    private final UUID id;
    private final String type;
    private Point curPoint;
    private double vx;
    private double vy;
    private long lastTimeSave = 0;
    private List<UUID> listIdGeoFenceIn = new ArrayList<>();

    public Vehicle(UUID id, String type, Point curPoint, double vx, double vy) {
        this.id = id;
        this.vx = vx;//
        this.vy = vy;//
        this.type = type;
        this.curPoint = curPoint;
        this.lastTimeSave = (new Date()).getTime();
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

    public double getVy() {
        return vy;
    }

    public double getVx() {
        return vx;
    }

    public long getLastTimeSave() {
        return lastTimeSave;
    }

    public void saveVehicleToDB(){
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        try {
            javaConnect2SQL.insertB1ToDB(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVehicleToDB(){
        long curTime = (new Date()).getTime();
        if (curTime - lastTimeSave >= 5000){
            JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
            try {
                javaConnect2SQL.insertB2ToDB(this);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
