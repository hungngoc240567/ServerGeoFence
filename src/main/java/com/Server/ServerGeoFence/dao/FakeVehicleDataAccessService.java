package com.Server.ServerGeoFence.dao;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
// this class need auto created by spring and role of this
// is a repository (kho chua)

@Repository("fakeVehicleDao")
public class FakeVehicleDataAccessService implements VehicleDao{

    private static List<Vehicle> db = null;

    @Override
    public UUID insertVehicle(UUID id, String type, Point point, List<UUID> listId, double vx, double vy) {
        Vehicle vehicle = new Vehicle(id, type, point, vx, vy);
        vehicle.setListIdGeoFenceIn(listId);
        db.add(vehicle);
        vehicle.saveVehicleToDB();
        return id;
    }

    @Override
    public synchronized List<Vehicle> selectAllVehicle() {
        if(db == null){
            db = this.parseListVehicleFromDB();
        }
        return db;
    }

    public List<Vehicle> parseListVehicleFromDB(){
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            vehicles = javaConnect2SQL.loadAllVehicle();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
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
                Vehicle newVehicle = new Vehicle(id, vehicle.getType(), vehicle.getCurPoint(), vehicle.getVx(), vehicle.getVy());
                db.set(indexOfDelete, newVehicle);
                return 1;
            }
            return 0;
        }).orElse(0);
    }
}
