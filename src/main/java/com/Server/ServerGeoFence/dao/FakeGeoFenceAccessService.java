package com.Server.ServerGeoFence.dao;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("fakeGeoFenceDao")
public class FakeGeoFenceAccessService implements GeoFenceDao{
    private List<GeoFence> db = new ArrayList<>();

    @Override
    public GeoFence addGeoFence(UUID id, List<Point> listPoint) {
        GeoFence newGeoFence = new GeoFence(id, listPoint);
        db.add(newGeoFence);
        return newGeoFence;
    }

    @Override
    public List<GeoFence> getAllGeoFence() {
        return db;
    }
}
