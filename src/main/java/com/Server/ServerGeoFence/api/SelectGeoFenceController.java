package com.Server.ServerGeoFence.api;

import com.Server.ServerGeoFence.ReceivedPacket.ReceivedSelectGeoFence;
import com.Server.ServerGeoFence.ReceivedPacket.ReceivedUpdateGeoFence;
import com.Server.ServerGeoFence.SendPacket.SendSelectGeoFence;
import com.Server.ServerGeoFence.SendPacket.SendUpdateGeoFence;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.service.GeoFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("api/v1/geoFence/selectGeoFence")
@RestController
public class SelectGeoFenceController {
    private final GeoFenceService geoFenceService;

    @Autowired
    public SelectGeoFenceController(GeoFenceService geoFenceService) {
        this.geoFenceService = geoFenceService;
    }

    @PostMapping
    public SendSelectGeoFence getListSelectGeoFenceByPoint(@RequestBody ReceivedSelectGeoFence receivedSelectGeoFence){
        Point selectPoint = receivedSelectGeoFence.getPoint();
        List<GeoFence> listGeoFence = this.geoFenceService.getAllGeoFence();
        List<UUID> listGeoFenceInUUID = new ArrayList<>();
        for (GeoFence geoFence : listGeoFence){
            if(geoFence.isInThis(selectPoint)){
                listGeoFenceInUUID.add(geoFence.getId());
            }
        }
        SendSelectGeoFence sendSelectGeoFence = new SendSelectGeoFence();
        sendSelectGeoFence.setListUid(listGeoFenceInUUID);
        return sendSelectGeoFence;
    }
}
