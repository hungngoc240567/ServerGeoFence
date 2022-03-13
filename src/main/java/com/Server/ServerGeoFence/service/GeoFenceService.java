package com.Server.ServerGeoFence.service;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.dao.GeoFenceDao;
import com.Server.ServerGeoFence.dao.VehicleDao;
import com.Server.ServerGeoFence.model.GeoFence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GeoFenceService {
    private final GeoFenceDao geoFenceDao;

    public GeoFenceService(@Qualifier("fakeGeoFenceDao") GeoFenceDao geoFenceDao) {
        this.geoFenceDao = geoFenceDao;
    }

    public List<GeoFence> getAllGeoFence(){
        return this.geoFenceDao.getAllGeoFence();
    }

    public GeoFence addGeoFence(List<Point> points){
        return this.geoFenceDao.addGeoFence(points);
    }

    public List<UUID> getListIdGeoWithPointIn(Point point){
        List<GeoFence> listGeoFence = this.geoFenceDao.getAllGeoFence();
        List<UUID> listId = new ArrayList<>();
        for(int i = 0;i < listGeoFence.size();i++){
            GeoFence geoFence = listGeoFence.get(i);
            if(geoFence.isInThis(point))
                listId.add(geoFence.getId());
        }
        return listId;
    }
}
