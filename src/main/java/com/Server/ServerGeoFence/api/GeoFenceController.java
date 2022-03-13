package com.Server.ServerGeoFence.api;

import com.Server.ServerGeoFence.SupportClass.MyListPoint;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.service.GeoFenceService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/geoFence")
@RestController
public class GeoFenceController {
    private final GeoFenceService geoFenceService;

    @Autowired
    public GeoFenceController(GeoFenceService geoFenceService) {
        this.geoFenceService = geoFenceService;
    }

    @PostMapping
    public GeoFence addGeoFence(@RequestBody MyListPoint points){
        return this.geoFenceService.addGeoFence(points.getPoints());
    }

    @GetMapping
    public List<GeoFence> getAllGeoFence(){
        return this.geoFenceService.getAllGeoFence();
    }

}
