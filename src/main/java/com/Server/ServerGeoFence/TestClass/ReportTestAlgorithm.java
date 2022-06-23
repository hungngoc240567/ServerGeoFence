package com.Server.ServerGeoFence.TestClass;

import java.util.HashMap;
import java.util.Map;

public class ReportTestAlgorithm {
    String name = "";
    Map<Integer, Long> tablePreprocessing = new HashMap<>();
    Map<Integer, Long> tableProcess = new HashMap<>();

    public Map<Integer, Long> getTablePreprocessing() {
        return tablePreprocessing;
    }

    public Map<Integer, Long> getTableProcess() {
        return tableProcess;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("Report of algorithm " + name);
        for (Integer numberVertical : tablePreprocessing.keySet()){
            System.out.print(numberVertical +"     ");
        }
        System.out.println();
        System.out.println("Table preprocessing");
        for (Integer numberVertical : tablePreprocessing.keySet()){
            System.out.print((double)tablePreprocessing.get(numberVertical) / 1000 +"     ");
        }
        System.out.println();
        System.out.println("Table processing");
        for (Integer numberVertical : tableProcess.keySet()){
            System.out.print((double)tableProcess.get(numberVertical) / 1000 +"     ");
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
