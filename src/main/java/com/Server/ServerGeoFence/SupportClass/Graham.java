package com.Server.ServerGeoFence.SupportClass;

import com.Server.ServerGeoFence.TestClass.ReportTestAlgorithm;
import com.Server.ServerGeoFence.Utils.Constant;
import com.Server.ServerGeoFence.model.GeoFence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Graham {
    private static short IS_GO_LEFT = 1;  
    private static short IS_GO_RIGHT = -1;
    private static short IS_GO_AHEAD = 0;

    public static short cww(Point p1, Point p2, Point p3){
        double t, a1, a2, b1, b2;
        a1 = p2.getX() - p1.getX();
        b1 = p2.getY() - p1.getY();
        a2 = p3.getX() - p2.getX();
        b2 = p3.getY() - p2.getY();
        t = a1*b2 - a2*b1;
        if(Math.sqrt(t) <= Constant.esp) return Graham.IS_GO_AHEAD;
        if(t < 0) return Graham.IS_GO_RIGHT;
        else return Graham.IS_GO_LEFT;
    }

    private static boolean isLower(List<Point> listPoint, Point p1, Point p2){
        short c = Graham.cww(listPoint.get(0), p1, p2);
        if(c > 0) return true;
        else if((c == 0) && ((p1.getX() < p2.getX()) || ((p1.getX() == p2.getX()) && (p1.getY() < p2.getY()))))
            return true;
        return false;
    }

    public static boolean isLower(Point p1, Point p2, Point p3){
        short c = Graham.cww(p1, p2, p3);
        if(c > 0) return true;
        else if((c == 0) && ((p1.getX() < p2.getX()) || ((p1.getX() == p2.getX()) && (p1.getY() < p2.getY()))))
            return true;
        return false;
    }

    public static void quickSort(List<Point> listPoint, int l, int r){
        int i = l, j = r;
        Point x = new Point(listPoint.get((i + j) / 2));
        do{
            while(Graham.isLower(listPoint, listPoint.get(i), x)) i++;
            while(Graham.isLower(listPoint, x, listPoint.get(j))) j--;
            if(i <= j){
                listPoint.get(i).swap(listPoint.get(j));
                i++;
                j--;
            }
        }while(i < j);
        if(i < r) quickSort(listPoint, i, r);
        if(l < j) quickSort(listPoint, l, j);
    }

    public static int getMinYPointIndex(List<Point> listPoint){
        int index = 0;
        for(int i = 1;i < listPoint.size();i++){
            Point p1 = listPoint.get(i);
            Point p2 = listPoint.get(index);
            if(p1.getY() <  p2.getY() || (p1.getY() == p2.getY() && p1.getX() < p2.getX())){
                index = i;
            }
        }
        return index;
    }

    private static PairInt findPosOfPointInConvex(List<Point> convex, int l, int r, Point p){
        int mid = (l + r) / 2;
        if(r == (l + 1))
            return new PairInt(l, r);
        Point oPoint = convex.get(0);
        Point curPoint = convex.get(mid);
        if(!Graham.isLower(oPoint, curPoint, p))
            return findPosOfPointInConvex(convex, l, mid, p);
        else return findPosOfPointInConvex(convex, mid, r, p);
    }

    public static List<Point> findConvexHull(List<Point> listPoint){
        List<Point> listConvex = new ArrayList<>();
        for(int i = 0;i < listPoint.size();i++){
            Point point = new Point(0, 0);
            listConvex.add(point);
        }
        int minYPointIndex = Graham.getMinYPointIndex(listPoint);
        listPoint.get(0).swap(listPoint.get(minYPointIndex));
        quickSort(listPoint, 1, listPoint.size() - 1);
        int m = 1;
        listConvex.set(0 ,listPoint.get(0));
        listConvex.set(1, listPoint.get(1));
        for(int i = 2;i < listPoint.size();i++){
            while(m > 0 && (cww(listConvex.get(m - 1), listConvex.get(m), listPoint.get(i)) == Graham.IS_GO_RIGHT)){
                m--;
            }
            m++;
            listConvex.set(m, listPoint.get(i));
        }
        List<Point> listRetConvex = new ArrayList<>();
        for(int i = 0;i <= m;i++){
            listRetConvex.add(listConvex.get(i));
        }
        return listRetConvex;
    }

    public static boolean isInConvex(List<Point> convex, Point point){
        // check o hai bien cua covert
        Point lastPoint = convex.get(convex.size() - 1);
        Point firstPoint = convex.get(1);
        Point oPoint = convex.get(0);

        // Point nam ngoai cuc tren
        if(Graham.isLower(oPoint, lastPoint, point))
            return false;

        // Point nam ngoai cuc duoi
        else if(!Graham.isLower(oPoint, firstPoint, point))
            return false;

        // Duyet nhi phan de tim ra 2 diem co
        // goc kep giua diem can xet
        PairInt pair = Graham.findPosOfPointInConvex(convex, 1, convex.size() - 1, point);
        if(Graham.isLower(convex.get(pair.getFirst()), convex.get(pair.getSecond()), point))
            return true;
        else
            return false;
    }

    public static ReportTestAlgorithm testPerformance(int[] listNumberVertical, List<List<Point>> listPolygon, List<Point> pointsTest){
        ReportTestAlgorithm table = new ReportTestAlgorithm();
        Map<Integer, Long> tablePreprocessing = table.getTablePreprocessing();
        Map<Integer, Long> tableProcessing = table.getTableProcess();
        for(int i = 0;i < listNumberVertical.length;i++){
            // estimate preprocessing
            int numberVertical = listNumberVertical[i];
            long curTime = (new Date()).getTime();
            long preprocessTime = (new Date()).getTime() - curTime;
            tablePreprocessing.put(numberVertical, preprocessTime);
            // estimate processTime
            List<Point> polygon = listPolygon.get(i);
            curTime = (new Date()).getTime();
            for(int j = 0;j < pointsTest.size();j++){
                Point point = pointsTest.get(j);
                Graham.isInConvex(polygon, point);
            }
            long timeProcess = (new Date()).getTime() - curTime;
            tableProcessing.put(numberVertical, timeProcess);
        }
        return table;
    }
}
