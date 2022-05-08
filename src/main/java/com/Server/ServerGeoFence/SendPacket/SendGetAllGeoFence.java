package com.Server.ServerGeoFence.SendPacket;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SendGetAllGeoFence extends BaseSendMsg{
    List<UUID> listGeoFenceUID;
    List<List<Point>> listPointOfGeoFences;

    public SendGetAllGeoFence() {
        super(SUCCESS);
    }

    public void setInfoListGeoFence(List<GeoFence> listGeoFence){
        listPointOfGeoFences = new ArrayList<>();
        listGeoFenceUID = new ArrayList<>();
        for(int i = 0;i < listGeoFence.size();i++){
            GeoFence geoFence = listGeoFence.get(i);
            List<Point> listPointOfGeoFence = geoFence.getListPoint();
            List<Point> listPoints = new ArrayList<>();
            for(int j = 0;j < listPointOfGeoFence.size();j++){
                listPoints.add(new Point(listPointOfGeoFence.get(j)));
            }
            listGeoFenceUID.add(geoFence.getId());
            listPointOfGeoFences.add(listPoints);
        }
    }

    public List<List<Point>> getListPointOfGeoFences() {
        return listPointOfGeoFences;
    }

    public List<UUID> getListGeoFenceUID() {
        return listGeoFenceUID;
    }
}
