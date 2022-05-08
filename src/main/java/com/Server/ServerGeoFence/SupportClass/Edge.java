package com.Server.ServerGeoFence.SupportClass;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private Point firstPoint;
    private Point secondPoint;

    public Edge(Point p1, Point p2){
        firstPoint = new Point(p1);
        secondPoint = new Point(p2);
        sortPoint();
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setFirstPoint(Point firstPoint) {
        this.firstPoint = firstPoint;
    }

    public void setSecondPoint(Point secondPoint) {
        this.secondPoint = secondPoint;
    }

    private void sortPoint(){
        if(firstPoint == null || secondPoint == null) return;
        Point oPoint = new Point(0, 0);
        if(!Graham.isLower(oPoint, secondPoint, firstPoint))
            firstPoint.swap(secondPoint);
    }

    public Boolean isIntersect(Point p1, Point p2){
        return (Graham.isLower(p1, p2, firstPoint) ^ Graham.isLower(p1, p2, secondPoint)) && (Graham.isLower(firstPoint, secondPoint, p1) ^ Graham.isLower(firstPoint, secondPoint, p2));
    }

    public static List<Edge> createListEdgeByListPoint(List<Point> points){
        List<Edge> listEdge = new ArrayList<>();
        if(points.size() < 3) return listEdge;
        for(int i = 0;i < points.size();i++){
            Point p1 = points.get(i);
            Point p2 = (i == points.size() - 1) ? points.get(0) : points.get(i + 1);
            Edge edge = new Edge(p1, p2);
            listEdge.add(edge);
        }
        return listEdge;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "firstPoint=" + firstPoint.toString() +
                ", secondPoint=" + secondPoint.toString() +
                '}';
    }
}
