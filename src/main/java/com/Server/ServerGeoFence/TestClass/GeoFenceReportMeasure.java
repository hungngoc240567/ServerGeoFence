package com.Server.ServerGeoFence.TestClass;

public class GeoFenceReportMeasure {
    int numberSample = 0;
    int numberWrongSample = 0;
    int numberRightSample = 0;

    public GeoFenceReportMeasure(int numberSample, int numberWrongSample, int numberRightSample) {
        this.numberSample = numberSample;
        this.numberWrongSample = numberWrongSample;
        this.numberRightSample = numberRightSample;
    }

    public double getPercentRight(){
        return ((double)numberRightSample) / numberSample;
    }

    public int getNumberRightSample() {
        return numberRightSample;
    }

    public int getNumberSample() {
        return numberSample;
    }

    public int getNumberWrongSample() {
        return numberWrongSample;
    }
}
