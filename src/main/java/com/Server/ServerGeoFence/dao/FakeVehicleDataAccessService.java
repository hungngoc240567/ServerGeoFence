package com.Server.ServerGeoFence.dao;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
// this class need auto created by spring and role of this
// is a repository (kho chua)

@Repository("fakeVehicleDao")
public class FakeVehicleDataAccessService implements VehicleDao{

    private static List<Vehicle> db = new ArrayList<>();

    @Override
    public UUID insertVehicle(UUID id, String type, Point point, List<UUID> listId) {
        Vehicle vehicle = new Vehicle(id, type, point);
        vehicle.setListIdGeoFenceIn(listId);
        db.add(vehicle);
        return id;
    }

    @Override
    public List<Vehicle> selectAllVehicle() {
        return db;
    }

    @Override
    public Optional<Vehicle> getVehicleById(UUID id) {
        return db.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();
    }

    @Override
    public int deleteVehicleById(UUID id) {
        Optional<Vehicle> vehicle = getVehicleById(id);
        if(vehicle.isEmpty())
            return 0;
        db.remove(vehicle.get());
        return 1;
    }

    @Override
    public int updateVehicleById(UUID id, Vehicle vehicle) {
        return this.getVehicleById(id).map(p -> {
            int indexOfDelete = db.indexOf(p);
            if(indexOfDelete >= 0){
                Vehicle newVehicle = new Vehicle(id, vehicle.getType(), vehicle.getCurPoint());
                db.set(indexOfDelete, newVehicle);
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}
