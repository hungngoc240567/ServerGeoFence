package com.Server.ServerGeoFence.model;

import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Graham;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.TriTree.TriTree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeoFence {
    private UUID id;
    private List<Point> listPoint;
    private static final boolean IS_TEST = false;
    private TriTree tree;

    public GeoFence(UUID id, List<Point> listPoint) {
        this.id = id;
        tree = new TriTree();
        this.setListPoint(listPoint);
        tree.build(listPoint);
//        if(GeoFence.IS_TEST)
//            this.testListConvex();
    }

    private void testListConvex(){
        // test field
//        List<Point> lp = new ArrayList<>();
//        lp.add(new Point(1, 3));
//        lp.add(new Point(4, 4));
//        lp.add(new Point(3, 7));
//        lp.add(new Point(3, 1));
//        lp.add(new Point(6, 5));
//        lp.add(new Point(4, 6));
//        lp.add(new Point(4, 2));
//        lp.add(new Point(3, 4));
//        lp.add(new Point(2, 4));
//        lp.add(new Point(5, 3));
//        lp.add(new Point(2, 5));
//        lp.add(new Point(1, 2));
//        for(int i = 0;i < lp.size();i++){
//            boolean isIn = this.isInThis(lp.get(i));
//            System.out.println(lp.get(i).toString() + " " + "is in: " + isIn);
//        }
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
}
