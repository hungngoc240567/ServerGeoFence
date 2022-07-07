package com.Server.ServerGeoFence.dao;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;
// this class need auto created by spring and role of this
// is a repository (kho chua)

@Repository("fakeVehicleDao")
public class FakeVehicleDataAccessService implements VehicleDao{

    private static Map<UUID, Vehicle> db = null;

    @Override
    public UUID insertVehicle(UUID id, String type, Point point, List<UUID> listId, double vx, double vy) {
        Vehicle vehicle = new Vehicle(id, type, point, vx, vy);
        vehicle.setListIdGeoFenceIn(listId);
        db.put(vehicle.getId(), vehicle);
        vehicle.saveVehicleToDB();
        return id;
    }

    @Override
    public synchronized List<Vehicle> selectAllVehicle() {
        if(db == null){
            db = this.parseListVehicleFromDB();
        }
        List<Vehicle> listVehicle = new ArrayList<>();
        for (UUID id : db.keySet()){
            listVehicle.add(db.get(id));
        }
        return listVehicle;
    }

    public Map<UUID, Vehicle> parseListVehicleFromDB(){
        JavaConnect2SQL javaConnect2SQL = JavaConnect2SQL.getInstance();
        Map<UUID, Vehicle> db = new HashMap<>();
        try {
            List<Vehicle> vehicles = javaConnect2SQL.loadAllVehicle();
            for (int i = 0;i < vehicles.size();i++){
                Vehicle vehicle = vehicles.get(i);
                db.put(vehicle.getId(), vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return db;
    }

    @Override
    public Vehicle getVehicleById(UUID id) {
        return db.get(id);
    }

    @Override
    public int deleteVehicleById(UUID id) {
        Vehicle vehicle = getVehicleById(id);
        if(vehicle == null) return 0;
        db.remove(vehicle.getId());
        return 1;
    }

    @Override
    public int updateVehicleById(UUID id, Vehicle vehicle) {
        Vehicle oldVehicle = this.getVehicleById(id);
        if(oldVehicle == null) return 0;
        Vehicle newVehicle = new Vehicle(id, vehicle.getType(), vehicle.getCurPoint(), vehicle.getVx(), vehicle.getVy());
        db.put(id, newVehicle);
        return 1;
    }
}
