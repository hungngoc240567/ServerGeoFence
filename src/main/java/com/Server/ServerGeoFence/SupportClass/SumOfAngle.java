package com.Server.ServerGeoFence.SupportClass;

import com.Server.ServerGeoFence.TestClass.ReportTestAlgorithm;
import com.Server.ServerGeoFence.model.GeoFence;

import java.util.*;

public class SumOfAngle {
    private static final double EPS = 1e-8;
    public static boolean isInConvex(List<Point> convex, Point point){
        double sum = 0.0;
        double x = point.getX(), y = point.getY();
        List<Point> listConvex = new ArrayList<>(convex);
        listConvex.add(listConvex.get(0));
        for (int i = 0; i < listConvex.size() - 1; i++) {
            double x0 = listConvex.get(i).getX(), y0 = listConvex.get(i).getY(); //current start point
            double x1 = listConvex.get(i + 1).getX(), y1 = listConvex.get(i + 1).getY(); //current target point
            if ((x == x0 && y == y0) || (x == x1 && y == y1)) //check whether the point is same with the point in frame
                return true;
            //if(pointOnSegment(x0,y0,x1,y1,x,y))  //check whether the point on edge of the polygon
            //return true;
            double a = Len_ab(x, y, x0, y0); //get 3 side length of the triangle formed by current 3 points
            double b = Len_ab(x, y, x1, y1);
            double c = Len_ab(x0, y0, x1, y1);

            //calculate angle sum, add if cross product > 0, minus else
            sum += fabs(x_multi(x0, y0, x1, y1, x, y))
                    * Math.acos((a * a + b * b - c * c) / (2.0 * a * b));
        }
        sum = Math.abs(sum); //if angle sum equals 360, the point is inside polygon, else outside or upon
        if (Math.abs(sum - 2.0 * Math.PI) <= EPS) //compare the difference with a number small enough
            return true;
        return false;
    }

    private static double Len_ab(double x0, double y0, double x1, double y1) {
        return Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));
    }

    private static double fabs(double d) {
        if (Math.abs(d) < EPS)
            return 0;
        else
            return d > 0 ? 1 : -1;
    }

    private static double x_multi(double x0, double y0, double x1,
                                  double y1, double x, double y) { //?????
        return (x - x0) * (y1 - y0) - (x1 - x0) * (y - y0);
    }


    public static ReportTestAlgorithm testPerformance(int[] listNumberVertical, List<List<Point>> listPolygon, List<Point> pointsTest){
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
                SumOfAngle.isInConvex(polygon, point);
            }
            long timeProcess = (new Date()).getTime() - curTime;
            tableProcessing.put(numberVertical, timeProcess);
        }
        return table;
    }
}
