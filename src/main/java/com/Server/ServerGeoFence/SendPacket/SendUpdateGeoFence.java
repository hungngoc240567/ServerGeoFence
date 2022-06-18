package com.Server.ServerGeoFence.SendPacket;

import com.Server.ServerGeoFence.SupportClass.Point;

import java.util.List;
import java.util.UUID;

public class SendUpdateGeoFence extends SendAddGeoFence{
    public static byte GEO_FENCE_IS_NOT_EXIST = 2;
    public SendUpdateGeoFence() {
        super();
    }
}
