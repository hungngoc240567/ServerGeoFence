package Dataconnect;

import com.Server.ServerGeoFence.SupportClass.Point;
import com.Server.ServerGeoFence.model.GeoFence;
import com.Server.ServerGeoFence.model.Vehicle;

import java.sql.*;
import java.util.*;

public class JavaConnect2SQL {
    public  static JavaConnect2SQL instance = null;
    //url = "jdbc:sqlserver://" +serverName + ":1433;DatabaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;
    private static String url = "jdbc:sqlserver://DESKTOP-AMFKU1O:1433;DatabaseName=GEOFENCE;encrypt=true;trustServerCertificate=true";
    private static String user = "sa";
    private static String password = "1234";

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


    public void updatePointFromId(String id, Integer index, double x, double y) throws SQLException {
        System.out.println("Update geo fence by id and index point " +id +" "+index);
        String query = "UPDATE Area SET Latitude = ?, Longitude = ? WHERE ID_Area = ? AND index_point = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setDouble(1,x);
        pst.setDouble(2,y);
        pst.setString(3,id);
        pst.setInt(4,index);
        pst.execute();
    }

    public void deleteGeofenceFromDB(String id) throws SQLException {
        System.out.println("Delete Geofence from DB");
        String query = "DELETE FROM Area WHERE ID_Area = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1,id);
        pst.execute();
    }
    public Map<String, List<Point>> loadAllGeoFenceFromDB() throws SQLException {
        //report
        String query = "SELECT * FROM Area";

        //run query
        //ResultSet rs = statement.executeQuery(query);
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        Map<String, List<Point>> geoFenceMap = new HashMap<>();
        //print result
        while (rs.next()){
            System.out.println("Get point from db");
            String id = rs.getString("ID_Area");
            double x = rs.getDouble("Latitude");
            double y = rs.getDouble("Longitude");
            if(!geoFenceMap.containsKey(id)){
                List<Point> listPoint = new ArrayList<>();
                listPoint.add(new Point(x, y));
                geoFenceMap.put(id, listPoint);
            }
            else{
                List<Point> listPoint = geoFenceMap.get(id);
                listPoint.add(new Point(x, y));
            }
        }

        return geoFenceMap;
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
        List<UUID> listGeoFenceIn = vehicle.getListIdGeoFenceIn();
        for(int i = 0;i < listGeoFenceIn.size();i++){
            pst.setString(1,vehicle.getId().toString());
            pst.setString(2,listGeoFenceIn.get(i).toString());
            pst.setLong(3,vehicle.getLastTimeSave());
            pst.execute();
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
