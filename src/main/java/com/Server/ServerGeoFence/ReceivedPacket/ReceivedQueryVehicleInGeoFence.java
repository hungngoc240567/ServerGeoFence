package com.Server.ServerGeoFence.ReceivedPacket;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ReceivedQueryVehicleInGeoFence extends BaseReceivedMsg{
    private long startTime;
    private long endTime;
    private UUID idGeoFence;

    ReceivedQueryVehicleInGeoFence(@JsonProperty("id") UUID idGeoFence, @JsonProperty("startTime") long startTime, @JsonProperty("endTime") long endTime){
        this.idGeoFence = idGeoFence;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getIdGeoFence() {
        return idGeoFence;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
