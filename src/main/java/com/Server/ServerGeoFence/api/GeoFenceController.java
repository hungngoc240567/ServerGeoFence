package com.Server.ServerGeoFence.api;

import com.Server.ServerGeoFence.ReceivedPacket.ReceivedAddGeoFence;
import com.Server.ServerGeoFence.SendPacket.SendAddGeoFence;
import com.Server.ServerGeoFence.SendPacket.SendGetAllGeoFence;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.service.GeoFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/v1/geoFence")
@RestController
public class GeoFenceController {
    private final GeoFenceService geoFenceService;

    @Autowired
    public GeoFenceController(GeoFenceService geoFenceService) {
        this.geoFenceService = geoFenceService;
    }

    @PostMapping
    public SendAddGeoFence addGeoFence(@RequestBody ReceivedAddGeoFence receivedAddGeoFence){
        List<Point> listPoint = receivedAddGeoFence.getPoints();
        SendAddGeoFence msg = new SendAddGeoFence();
        if(this.geoFenceService.checkGeoFenceNumberPointIsLow(listPoint)){
            msg.setErrCode(SendAddGeoFence.FAIL_NUMBER_POINTS_TOO_LOW);
            return msg;
        }
        if(this.geoFenceService.checkGeoFenceIntercept(listPoint)){
            msg.setErrCode(SendAddGeoFence.FAIL_POINTS_INTERCEPT);
            return msg;
        }
        GeoFence newGeoFence = this.geoFenceService.addGeoFence(listPoint);
        msg.setErrCode(SendAddGeoFence.SUCCESS);
        msg.setIdGeoFence(newGeoFence.getId());
        msg.setListGeoFencePoint(newGeoFence.getListPoint());
        return msg;
    }

    @GetMapping
    public SendGetAllGeoFence getAllGeoFence(){
        List<GeoFence> listGeoFence = this.geoFenceService.getAllGeoFence();
        SendGetAllGeoFence msg = new SendGetAllGeoFence();
        msg.setInfoListGeoFence(listGeoFence);
        return msg;
    }

}
