package Dataconnect;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.model.Vehicle;

import java.sql.*;
import java.util.*;

public class JavaConnect2SQL {
    public  static JavaConnect2SQL instance = null;
//    url = "jdbc:sqlserver://" +serverName + ":1433;DatabaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;
    private static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=GEOFENCE;encrypt=true;trustServerCertificate=true";
    private static String user = "sa";
    private static String password = "Password.1";
    //Long
//    private static String url = "jdbc:sqlserver://DESKTOP-AMFKU1O:1433;DatabaseName=GEOFENCE;encrypt=true;trustServerCertificate=true";
//    private static String user = "sa";
//    private static String password = "1234";

    private Connection conn = null;
    private static short NUMBER_CONNECTION = 4;
    private static short CONNECTION_TO_VEHICLE_TABLE = 0;
    private static short CONNECTION_TO_POINT_TABLE = 1;
    private static short CONNECTION_TO_GEOFENCE_TABLE = 2;
    private static short CONNECTION_TO_VEHICLE_RECORD_TABLE = 3;
    private List<Connection> listConnection = new ArrayList<>();


    //get url
    public String get_url(){
        return this.url;
    }

    public void initListConnection(){
        for(int i = 0;i < NUMBER_CONNECTION;i++){
            Connection conn = this.getConnection(url, user, password);
            this.listConnection.add(conn);
        }
    }

    private Connection getConnectionById(short id){
        return this.listConnection.get(id);
    }

    public JavaConnect2SQL(){
        System.out.println("Connect....");
        this.initListConnection();
    }

    public void insertGeoFenceToDB(GeoFence geoFence) throws SQLException {
//        String ins_area_by_DataGenerator = "INSERT INTO Area (ID_Area, index_point, Latitude, Longitude) VALUES (?,?,?,?)";
        // insert geo fence into geo fence table (id, name:#color)
        String ins_geo_fence_by_DataGenerator = "INSERT INTO Geofence (ID_Geo, NAME_Geo) VALUES (?,?)";
        Connection geoFenceConnect = this.getConnectionById(CONNECTION_TO_GEOFENCE_TABLE);
        PreparedStatement pst = geoFenceConnect.prepareStatement(ins_geo_fence_by_DataGenerator);
        List<Point> listPoint = geoFence.getListPoint();
        pst.setString(1, geoFence.getId().toString());
        pst.setString(2, geoFence.getId().toString());
        pst.execute();

        String ins_geo_fence_point_by_DataGenerator = "INSERT INTO Point (ID_Point, Latitude, Longitude, ID_Geo) VALUES (?,?,?,?)";
        Connection pointTableConnection = this.getConnectionById(CONNECTION_TO_POINT_TABLE);
        pst = pointTableConnection.prepareStatement(ins_geo_fence_point_by_DataGenerator);
        for (int i = 0;i < listPoint.size();i++){
            String id = geoFence.getId().toString();
            Point point = listPoint.get(i);
            pst.setString(1,geoFence.getId().toString() + "_" + i);
            pst.setDouble(2,point.getX());
            pst.setDouble(3,point.getY());
            pst.setString(4, geoFence.getId().toString());
            pst.execute();
        }
    }

    public void insertVehicle_in_Geo (Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO Vehicle_in_Geo(ID_Geo, ID_Vehicle, Time_in) VALUES (?,?,?)";
        Connection connection = this.getConnectionById(CONNECTION_TO_VEHICLE_RECORD_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        List<UUID> listIdGeoFenceIn = vehicle.getListIdGeoFenceIn();
        for(int i = 0;i < listIdGeoFenceIn.size();i++){
            pst.setString(1, listIdGeoFenceIn.get(i).toString());
            pst.setString(2, vehicle.getId().toString());
            pst.setDouble(3, vehicle.getLastTimeSave());
            pst.execute();

        }
    }

    public void updatePointFromId(String id_geo, String id_point, double latitude, double longtitude) throws SQLException {
        String query = "UPDATE Point SET Latitude = ?, Longitude = ? WHERE ID_Geo = ? AND ID_Point = ?";
        Connection connection = this.getConnectionById(CONNECTION_TO_POINT_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setDouble(1,latitude);
        pst.setDouble(2,longtitude);
        pst.setString(3,id_geo);
        pst.setString(4,id_point);
        pst.execute();
    }

    public void updateVehicle(String id_vehicle, double latitude, double longitude) throws SQLException {
        String query = "UPDATE VEHICLE SET Latitude = ?, Longitude = ? WHERE ID_Vehicle = ?";
        Connection connection = this.getConnectionById(CONNECTION_TO_VEHICLE_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setDouble(1,latitude);
        pst.setDouble(2,longitude);
        pst.setString(3,id_vehicle);
        pst.execute();
    }


    public void deleteGeofenceFromDB(String id_geo) throws SQLException {
        String query1 = "DELETE FROM Point WHERE ID_Geo = ?";
        String query2 = "DELETE FROM Vehicle_in_Geo WHERE ID_Geo = ?";
        String query3 = "DELETE FROM Geofence WHERE ID_Geo = ?";
        Connection pointConnection = this.getConnectionById(CONNECTION_TO_POINT_TABLE);
        PreparedStatement pst1 = pointConnection.prepareStatement(query1);
        pst1.setString(1,id_geo);
        pst1.execute();
        Connection vehicleInGeoConnection = this.getConnectionById(CONNECTION_TO_VEHICLE_RECORD_TABLE);
        PreparedStatement pst2 = vehicleInGeoConnection.prepareStatement(query2);
        pst2.setString(1,id_geo);
        pst2.execute();
        Connection geoFenceConnection = this.getConnectionById(CONNECTION_TO_GEOFENCE_TABLE);
        PreparedStatement pst3 = geoFenceConnection.prepareStatement(query3);
        pst3.setString(1,id_geo);
        pst3.execute();
    }

    public Map<String, List<Point>> loadAllPointOfGeofenceFromDB() throws SQLException {
        String query = "SELECT P.ID_Point, G.ID_Geo, P.Latitude, P.Longitude FROM Geofence AS G INNER JOIN Point AS P ON P.ID_Geo = G.ID_Geo";
        Connection connection = this.getConnectionById(CONNECTION_TO_POINT_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        Map<String, List<Point>> geoFenceMap = new HashMap<>();
        while (rs.next()){
            String id_point = rs.getString("ID_Point");
            String id_geo = rs.getString("ID_Geo");
            double latitude = rs.getDouble("Latitude");
            double longitude = rs.getDouble("Longitude");
            if(!geoFenceMap.containsKey(id_geo)){
                List<Point> listPoint = new ArrayList<>();
                listPoint.add(new Point(latitude, longitude));
                geoFenceMap.put(id_geo, listPoint);
            }
            else{
                List<Point> listPoint = geoFenceMap.get(id_geo);
                listPoint.add(new Point(latitude, longitude));
            }
        }
        return geoFenceMap;
    }

    public List<Vehicle> loadAllVehicle() throws SQLException {
        String query = "select * from Vehicle";
        Connection connection = this.getConnectionById(CONNECTION_TO_VEHICLE_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        List<Vehicle> vehicles = new ArrayList<>();
        while(rs.next()){
            String ID_Vehicle = rs.getString("ID_Vehicle");
            double latitude = rs.getDouble("Latitude");
            double longitude = rs.getDouble("Longitude");
            double Vx = rs.getDouble("Vx");
            double Vy = rs.getDouble("Vy");
            Vehicle vehicle = new Vehicle(UUID.fromString(ID_Vehicle), "", new Point(latitude, longitude), Vx, Vy);
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    public void insertVehicleToDB(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO Vehicle (ID_Vehicle, Latitude, Longitude, Vx, Vy) VALUES (?,?,?,?,?)";
        Connection connection = this.getConnectionById(CONNECTION_TO_VEHICLE_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1,vehicle.getId().toString());
        pst.setDouble(2,vehicle.getCurPoint().getX());
        pst.setDouble(3,vehicle.getCurPoint().getY());
        pst.setDouble(4,vehicle.getVx());
        pst.setDouble(5,vehicle.getVy());
        pst.execute();
    }

    public List<UUID> getVehicleInGeoFence(UUID idGeo, long timeStart, long timeEnd) throws SQLException {
        String query = "select distinct ID_Vehicle from Vehicle_in_geo where ? <= Time_in and ? >= Time_in and ID_GEO = ?";
        Connection connection = this.getConnectionById(CONNECTION_TO_VEHICLE_RECORD_TABLE);
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(3, idGeo.toString());
        pst.setLong(1, timeStart);
        pst.setLong(2, timeEnd);
        ResultSet rs = pst.executeQuery();
        List<UUID> listIdVehicle = new ArrayList<>();
        while (rs.next()){
            listIdVehicle.add(UUID.fromString(rs.getString("ID_Vehicle")));
        }
        return listIdVehicle;
    }

    public static Connection getConnection(String url, String user, String password){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connect to DB");
        } catch (SQLException e) {
            System.out.println("Connection fail");
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static synchronized void createInstance(){
        JavaConnect2SQL.instance = new JavaConnect2SQL();
    }

    public static synchronized JavaConnect2SQL getInstance(){
        return JavaConnect2SQL.instance;
    }
}
