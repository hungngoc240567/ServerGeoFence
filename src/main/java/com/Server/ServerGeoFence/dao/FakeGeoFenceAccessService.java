package com.Server.ServerGeoFence.dao;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import org.springframework.stereotype.Repository;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository("fakeGeoFenceDao")
public class FakeGeoFenceAccessService implements GeoFenceDao{
    private List<GeoFence> db = null;

    @Override
    public GeoFence addGeoFence(UUID id, List<Point> listPoint) {
        GeoFence newGeoFence = new GeoFence(id, listPoint);
        db.add(newGeoFence);
        // luu lai hang rao vao db
        try {
            newGeoFence.saveToDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newGeoFence;
    }

    @Override
    public void deleteGeoFenceById(UUID id){
        int index = -1;
        GeoFence deleteGeoFence = null;
        for (int i = 0;i < db.size();i++){
            GeoFence geoFence = db.get(i);
            if(geoFence != null && geoFence.getId().equals(id)){
                index = i;
                deleteGeoFence = db.get(index);
            }
        }
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        try {
            javaConnect2SQL.deleteGeofenceFromDB(deleteGeoFence.getId().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        db.remove(index);
    }

    @Override
    public synchronized List<GeoFence> getAllGeoFence() {
        if(db == null){
            db = this.parseListGeoFenceFromDB();
        }
        System.out.println(db);
        return db;
    }

    public List<GeoFence> parseListGeoFenceFromDB(){
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        List<GeoFence> listGeoFence = new ArrayList<>();
        try {
            Map<String, List<Point>> geoFenceMap = javaConnect2SQL.loadAllPointOfGeofenceFromDB();
            for(String id : geoFenceMap.keySet()){
                List<Point> listPoint = geoFenceMap.get(id);
                GeoFence geoFence = new GeoFence(UUID.fromString(id), listPoint);
                listGeoFence.add(geoFence);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listGeoFence;
    }
}
