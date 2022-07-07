package com.Server.ServerGeoFence;
import Dataconnect.JavaConnect2SQL;
import com.Server.ServerGeoFence.SupportClass.Edge;
import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.SupportClass.RandomPolygon;
import com.Server.ServerGeoFence.TestClass.GeoFencePerformanceTest;
import com.Server.ServerGeoFence.TestClass.GeoFenceReportMeasure;
import com.Server.ServerGeoFence.Utils.Printer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ServerGeoFenceApplication {

	public static void main(String[] args) {
		JavaConnect2SQL.createInstance();
		SpringApplication.run(ServerGeoFenceApplication.class, args);
//		GeoFencePerformanceTest tester = GeoFencePerformanceTest.getInstance();
//		tester.test();
	}
}

