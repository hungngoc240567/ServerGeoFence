package com.Server.ServerGeoFence.SendPacket;

import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SendAddGeoFence extends BaseSendMsg {
    public static byte FAIL_POINTS_INTERCEPT = 2;
    public static byte FAIL_NUMBER_POINTS_TOO_LOW = 3;
    private List<Point> listGeoFencePoint;
    private UUID idGeoFence;

    public SendAddGeoFence(byte errCode, List<Point> listGeoFencePoint, UUID idGeoFence, byte errCode1) {
        super(errCode);
        this.listGeoFencePoint = listGeoFencePoint;
        this.idGeoFence = idGeoFence;
        this.errCode = errCode1;
    }

    public SendAddGeoFence(){
        super(SUCCESS);
        this.errCode = SUCCESS;
        this.idGeoFence = null;
        this.listGeoFencePoint = new ArrayList<>();
    }

    public void setErrCode(byte errCode) {
        this.errCode = errCode;
    }

    public void setIdGeoFence(UUID idGeoFence) {
        this.idGeoFence = idGeoFence;
    }

    public void setListGeoFencePoint(List<Point> listGeoFencePoint) {
        this.listGeoFencePoint = listGeoFencePoint;
    }

    public List<Point> getListGeoFencePoint() {
        return listGeoFencePoint;
    }

    public UUID getIdGeoFence() {
        return idGeoFence;
    }
}
