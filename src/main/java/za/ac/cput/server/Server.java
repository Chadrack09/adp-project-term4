package za.ac.cput.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import za.ac.cput.model.Customer;
import za.ac.cput.model.CustomerQuery;
import za.ac.cput.model.CustomerSelectAll;
import za.ac.cput.model.Users;
import za.ac.cput.model.UsersQuery;
import za.ac.cput.model.UsersSelectAll;
import za.ac.cput.model.UsersUpdate;
import za.ac.cput.model.Venue;
import za.ac.cput.model.VenueQuery;
import za.ac.cput.model.VenueSelectAll;

/**
 * 
 * @author Chadrack B. Boudzoumou
 * @author Tim Davids
 * 
 * @email 219383847@mycput.ac.za
 * @email 219296219@mycput.ac.za
 * 
 * @student 219296219
 * @student 219383847
 * 
 * @uni Cape Peninsula University Of Technology
 * @since Oct 6, 2021 | 10:40:52 PM
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
    customerDao = new CustomerDao();
  }
  
  private final DBInit dbinitialize;
  private final AdminDAO adminDao;
  private final UsersDao usersDao;
  private final VenueDao venueDao;
  private final CustomerDao customerDao;
  private ObjectInputStream   inputStream;
  private ObjectOutputStream  outputStream;
  private Connection con        = null;
  private Statement st          = null;
  private PreparedStatement ps  = null;
  private Socket socket;
  private ServerSocket server;
  private int SERVER_PORT       = 2000;
  
  /**
   * 
   * <p>Initialize server connection on TCP Port default (2000)</p>
   */
  public void startServer() { 
    try {
      
      Socket socket = new ServerSocket(SERVER_PORT).accept();
      System.out.println("Server waiting on port "+SERVER_PORT);
      
      inputStream = new ObjectInputStream(socket.getInputStream());
      outputStream = new ObjectOutputStream(socket.getOutputStream());
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  } 
  /**
   * 
   * <p>Receive request and send back response</p>
   */
  public void receiveRequest() {
    try {
      while(true) {     
        if(inputStream != null) {
          Object object = inputStream.readObject();
          
          if(object instanceof Admin) {
            Admin a = (Admin) object;
            Boolean IS_AUTHENTIC = adminDao.isAuthentic(a);
 
              System.out.println(a);
              System.out.printf("%n%s %s%n", "Is admin authentic:", 
                      String.valueOf(IS_AUTHENTIC));
              outputStream.writeObject(IS_AUTHENTIC);
          }
          if(object instanceof Users) {
            Users u = (Users) object;
            Boolean IS_AUTHENTIC = usersDao.isAuthentic(u);
 
              System.out.println(u);
              System.out.printf("%n%s %s%n", "Is User authentic?:", 
                      String.valueOf(IS_AUTHENTIC));
              outputStream.writeObject(IS_AUTHENTIC);
          }
          if(object instanceof VenueQuery) {
            VenueQuery vq = (VenueQuery) object;
            if(vq.getQuery().equals("add new venue")) {
              int row = venueDao.insertToDB(vq.getVenue());
              if(row > 0) {
                System.out.println("New Venue added to DB");
                System.out.println(vq.getVenue().toString());
                System.out.println("Row inserted");
              }
              else {
                System.out.printf("Could not insert row");
              }
            }
          }
          if(object instanceof UsersQuery) {
            UsersQuery uq = (UsersQuery) object;
            if(uq.getQuery().equals("add new user")) {
              int row = usersDao.insertToDB(uq.getUser());
              if(row > 0) {
                System.out.println("New User added to DB");
                System.out.println(uq.getUser().toString());
                System.out.println("Row inserted");
              }
              else {
                System.out.println("Could not insert row");
              }
            }
          }
          if(object instanceof VenueSelectAll) {
            VenueSelectAll vsa = (VenueSelectAll) object;
            if(vsa.getSendQuery().equals("select all")) {
              List<Venue> list = venueDao.selectAll();
              outputStream.writeObject(list);
            }
          }
          if(object instanceof UsersSelectAll) {
            UsersSelectAll usa = (UsersSelectAll) object;
            if(usa.getSendQuery().equals("select all")) {
              List<Users> list = usersDao.selectAll();
              outputStream.writeObject(list);
              System.out.println("Users Select All");
              System.out.println(list);
            }
          }
          if(object instanceof CustomerQuery) {
            CustomerQuery cq = (CustomerQuery) object;
            
            String query = cq.getQuery();
            if(query.equals("add new customer")) {
              int row = customerDao.insertToDB(cq.getC());
              if(row > 0) {
                System.out.println("New User added to DB");
                System.out.println(cq.getC().toString());
                System.out.println("Row inserted");
              }
              else {
                System.out.println("Could not insert row");
              }
            }
          }
          if(object instanceof Venue) {
            Venue v = (Venue) object;
            int update = venueDao.update(v);
            if(update > 0) {
              System.out.println("Updating table venue");
              System.out.println(v);
              System.out.println("Row updated");
            }
          }
          if(object instanceof UsersUpdate) {
            UsersUpdate uq = (UsersUpdate) object;
            Users u = uq.getU();
            int row = usersDao.update(u);
            if(row > 0) {
              System.out.println("Row updated");
              System.out.println(uq.getU());
            }
            else {
              System.out.println("Could not update row");
            }
          }
          if(object instanceof CustomerSelectAll) {
            CustomerSelectAll csq = (CustomerSelectAll) object;
            
            if(csq.getQuery().equals("select all")) {
              List<Customer> list = customerDao.selectAll();
              outputStream.writeObject(list);
            }
          }
        }
      }
    }
    catch(Exception ex) {
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
          st.executeUpdate("CREATE TABLE APP.users ("
                  + "userid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                  + "username VARCHAR(255), password VARCHAR(255))");
        }
        if(!getDBTables().contains("admin")) {
          st.executeUpdate("CREATE TABLE APP.admin ("
                  + "adminid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                  + "adminname VARCHAR(255), password VARCHAR(255))");
        }
 
        if(!getDBTables().contains("venue")) {
          st.executeUpdate("CREATE TABLE APP.venue("
                  + "venueid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                  + "name VARCHAR(255), location VARCHAR(255), type VARCHAR(255), "
                  + "cost VARCHAR(255), maxguest INT, availability BOOLEAN, date DATE)");
        }
        
        if(!getDBTables().contains("customer")) {
          st.executeUpdate("CREATE TABLE APP.customer("
                  + "customerid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                  + "customername VARCHAR(255), venuename VARCHAR(255), venuedate DATE)");
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
        
        if(customerDao.isTableEmpty()) {
          System.out.println("Table <Customer> empty seeding table...");
          Customer c1 = new Customer("Serge", "Virgin Active", Date.valueOf("2021-10-10"));
          Customer c2 = new Customer("Ellis", "Food Restore", Date.valueOf("2021-10-20"));
          
          customerDao.insertToDB(c1);
          customerDao.insertToDB(c2);
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
    
    public Admin selectById(Admin a) throws SQLException {
      
      String sql = "SELECT * FROM APP.admin WHERE adminname = ? AND password = ?";
      List<Admin> list = new ArrayList<>();
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(1, a.getAdminName());
      ps.setString(2, a.getPassword());
      
      ResultSet rs = ps.executeQuery();
      if(rs != null && rs.next()) {
        Admin admin = new Admin(rs.getString("adminname"), 
                rs.getString("password"));
        return admin;
      }
      else {
        return null;
      }
    }
    
    public boolean isAuthentic(Admin a) throws SQLException {
      
      if(selectById(a) != null 
              && a.getAdminName().equals(selectById(a).getAdminName()) 
              && a.getPassword().equals(selectById(a).getPassword())) {
          return true;
        }
        else {
          return false;
        }
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
    
    public int insertToDB(Users u) {
      
      String sql = "INSERT INTO APP.users (username, password) VALUES (?, ?)";
      int row = 0;
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        ps.setString(2, u.getPassword());
        
        row = ps.executeUpdate();
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
      return row;
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
    public Users selectById(Users u) throws SQLException {
      
      String sql = "SELECT * FROM APP.users WHERE username = ? AND password = ?";
      List<Users> list = new ArrayList<>();
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(1, u.getUserName());
      ps.setString(2, u.getPassword());
      
      ResultSet rs = ps.executeQuery();
      if(rs != null && rs.next()) {
        Users user = new Users(rs.getString("username"), rs.getString("password"));
        return user;
      }
      else {
        return null;
      }
    }
    public boolean isAuthentic(Users u) throws SQLException {
      
      if(selectById(u) != null
              && u.getUserName().equals(selectById(u).getUserName())
              && u.getPassword().equals(selectById(u).getPassword())) {
          return true;
        }
        else {
          return false;
        }
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
    public int update(Users u) {
      
      String sql = "UPDATE APP.users SET username = ?, password = ? WHERE userid = ?";
      int update = 0;
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, u.getUserName());
        ps.setString(2, u.getPassword());
        ps.setInt(3, u.getUserId());
        
        update = ps.executeUpdate();
        if(update > 0) {
          System.out.println("Row updated");
        }
        else {
          System.out.println("Could not update row");
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return update;
    }
  } 
  private class VenueDao {
    public int insertToDB(Venue venue) {
      String sql = "INSERT INTO APP.venue "
              + "(name, location, type, cost, maxguest, availability, date) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      int row = 0;
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, venue.getName());
        ps.setString(2, venue.getLocation());
        ps.setString(3, venue.getType());
        ps.setString(4, venue.getCost());
        ps.setInt(5, venue.getMaxGuest());
        ps.setBoolean(6, venue.isAvailability());
        ps.setDate(7, venue.getDate());
        
        row = ps.executeUpdate();
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
      return row;
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
    
    public int update(Venue v) {
      
      String sql = "UPDATE APP.venue SET name = ?, location = ?, type = ?, "
              + "cost = ?, maxguest = ?, availability = ?, date = ? WHERE venueid = ?";
      int update = 0;
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, v.getName());
        ps.setString(2, v.getLocation());
        ps.setString(3, v.getType());
        ps.setString(4, v.getCost());
        ps.setInt(5, v.getMaxGuest());
        ps.setBoolean(6, v.isAvailability());
        ps.setDate(7, v.getDate());
        ps.setInt(8, v.getVenueId());
        
        update = ps.executeUpdate();
        if(update > 0) {
          System.out.println("Row updated");
        }
        else {
          System.out.println("Could not update row");
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return update;
    }
  }
  private class CustomerDao {
    
    public boolean isTableEmpty() throws SQLException {
      
      String sql = "SELECT * FROM APP.customer";
      PreparedStatement ps = con.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        return false;
      }
      else {
        return true;
      }
    }
    
    public List<Customer> selectAll() {
      
      String sql = "SELECT * FROM APP.customer";
      List<Customer> list = new ArrayList<>();
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Customer c = new Customer(rs.getInt("customerid"), rs.getString("customername"), 
                  rs.getString("venuename"), rs.getDate("venuedate"));
          list.add(c);
        }
      }
      catch(SQLException ex) {
        ex.printStackTrace();
      }
      return list;
    }
    
    public int insertToDB(Customer c) {
      String sql = "INSERT INTO APP.customer (customername, venuename, venuedate) "
              + "VALUES (?, ?, ?)";
      int row = 0;
      try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getCustomerName());
        ps.setString(2, c.getVenueName());
        ps.setDate(3, c.getVenueDate());
        
        row = ps.executeUpdate();
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
      return row;
    }
  }
}
