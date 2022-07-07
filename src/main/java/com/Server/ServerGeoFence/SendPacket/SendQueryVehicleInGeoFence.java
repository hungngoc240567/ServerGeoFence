package com.Server.ServerGeoFence.SendPacket;

import java.util.List;
import java.util.UUID;

public class SendQueryVehicleInGeoFence extends BaseSendMsg{
    private List<UUID> listIdVehicle;
    public SendQueryVehicleInGeoFence(byte errCode) {
        super(errCode);
    }

    public List<UUID> getListIdVehicle() {
        return listIdVehicle;
    }

    public void setListIdVehicle(List<UUID> listIdVehicle) {
        this.listIdVehicle = listIdVehicle;
    }
}
