package com.Server.ServerGeoFence.api;

import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.ReceivedPacket.ReceivedQueryVehicleInGeoFence;
import com.Server.ServerGeoFence.SendPacket.BaseSendMsg;
import com.Server.ServerGeoFence.SendPacket.SendQueryVehicleInGeoFence;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/query")
@RestController
public class QueryDataBaseController {
    @PutMapping("/vehicle_in_geo_fence")
    SendQueryVehicleInGeoFence getVehicleInGeoFence(@RequestBody  ReceivedQueryVehicleInGeoFence receivedQueryVehicleInGeoFence){
        SendQueryVehicleInGeoFence sendQueryVehicleInGeoFence = new SendQueryVehicleInGeoFence(BaseSendMsg.SUCCESS);
        UUID idGeo = receivedQueryVehicleInGeoFence.getIdGeoFence();
        long timeStart = receivedQueryVehicleInGeoFence.getStartTime();
        long timeEnd = receivedQueryVehicleInGeoFence.getEndTime();
        try {
            sendQueryVehicleInGeoFence.setListIdVehicle(JavaConnect2SQL.getInstance().getVehicleInGeoFence(idGeo, timeStart, timeEnd));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sendQueryVehicleInGeoFence;
    }
}
