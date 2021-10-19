/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bbeno
 */
public class ServerTest {
  
  public ServerTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of main method, of class Server.
   */
  @Test
  public void testMain() {
    System.out.println("main");
    String[] args = null;
    Server.main(args);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of startServer method, of class Server.
   */
  @Test
  public void testStartServer() {
    System.out.println("startServer");
    Server instance = new Server();
    instance.startServer();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of receiveRequest method, of class Server.
   */
  @Test
  public void testReceiveRequest() {
    System.out.println("receiveRequest");
    Server instance = new Server();
    instance.receiveRequest();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}
