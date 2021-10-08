package za.ac.cput.testing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 7, 2021 | 9:01:50 AM
 *
 */
public class Client {

  private static ObjectOutputStream outputStream;
  private static ObjectInputStream inputStream;
  private static Socket socket;
  private static byte[] b = {127, 0, 0, 1};
  private static BufferedOutputStream bw;
  private static BufferedInputStream br;
  
  public static void main(String[] args) throws IOException, ClassNotFoundException {

    socket = new Socket(InetAddress.getByAddress(b), 3000);
    outputStream = new ObjectOutputStream(socket.getOutputStream());
    inputStream = new ObjectInputStream(socket.getInputStream());
    
    User u = new User(1, "Prisca", "Ellis");
    while(true) {
      System.out.println("Client writing object -<User>-");
      outputStream.writeObject(u);
      outputStream.flush();
      System.out.println("Response from server");
      User user = (User) inputStream.readObject();
      System.out.println(user);
      if(outputStream != null) {
       outputStream.close();
      }
      if(inputStream != null) {
        inputStream.close();
      }
    }
  }
}
