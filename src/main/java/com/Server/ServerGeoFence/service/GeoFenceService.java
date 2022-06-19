package com.Server.ServerGeoFence.service;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.dao.GeoFenceDao;
import com.Server.ServerGeoFence.dao.VehicleDao;
import com.Server.ServerGeoFence.model.GeoFence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class GeoFenceService {
    private final GeoFenceDao geoFenceDao;

    public GeoFenceService(@Qualifier("fakeGeoFenceDao") GeoFenceDao geoFenceDao) {
        this.geoFenceDao = geoFenceDao;
    }

    public List<GeoFence> getAllGeoFence(){
        return this.geoFenceDao.getAllGeoFence();
    }

    public Boolean checkGeoFenceNumberPointIsLow(List<Point> points){
        return points.size() < 3;
    }

    public Boolean checkGeoFenceIntercept(List<Point> points){
        List<Edge> listEdge = Edge.createListEdgeByListPoint(points);
        for (int i = 0;i < listEdge.size() - 1;i++){
            for(int j = i + 2;j < listEdge.size();j++){
                Edge e1 = listEdge.get(i);
                Edge e2 = listEdge.get(j);
                if(i == 0 && j == listEdge.size() - 1) continue;
                if(e1.isIntersect(e2.getFirstPoint(), e2.getSecondPoint())) {
                    return true;
                }
            }
        }
        return false;
    }

    public GeoFence getGeoFenceById(UUID id){
        List<GeoFence> listGeoFence = this.geoFenceDao.getAllGeoFence();
        for (GeoFence geoFence : listGeoFence){
            if(geoFence.getId().equals(id)){
                return geoFence;
            }
        }
        return null;
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

    public void updateGeoFenceById(UUID id, GeoFence newGeoFence){
        List<GeoFence> listGeoFence = this.geoFenceDao.getAllGeoFence();
        for(int i = 0;i < listGeoFence.size();i++){
            if(listGeoFence.get(i).getId().equals(id)){
                GeoFence geoFence = listGeoFence.get(i);
                Map<Integer, Point> indexPointMap = geoFence.listUpdatePoint(newGeoFence.getListPoint());
                this.updateGeoFenceToDB(id.toString(), indexPointMap);
                listGeoFence.set(i, newGeoFence);
                return;
            }
        }
    }

    public void updateGeoFenceToDB(String idGeoFence, Map<Integer, Point> indexPointMap){
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        for (Integer index : indexPointMap.keySet()){
            Point point = indexPointMap.get(index);
            try {
                javaConnect2SQL.updatePointFromId(idGeoFence, idGeoFence + "_" + index, point.getX(), point.getY());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteGeoFenceById(UUID id){
        this.geoFenceDao.deleteGeoFenceById(id);
    }
}
