package com.Server.ServerGeoFence.api;
import com.Server.ServerGeoFence.ReceivedPacket.ReceivedAddVehicle;
import com.Server.ServerGeoFence.SupportClass.UpdateVehiclePosition;
import com.Server.ServerGeoFence.model.Vehicle;
import com.Server.ServerGeoFence.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/vehicle")
@RestController
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public UUID addVehicle(@RequestBody ReceivedAddVehicle receivedAddVehicle){
        Vehicle vehicle = new Vehicle(null, receivedAddVehicle.getType(), receivedAddVehicle.getVehiclePoint(),receivedAddVehicle.getVx(), receivedAddVehicle.getVy());
        return this.vehicleService.addVehicle(vehicle);
    }

    @GetMapping
    public List<Vehicle> selectAllVehicle(){
        return this.vehicleService.selectAllVehicle();
    }

    @GetMapping(path = "{id}")
    public Optional<Vehicle> getVehicleById(@PathVariable("id") UUID id){
        return Optional.ofNullable(this.vehicleService.getVehicleById(id).orElse(null));
    }

    @DeleteMapping(path = "{id}")
    public void deleteVehicleById(@PathVariable("id") UUID id){
        this.vehicleService.deleteVehicleById(id);
    }

    @PutMapping(path = "{id}")
    public void updateVehicleById(@PathVariable("id") UUID id, @RequestBody Vehicle vehicle){
        this.vehicleService.updateVehicleById(id, vehicle);
    }

    @PutMapping("/update_position")
    public List<UUID> updatePosition(@RequestBody UpdateVehiclePosition updateVehiclePosition){
        return this.vehicleService.updatePositionVehicleById(updateVehiclePosition.getId(), updateVehiclePosition.getPoint());
    }
}
