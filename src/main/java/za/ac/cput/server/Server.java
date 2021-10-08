package za.ac.cput.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.model.Admin;
import za.ac.cput.model.Users;
import za.ac.cput.model.Venue;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 6, 2021 | 3:03:29 PM
 *
 */
public class Server {
  
  public static void main(String[] args) {
    Server server = new Server();
    
    server.createDBConnection();
    server.dbinitialize.dbinit();
    server.startServer();
    server.receiveRequest();
  }
  public Server() {
    dbinitialize = new DBInit();
    adminDao = new AdminDAO();
    usersDao = new UsersDao();
    venueDao = new VenueDao();
  }
  
  private final DBInit dbinitialize;
  private final AdminDAO adminDao;
  private final UsersDao usersDao;
  private final VenueDao venueDao;
  private ObjectInputStream   inputStream;
  private ObjectOutputStream  outputStream;
  private Connection con        = null;
  private Statement st          = null;
  private PreparedStatement ps  = null;
  private Socket socket;
  private ServerSocket server;
  private OutputStreamWriter writer;

  private int SERVER_PORT       = 2000;
  private byte[] LOCAL_ADDRESS = {127, 0, 0, 1};
  
  /**
   * 
   * <p>Initialize server connection on TCP Port default (2000)</p>
   */
  public void startServer() {
    
    try {
      System.out.println("Server waiting on port "+SERVER_PORT);
      
      server = new ServerSocket(SERVER_PORT, 0, InetAddress.getByAddress("localhost", LOCAL_ADDRESS));
      socket = server.accept();
      
      inputStream = new ObjectInputStream(socket.getInputStream());
      outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   *
   * <p>Create a Derby database connection.</p>
   */
  private void createDBConnection() {
    
    try {
      System.out.println("Connecting to database...");
      String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
      String DB_URL = "jdbc:derby:src/main/resources/databases/dbooking;create=true";
      
      Class.forName(DERBY_DRIVER);
      con = DriverManager.getConnection(DB_URL);
      System.out.println("Connection sucessful !!!");
    }
    catch(ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    catch(SQLException ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * <p>Close Derby database connection</p>
   */
  private void closeDBConnection() {
    try {
      System.out.println("Closing DB Connection");
      if(st != null) {
        st.close();
      }
      if(con != null) {
        con.close();
      }
    }
    catch(SQLException ex) {
      System.err.println("Error closing DB connection");
      ex.printStackTrace();
    }
  }
  
  /**
   * 
   * <p>Receive input or output data</p>
   */
  
  public void receiveRequest() {
    try {
      while(true) {
        writer = new OutputStreamWriter(socket.getOutputStream());
        
        BufferedWriter bw = new BufferedWriter(writer);        
        bw.write("This is from server");
        bw.newLine();
        bw.flush();
      }
    }
    catch(Exception ex) {
      
    }
  }
  
  private class DBInit {
    
    public void dbinit() {
      getDBTables();
      seedDatabase();
      seedTables();
    }
    
    public List<String> getDBTables() {
      List<String> list = null;
      try {
        DatabaseMetaData  dbms = con.getMetaData();
        ResultSet rs = dbms.getTables(null, null, null, new String[]{"TABLE"});
        list = new ArrayList<>();
        while(rs.next()) {
          list.add(rs.getString("TABLE_NAME").toLowerCase());
        }
        System.out.print("Table name: ");
        System.out.println(list);
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return list;
    }
    
    private void seedDatabase() {
      System.out.println("---[ Seeding database ]---");
      try {
        st = con.createStatement();
        
        if(!getDBTables().contains("users")) {
          st.executeUpdate("CREATE TABLE APP.users (userid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, username VARCHAR(255), password VARCHAR(255))");
        }
        if(!getDBTables().contains("admin")) {
          st.executeUpdate("CREATE TABLE APP.admin (adminid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, adminname VARCHAR(255), password VARCHAR(255))");
        }
        
        if(!getDBTables().contains("customer")) {
          st.executeUpdate("CREATE TABLE APP.customer(customerid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, customername VARCHAR(255), phone VARCHAR(255))");
        }
        
        if(!getDBTables().contains("venue")) {
          st.executeUpdate("CREATE TABLE APP.venue(venueid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name VARCHAR(255), location VARCHAR(255), type VARCHAR(255), cost VARCHAR(255), maxguest INT, availability BOOLEAN, date DATE)");
        }
        
        
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
    }
    
    private void seedTables() {
      try {
        System.out.print("\nIs Table Admin Empty?: ");
        System.out.println(adminDao.isTableEmpty());
        
        Admin admin1 = new Admin("Sarah", "abc@123");
        Admin admin2 = new Admin("Mark", "abc@123");
        
        if(adminDao.isTableEmpty()) {
          System.out.println("Table <Admin> empty seeding table...");
          adminDao.insertToDB(admin1);
          adminDao.insertToDB(admin2);
        }
        else {
          System.out.println("Table <Admin> not empty");
          System.out.print("Full List: ");
          System.out.println(adminDao.selectAll());
        }
        
        System.out.print("\nIs Table Users Empty?: ");
        System.out.println(usersDao.isTableEmpty());
        if(usersDao.isTableEmpty()) {
          
          System.out.println("Table <Users> empty seeding table...");
          Users u1 = new Users("Serge", "user@123");
          Users u2 = new Users("Ellis", "user@123");
          usersDao.insertToDB(u1);
          usersDao.insertToDB(u2);
        }
        else {
          System.out.println("Table <Users> not empty");
          System.out.println(usersDao.selectAll());
        }
        
        System.out.print("\nIs Table Venue Empty?: ");
        System.out.println(venueDao.isTableEmpty());
        if(venueDao.isTableEmpty()) {
          System.out.println("Table <Venue> empty seeding table...");
          Venue venue1 = new Venue("Food Restore", "Cape Town", "Restaurant", 
                  "1254", 45, true, Date.valueOf("2021-10-20"));
          Venue venue2 = new Venue("Virgin Active", "Cape Town", "Gym", 
                  "7552", 60, true, Date.valueOf("2021-10-10"));
          venueDao.insertToDB(venue1);
          venueDao.insertToDB(venue2);
        }
        else {
          System.out.println("Table <Venue> not empty");
          System.out.println(venueDao.selectAll());
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  private class AdminDAO {
    
    public void insertToDB(Admin admin) {
      
      String sql = "INSERT INTO APP.admin (adminname, password) VALUES (?, ?)";
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, admin.getAdminName());
        ps.setString(2, admin.getPassword());
        
        int result = ps.executeUpdate();
        if(result > 0) {
          System.out.println("Row inserted");
          ps.close();
        }
        else {
          System.out.println("Could not insert row");
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
    }
    
    public List<Admin> selectAll() {
      
      String sql = "SELECT * FROM APP.admin";
      List<Admin> list = new ArrayList<>();
      try {
        
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Admin a = new Admin(rs.getInt("adminid"), 
                  rs.getString("adminname"), rs.getString("password"));
          list.add(a);
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return list;
    }
    
    public boolean isTableEmpty() throws SQLException {
      
      String sql = "SELECT * FROM APP.admin";
      ResultSet rs = null;
      PreparedStatement ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      if(rs.next()) {
        return false;
      }
      else {
        return true;
      }  
    }
  }
  
  private class UsersDao {
    
    public void insertToDB(Users u) {
      
      String sql = "INSERT INTO APP.users (username, password) VALUES (?, ?)";
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        ps.setString(2, u.getPassword());
        
        int row = ps.executeUpdate();
        if(row > 0) {
          System.out.println("Row inserted");
          ps.close();
        }
        else {
          System.out.println("Could not insert row");
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
    }
    
    public List<Users> selectAll() {
      
      String sql = "SELECT * FROM APP.users";
      List<Users> list = new ArrayList<>();
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Users u = new Users(rs.getInt("userid"), 
                  rs.getString("username"), rs.getString("password"));
          list.add(u);
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return list;
    }
    
    public boolean isTableEmpty() throws SQLException {
      
      String sql = "SELECT * FROM APP.users";
      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        return false;
      }
      else {
        return true;
      }
    }
  }
  
  private class VenueDao {
    
    public void insertToDB(Venue venue) {
      String sql = "INSERT INTO APP.venue "
              + "(name, location, type, cost, maxguest, availability, date) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, venue.getName());
        ps.setString(2, venue.getLocation());
        ps.setString(3, venue.getType());
        ps.setString(4, venue.getCost());
        ps.setInt(5, venue.getMaxGuest());
        ps.setBoolean(6, venue.isAvailability());
        ps.setDate(7, venue.getDate());
        
        int row = ps.executeUpdate();
        if(row > 0) {
          System.out.println("Row inserted");
          ps.close();
        }
        else {
          System.out.println("Could not insert row");
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
    }
    
    public List<Venue> selectAll() {
      
      String sql = "SELECT * FROM APP.venue";
      List<Venue> list = new ArrayList<>();
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Venue venue = new Venue(rs.getInt("venueid"), rs.getString("name"), 
                  rs.getString("location"), rs.getString("type"), rs.getString("cost"), 
                  rs.getInt("maxguest"), rs.getBoolean("availability"), rs.getDate("date"));
          list.add(venue);
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return list;
    }
    
    public boolean isTableEmpty() throws SQLException {
      
      String sql = "SELECT * FROM APP.venue";
      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        return false;
      }
      else {
        return true;
      }
    }
  }
}
