package com.Server.ServerGeoFence.SupportClass;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UpdateVehiclePosition {
    private final UUID id;
    private final Point point;

    public UpdateVehiclePosition(@JsonProperty("id") UUID id,@JsonProperty("point") Point point) {
        this.id = id;
        this.point = point;
    }

    public UUID getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "UpdateVehiclePosition{" +
                "id=" + id +
                ", point=" + point.toString() +
                '}';
    }
}
