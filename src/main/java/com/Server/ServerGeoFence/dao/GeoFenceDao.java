package com.Server.ServerGeoFence.dao;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;

import java.util.List;
import java.util.UUID;

public interface GeoFenceDao {
    GeoFence addGeoFence(UUID id, List<Point> listConvex);
    default GeoFence addGeoFence(List<Point> points){
        UUID id = UUID.randomUUID();
        return addGeoFence(id, points);
    }
    public List<GeoFence> getAllGeoFence();
}
