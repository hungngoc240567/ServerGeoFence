package com.Server.ServerGeoFence.service;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.dao.FakeVehicleDataAccessService;
import com.Server.ServerGeoFence.dao.VehicleDao;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {
    private final VehicleDao vehicleDao;

    private GeoFenceService geoFenceService;

    public VehicleService(@Qualifier("fakeVehicleDao") VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    public UUID addVehicle(Vehicle vehicle){
        List<UUID> listId = this.getGeoFenceService().getListIdGeoWithPointIn(vehicle.getCurPoint());
        vehicle.setListIdGeoFenceIn(listId);
        return vehicleDao.insertVehicle(vehicle);
    }

    public List<Vehicle> selectAllVehicle(){
        return this.vehicleDao.selectAllVehicle();
    }

    @Autowired
    public void setGeoFenceService(GeoFenceService geoFenceService) {
        this.geoFenceService = geoFenceService;
    }

    public Optional<Vehicle> getVehicleById(UUID id){
        return this.vehicleDao.getVehicleById(id);
    }

    public int deleteVehicleById(UUID id){
        return this.vehicleDao.deleteVehicleById(id);
    }

    public int updateVehicleById(UUID id, Vehicle newVehicle){
        return this.vehicleDao.updateVehicleById(id, newVehicle);
    }

    public GeoFenceService getGeoFenceService() {
        return geoFenceService;
    }

    public List<UUID> updatePositionVehicleById(UUID id, Point point) {
        List<UUID> listEmptyId = new ArrayList<>();
        Optional<Vehicle> vehicles = this.getVehicleById(id);
        return vehicles.map(p -> {
            int indexUpdatePosition = this.selectAllVehicle().indexOf(p);
            if(indexUpdatePosition >= 0){
                Vehicle vehicle = this.selectAllVehicle().get(indexUpdatePosition);
                vehicle.setListIdGeoFenceIn(getGeoFenceService().getListIdGeoWithPointIn(point));
                vehicle.setCurPoint(point);
                return vehicle.getListIdGeoFenceIn();
            }
            System.out.println("Can not find vehicle with id" + id);
            return listEmptyId;
        }).orElse(listEmptyId);
    }
}
