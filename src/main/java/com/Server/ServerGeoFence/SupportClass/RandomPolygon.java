package com.Server.ServerGeoFence.SupportClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Class phục vụ việc generate polygon để test
public class RandomPolygon {
    public static List<Point> generatePolygon(Point center, double avgRadius, double irregularity, double spikiness, int numberVertices){
        irregularity *= 2 * Math.PI / numberVertices;
        spikiness *= avgRadius;
        List<Double> anglesSteps = RandomPolygon.randomAngleStep(numberVertices, irregularity);
        List<Point> points = new ArrayList<>();
        double angle = Math.random() * 2 * Math.PI;
        for (int i = 0;i < numberVertices;i++){
            double radius = RandomPolygon.clip(avgRadius + new Random().nextGaussian() * spikiness, 0, 2 * avgRadius);
            Point point = new Point(center.getX() + radius * Math.cos(angle), center.getY() + radius * Math.sin(angle));
            points.add(point);
            angle+=anglesSteps.get(i);
        }
        return points;
    }

    private static List<Double> randomAngleStep(int steps, double irregularity){
        List<Double> angles = new ArrayList<>();
        double lower = (2 * Math.PI / steps) - irregularity;
        double upper = (2 * Math.PI / steps) + irregularity;
        double cumSum = 0;
        for (int i = 0;i < steps;i++){
            double angle = lower + Math.random()*(upper - lower);
            angles.add(angle);
            cumSum+=angle;
        }
        cumSum /= (2 * Math.PI);
        for (int i = 0;i < steps;i++){
            angles.set(i, angles.get(i) / cumSum);
        }
        return angles;
    }

    public static double clip(double value, double lower, double upper){
        return Math.min(upper, Math.max(value, lower));
    }

    public static List<Point> generateConvex(Point center, double radius, int numberVertical){
        List<Point> points = new ArrayList<>();
        for(int i = 0;i < numberVertical;i++){
            double x = radius - 2 * Math.random() * radius;
            double y = Math.sqrt(radius * radius - x * x);
            if(Math.random() >= 0.5) y *= -1;
            points.add(new Point(x + center.getX(), y + center.getY()));
        }
        points = Graham.findConvexHull(points);
        return points;
    }
}
