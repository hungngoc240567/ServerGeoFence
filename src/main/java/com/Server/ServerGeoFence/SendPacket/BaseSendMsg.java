package com.Server.ServerGeoFence.SendPacket;

public class BaseSendMsg {
    public static byte SUCCESS = 0;
    public static byte FAIL = 1;
    protected byte errCode;

    public BaseSendMsg(byte errCode) {
        this.errCode = errCode;
    }

    public byte getErrCode() {
        return errCode;
    }
}
