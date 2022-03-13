package com.Server.ServerGeoFence.model;

import com.Server.ServerGeoFence.SupportClass.Graham;
import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeoFence {
    private UUID id;
    private List<Point> listConvexPoint;
    private static final boolean IS_TEST = false;

    public GeoFence(UUID id, List<Point> listPoint) {
        this.id = id;
        this.listConvexPoint = this.findListConvex(listPoint);
        if(GeoFence.IS_TEST)
            this.testListConvex();
    }

    private void testListConvex(){
        // test field
        List<Point> lp = new ArrayList<>();
        lp.add(new Point(1, 3));
        lp.add(new Point(4, 4));
        lp.add(new Point(3, 7));
        lp.add(new Point(3, 1));
        lp.add(new Point(6, 5));
        lp.add(new Point(4, 6));
        lp.add(new Point(4, 2));
        lp.add(new Point(3, 4));
        lp.add(new Point(2, 4));
        lp.add(new Point(5, 3));
        lp.add(new Point(2, 5));
        lp.add(new Point(1, 2));
        for(int i = 0;i < lp.size();i++){
            boolean isIn = this.isInThis(lp.get(i));
            System.out.println(lp.get(i).toString() + " " + "is in: " + isIn);
        }
    }

    private List<Point> findListConvex(List<Point> listPoint){
        return Graham.findConvexHull(listPoint);
    }

    public UUID getId() {
        return id;
    }

    public List<Point> getListConvexPoint() {
        return listConvexPoint;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setListConvexPoint(List<Point> listConvexPoint) {
        this.listConvexPoint = listConvexPoint;
    }

    public boolean isInThis(Point point){
        return Graham.isInConvex(this, point);
    }
}
