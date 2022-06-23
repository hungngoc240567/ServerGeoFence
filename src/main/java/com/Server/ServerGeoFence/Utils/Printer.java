package com.Server.ServerGeoFence.Utils;

import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.List;

public class Printer {
    public static void printListPoint2Array(List<Point> points){
        System.out.print("[");
        for(int i = 0;i < points.size();i++){
            if(i != points.size() - 1)
                System.out.print(points.get(i).getX() +", " +points.get(i).getY() + ", ");
            else
                System.out.print(points.get(i).getX() +", " +points.get(i).getY());
        }
        System.out.println("]");
    }

    public static void printListPoint22DArray(List<Point> points){
        System.out.print("[");
        for(int i = 0;i < points.size();i++){
            if(i != points.size() - 1)
                System.out.print("[" + points.get(i).getX() +", " +points.get(i).getY() + "], ");
            else
                System.out.print("[" + points.get(i).getX() +", " +points.get(i).getY() + "]");
        }
        System.out.println("]");
    }

    public static void printListTrueFalse(List<Boolean> booleans){
        System.out.print("[");
        for(int i = 0;i < booleans.size();i++){
            if(i != booleans.size() - 1)
                System.out.print(booleans.get(i) +", ");
            else
                System.out.print(booleans.get(i));
        }
        System.out.println("]");
    }
}
