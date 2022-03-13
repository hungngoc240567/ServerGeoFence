package com.Server.ServerGeoFence.SupportClass;

public class GrahamPoint extends Point{
    private int ind = 0;
    public GrahamPoint(double x, double y) {
        super(x, y);
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }
}
