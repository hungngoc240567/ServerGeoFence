package com.Server.ServerGeoFence.dao;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleDao {

    UUID insertVehicle(UUID id, String type, Point curPoint, List<UUID> listId, double vx, double vy);

    default UUID insertVehicle(Vehicle vehicle){
        UUID id = UUID.randomUUID();
        return insertVehicle(id, vehicle.getType(), vehicle.getCurPoint(), vehicle.getListIdGeoFenceIn(), vehicle.getVx(), vehicle.getVy());
    }

    List<Vehicle> selectAllVehicle();

    Optional<Vehicle> getVehicleById(UUID id);

    int deleteVehicleById(UUID id);

    int updateVehicleById(UUID id, Vehicle vehicle);


}
