package za.ac.cput.testing;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 7, 2021 | 9:01:37 AM
 *
 */
public class Server {

  private static ObjectOutputStream output;
  private static ObjectInputStream input;
  private static Socket socket;
  private static ServerSocket server;
  private static byte[] b = {127, 0, 0, 1};
  
  public static void main(String[] args) throws UnknownHostException, 
          IOException, ClassNotFoundException {
    server = new ServerSocket(3000, 0, InetAddress.getByAddress(b));
    
    
    while(true) {
      socket  = server.accept();
      output  = new ObjectOutputStream(socket.getOutputStream());
      input   = new ObjectInputStream(socket.getInputStream());
      
      while(true) {
        User u = (User) input.readObject();
        System.out.println("Object from client");
        if(u != null) {
          System.out.println(u);
          output.writeObject(u);
        }
      }
    }
  }
}
