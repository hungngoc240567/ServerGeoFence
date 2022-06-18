package com.Server.ServerGeoFence.SendPacket;

import java.util.List;
import java.util.UUID;

public class SendSelectGeoFence extends BaseSendMsg{

    List<UUID> listUid;

    public SendSelectGeoFence() {
        super(BaseSendMsg.SUCCESS);
    }

    public void setListUid(List<UUID> listUid) {
        this.listUid = listUid;
    }

    public List<UUID> getListUid() {
        return listUid;
    }
}
