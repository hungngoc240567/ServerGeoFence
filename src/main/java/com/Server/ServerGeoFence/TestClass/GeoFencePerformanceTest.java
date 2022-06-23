package com.Server.ServerGeoFence.TestClass;

import com.Server.ServerGeoFence.SupportClass.*;
import com.Server.ServerGeoFence.model.GeoFence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GeoFencePerformanceTest {
    static  GeoFencePerformanceTest instance = null;
    private static int[] NUMBER_VERTICAL_PER_POLYGON = {16, 32, 64, 128, 256, 512, 1024};
    private Point center = new Point(800, 500);
    private double radius = 500;
    private Point outSidePoint = new Point(5000, 5000); // dùng cho ray-casting
    private int numberPointTest = 1000000;
    List<List<Point>> listConvexTest = new ArrayList<>();
    List<List<Point>> listPolygonTest = new ArrayList<>();
    List<Point> listTestPoint = new ArrayList<>();

    // table report cho các thuật toán
    ReportTestAlgorithm convexRayCasting = new ReportTestAlgorithm();
    ReportTestAlgorithm convexSumAngle = new ReportTestAlgorithm();
    ReportTestAlgorithm convexTriCon = new ReportTestAlgorithm();
    ReportTestAlgorithm convexGraham = new ReportTestAlgorithm();

    ReportTestAlgorithm nonConvexTriCon = new ReportTestAlgorithm();
    ReportTestAlgorithm nonConvexRayCasting = new ReportTestAlgorithm();


    public static synchronized GeoFencePerformanceTest getInstance(){
        if(GeoFencePerformanceTest.instance == null){
            GeoFencePerformanceTest.instance = new GeoFencePerformanceTest();
        }
        return GeoFencePerformanceTest.instance;
    }

    public void test(){
        this.generateListConvex();
        this.generateListPolygon();
        this.generateListTestPoint();
        this.testWithConvex();
        this.printAllTableConvex();
        this.testWithNoneConvex();
        this.printAllTableNonConvex();
    }

    public void generateListConvex(){
        listConvexTest.clear();
        for(int i = 0;i < NUMBER_VERTICAL_PER_POLYGON.length;i++){
            int numberVertical = NUMBER_VERTICAL_PER_POLYGON[i];
            List<Point> convex = RandomPolygon.generateConvex(center, radius, numberVertical);
            listConvexTest.add(convex);
        }
    }

    public void generateListPolygon(){
        listPolygonTest.clear();
        for(int i = 0;i < NUMBER_VERTICAL_PER_POLYGON.length;i++){
            int numberVertical = NUMBER_VERTICAL_PER_POLYGON[i];
            List<Point> polygon = RandomPolygon.generatePolygon(center, radius,0.2, 0.5, numberVertical);
            listPolygonTest.add(polygon);
        }
    }

    public void generateListTestPoint(){
        this.listTestPoint.clear();
        for(int i = 0;i < numberPointTest;i++){
            double x = center.getX() + radius - 2 * Math.random() * radius;
            double y = center.getX() + radius - 2 * Math.random() * radius;
            this.listTestPoint.add(new Point(x, y));
        }
    }

    public void testWithConvex(){
        this.convexGraham = Graham.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listConvexTest, listTestPoint);
        this.convexGraham.setName("convexGraham");
        this.convexRayCasting = RayCasting.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listConvexTest, listTestPoint, outSidePoint);
        this.convexRayCasting.setName("convexRayCasting");
        this.convexSumAngle = SumOfAngle.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listConvexTest, listTestPoint);
        this.convexSumAngle.setName("convexSumAngle");
        this.convexTriCon = GeoFence.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listConvexTest, listTestPoint);
        this.convexTriCon.setName("convexTriCon");
    }

    public void testWithNoneConvex(){
        this.nonConvexRayCasting = RayCasting.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listPolygonTest, listTestPoint, outSidePoint);
        this.nonConvexRayCasting.setName("nonConvexRayCasting");
        this.nonConvexTriCon = GeoFence.testPerformance(NUMBER_VERTICAL_PER_POLYGON, listPolygonTest, listTestPoint);
        this.nonConvexTriCon.setName("nonConvexTriCon");
    }

    public void printAllTableConvex(){
        this.convexGraham.print();
        this.convexRayCasting.print();
        this.convexSumAngle.print();
        this.convexTriCon.print();
    }

    public void printAllTableNonConvex(){
        this.nonConvexRayCasting.print();
        this.nonConvexTriCon.print();
    }
}
