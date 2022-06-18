package com.Server.ServerGeoFence.model;

import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Graham;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.TriTree.TriTree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        System.out.println("them geo fence");
        this.print();
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
}
