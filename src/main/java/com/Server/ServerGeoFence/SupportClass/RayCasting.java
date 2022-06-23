package com.Server.ServerGeoFence.SupportClass;

import com.Server.ServerGeoFence.TestClass.ReportTestAlgorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RayCasting {
    public static boolean isInPolygon(List<Point> polygon, Point point, Point outSidePoint){
        List<Edge> listEdge = Edge.createListEdgeByListPoint(polygon);
        Point p1 = outSidePoint;
        Point p2 = point;
        int count = 0;
        for (int i = 0;i < listEdge.size();i++){
            Edge edge = listEdge.get(i);
            if(edge.isIntersect(p1, p2)) count++;
        }
        return count % 2 == 1;
    }

    public static ReportTestAlgorithm testPerformance(int[] listNumberVertical, List<List<Point>> listPolygon, List<Point> pointsTest, Point outSidePoint){
        ReportTestAlgorithm table = new ReportTestAlgorithm();
        Map<Integer, Long> tablePreprocessing = table.getTablePreprocessing();
        Map<Integer, Long> tableProcessing = table.getTableProcess();
        for(int i = 0;i < listNumberVertical.length;i++){
            List<Point> polygon = listPolygon.get(i);
            // estimate preprocessing
            int numberVertical = listNumberVertical[i];
            long curTime = (new Date()).getTime();
            long preprocessTime = (new Date()).getTime() - curTime;
            tablePreprocessing.put(numberVertical, preprocessTime);
            // estimate processTime
            curTime = (new Date()).getTime();
            for(int j = 0;j < pointsTest.size();j++){
                Point point = pointsTest.get(j);
                RayCasting.isInPolygon(polygon, point, outSidePoint);
            }
            long timeProcess = (new Date()).getTime() - curTime;
            tableProcessing.put(numberVertical, timeProcess);
        }
        return table;
    }
}
