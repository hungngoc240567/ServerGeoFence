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

    //get url
    public String get_url(){
        return this.url;
    }

    public JavaConnect2SQL(){
        System.out.println("Connect....");
        conn = this.getConnection(url, user, password);
    }

//    public static void main(String[] args) throws SQLException {
//        //
////        java.sql.Connection conn = null;
////        try {
////            Class.forName("org.sqlite.JDBC");
////            conn = DriverManager.getConnection("jdbc:sqlite:c:\sqlite\test.db");
//        //
////        JavaConnect2SQL object = new JavaConnect2SQL();
////        System.out.println(object.get_url());
//
//
//
////        //Connect to SQL server
////        Connection conn = null;
////        try {
////            conn = DriverManager.getConnection(url, user, password);
////            System.out.println("Connect to DB");
////        } catch (SQLException e) {
////            System.out.println("Connect fail!");
////            throw new RuntimeException(e);
////        }
//
//        Connection conn = getConnection(url, user, password);
//
//        //create statement
//        Statement statement = conn.createStatement();
//
//        //SQL query
//        //String sql = "SELECT * FROM clazz";
//
////        //query drop table
////        String drop_table_Area = "DROP TABLE Area";
////        String drop_table_Vehicle = "DROP TABLE Vehicle";
////
////        //drop table
////        statement.executeUpdate(drop_table_Area);
////        statement.executeUpdate(drop_table_Vehicle);
//
//
//        //table Area
//        String cre_area = "CREATE TABLE Area (ID_Area VARCHAR(100),Latitude INT NOT NULL,Longitude INT NOT NULL,CONSTRAINT PK_Area PRIMARY KEY(ID_Area))";
//        //table Vehicle
//        String cre_vehicle = "CREATE TABLE Vehicle (ID_Vehicle VARCHAR(100),Latitude INT NOT NULL,Longitude INT NOT NULL,ID_Area VARCHAR(100),CONSTRAINT PK_Vehicle PRIMARY KEY(ID_Vehicle))";
//
//        //insert data to table
//        //insert into [table] values (?,?)
//        String ins_area = "INSERT INTO Area (ID_Area, Latitude, Longitude) VALUES ('1AB3',2344656,34356)";
//        String ins_area_by_DataGenerator = "INSERT INTO Area (ID_Area, Latitude, Longitude) VALUES (?,?,?)";
//
//        //update table: create, drop, insert, update, delete, ...
//        //create table
//        System.out.println("Create new table");
//        System.out.println("- Table Area");
//        System.out.println("- Table Vehicle");
//        statement.executeUpdate(cre_area);
//        statement.executeUpdate(cre_vehicle);
//
//        //insert into table from a query
//        System.out.println("Insert into table ");
//        //statement.executeUpdate(ins_area);
//
//        //insert into table from data generator
//        PreparedStatement pst = conn.prepareStatement(ins_area_by_DataGenerator);
//        pst.setString(1,"100A");
//        pst.setInt(2,100);
//        pst.setInt(3,100);
//        //int rowCount = pst.executeUpdate();
//        pst.execute();
//        //System.out.println("Number of rows after insert: "+ rowCount);
//
//        //report
//        String query = "SELECT * FROM Area";
//
//        //run query
//        //ResultSet rs = statement.executeQuery(query);
//        pst = conn.prepareStatement(query);
//        ResultSet rs = pst.executeQuery();
//
//        //print result
//        while (rs.next()){
//            System.out.print(rs.getString("ID_Area") + "-"); // Can change "ID_Area" to 1
//            System.out.print(rs.getInt("Latitude") + "-");
//            System.out.print(rs.getInt("Longitude"));
//        }
//
////        //run query
////        ResultSet rs = statement.executeQuery(sql);
////
////        //print result
////        while (rs.next()) {
////            System.out.print(rs.getInt("id") + "-");
////            System.out.print(rs.getString("name") + "\n");
////        }
//
//
//        conn.close();
//    }

    public void insertGeoFenceToDB(GeoFence geoFence) throws SQLException {
//        String ins_area_by_DataGenerator = "INSERT INTO Area (ID_Area, index_point, Latitude, Longitude) VALUES (?,?,?,?)";
        // insert geo fence into geo fence table (id, name:#color)
        String ins_geo_fence_by_DataGenerator = "INSERT INTO Geofence (ID_Geo, NAME_Geo) VALUES (?,?)";
        PreparedStatement pst = conn.prepareStatement(ins_geo_fence_by_DataGenerator);
        List<Point> listPoint = geoFence.getListPoint();
        pst.setString(1, geoFence.getId().toString());
        pst.setString(2, geoFence.getId().toString());
        pst.execute();
        String ins_geo_fence_point_by_DataGenerator = "INSERT INTO Point (ID_Point, Latitude, Longitude, ID_Geo) VALUES (?,?,?,?)";
        pst = conn.prepareStatement(ins_geo_fence_point_by_DataGenerator);
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

    public void insertVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO Vehicle VALUES(?,?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, vehicle.getId().toString());
        pst.setDouble(2, vehicle.getCurPoint().getX());
        pst.setDouble(3, vehicle.getCurPoint().getY());
        pst.setDouble(4, vehicle.getVx());
        pst.setDouble(5, vehicle.getVy());
        pst.execute();
    }

    public void insertVehicle_in_Geo (Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO Vehicle_in_Geo(ID_Geo, ID_Vehicle, Time_in) VALUES (?,?,?)";
        PreparedStatement pst = conn.prepareStatement(query);
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
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setDouble(1,latitude);
        pst.setDouble(2,longtitude);
        pst.setString(3,id_geo);
        pst.setString(4,id_point);
        pst.execute();
    }

    public void updateVehicle(String id_vehicle, double latitude, double longitude) throws SQLException {
        String query = "UPDATE VEHICLE SET Latitude = ?, Longitude = ? WHERE ID_Vehicle = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setDouble(1,latitude);
        pst.setDouble(2,longitude);
        pst.setString(3,id_vehicle);
        pst.execute();
    }


    public void deleteGeofenceFromDB(String id_geo) throws SQLException {
        String query1 = "DELETE FROM Point WHERE ID_Geo = ?";
        String query2 = "DELETE FROM Vehicle_in_Geo WHERE ID_Geo = ?";
        String query3 = "DELETE FROM Geofence WHERE ID_Geo = ?";
        PreparedStatement pst1 = conn.prepareStatement(query1);
        pst1.setString(1,id_geo);
        pst1.execute();
        PreparedStatement pst2 = conn.prepareStatement(query2);
        pst2.setString(1,id_geo);
        pst2.execute();
        PreparedStatement pst3 = conn.prepareStatement(query3);
        pst3.setString(1,id_geo);
        pst3.execute();
    }

    public Map<String, List<Point>> loadAllPointOfGeofenceFromDB() throws SQLException {
        String query = "SELECT P.ID_Point, G.ID_Geo, P.Latitude, P.Longitude FROM Geofence AS G INNER JOIN Point AS P ON P.ID_Geo = G.ID_Geo";
        PreparedStatement pst = conn.prepareStatement(query);
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
        PreparedStatement pst = conn.prepareStatement(query);
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
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1,vehicle.getId().toString());
        pst.setDouble(2,vehicle.getCurPoint().getX());
        pst.setDouble(3,vehicle.getCurPoint().getY());
        pst.setDouble(4,vehicle.getVx());
        pst.setDouble(5,vehicle.getVy());
        pst.execute();
    }

    public void updateVehiclePointToDB(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO Vehicle_in_Geo (Vehicle_ID, Geofence_ID, V_Date) VALUES (?,?,?)";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        List<UUID> listGeoFenceIn = vehicle.getListIdGeoFenceIn();
        for(int i = 0;i < listGeoFenceIn.size();i++){
        }
    }

    public void get_vehicle_at_time() throws SQLException {
        //truyen tham so vao ? nhe
        String query = "select distinct ID_Vehicle from Vehicle_in_Geo where ? < Time_in and ? > Time_in";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        //print result
        while(rs.next()){
            String ID_Vehicle = rs.getString("ID_Vehicle");
        }
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

    public static synchronized JavaConnect2SQL getInstance(){
        if(JavaConnect2SQL.instance == null){
            JavaConnect2SQL.instance = new JavaConnect2SQL();
        }
        return JavaConnect2SQL.instance;
    }
}
