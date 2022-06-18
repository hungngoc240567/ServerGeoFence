package com.Server.ServerGeoFence.model;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Graham;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.TriTree.TriTree;

import java.sql.SQLException;
import java.util.*;

public class GeoFence {
    private UUID id; // hàng rào UID.
    private List<Point> listPoint; // Các điểm của hàng rào (double x, double y).
    private static final boolean IS_TEST = false; //
    private TriTree tree; //

    public GeoFence(UUID id, List<Point> listPoint) {
        this.id = id;
        tree = new TriTree();
        this.setListPoint(listPoint);
        tree.build(listPoint);
//        this.print();
    }

    public List<Point> getListPoint() {
        return listPoint;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isInThis(Point point){
        return tree.isPointInPolygon(point);
    }

    public void setListPoint(List<Point> listPoint) {
        this.listPoint = listPoint;
    }

    public List<List<Edge>> getListEdge(){
        return this.tree.getListEdgeByAllNode();
    }

    public Point getOriginPoint(){
        return this.tree.getOriginPoint();
    }

    public void print(){
        System.out.println("id: " + this.getId().toString());
        for(int i = 0;i < this.listPoint.size();i++){
            System.out.println("x:" + this.listPoint.get(i).getX());
            System.out.println("y:" + this.listPoint.get(i).getY());
        }
    }

    public Map<Integer, Point> listUpdatePoint(List<Point> listPoint){
        Map<Integer, Point> indexPointMap = new HashMap<>();
        List<Point> myListPoint = this.getListPoint();
        if(myListPoint.size() != listPoint.size()) return indexPointMap;
        for(int i = 0;i < myListPoint.size();i++){
            Point updatePoint = listPoint.get(i);
            Point myPoint = myListPoint.get(i);
            if(updatePoint.getX() != myPoint.getX() || updatePoint.getY() != myPoint.getY()){
                indexPointMap.put(i, updatePoint);
            }
        }
        return indexPointMap;
    }

    public void saveToDB() throws SQLException {
        JavaConnect2SQL java2SQL = JavaConnect2SQL.getInstance();
        java2SQL.insertGeoFenceToDB(this);
    }
}
