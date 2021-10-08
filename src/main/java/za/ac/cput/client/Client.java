package za.ac.cput.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 6, 2021 | 10:40:52 PM
 *
 */
public class Client extends JFrame implements ActionListener {

  private static ObjectInputStream inputStream;
  private static ObjectOutputStream outputStream;
  private static int SERVER_PORT       = 2000;
  private static byte[] LOCAL_ADDRESS = {127, 0, 0, 1};
  private static Socket socket;
  private static ServerSocket server;
  
  private static JPanel panel_north = new JPanel();
  
  private static JPanel panel_center = new JPanel();
  private static JPanel pc_one = new JPanel();
  private static JPanel pcc_one = new JPanel();
  private static JPanel pcc_two = new JPanel();
  private static JPanel pcc_three = new JPanel();
  private static JLabel pc_label_one = new JLabel("Usertype");
  private static JLabel pc_label_two = new JLabel("Username");
  private static JLabel pc_label_three = new JLabel("Password");
  private static JTextField pc_text_one = new JTextField("Text field", 15);
  private static JTextField pc_text_two = new JTextField("Text field", 15);
  private static String[] usertype = {"Admin", "User", "Customer"};
  private static JComboBox pc_comboBox = new JComboBox(usertype);
  
  private static JPanel panel_south = new JPanel();
  private static JButton ps_btn_login = new JButton("Login");
  private static JButton ps_btn_exit = new JButton("Exit");
  
  public Client() {
    
//    connectToServer();
    this.setTitle("Client");
    this.getContentPane().setLayout(new BorderLayout());
    this.setVisible(true);
    this.pack();
    this.setSize(new Dimension(720, 520));
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
//    this.add(panelNorth(), BorderLayout.NORTH);
    this.add(panelCenter(), BorderLayout.CENTER);
    this.add(panelSouth(), BorderLayout.SOUTH);
  }
  
  private JPanel panelNorth() {
    
    panel_north.setBorder(new TitledBorder("Panel North"));
    return panel_north;
  }
  private JPanel panelCenter() {
    
    panel_center.setLayout(new BoxLayout(panel_center, BoxLayout.X_AXIS));
    
    panel_center.add(Box.createHorizontalStrut(50));
    panel_center.add(pc_one);
    panel_center.add(Box.createHorizontalStrut(50));
    
    pc_one.setLayout(new BoxLayout(pc_one, BoxLayout.Y_AXIS));
    
    pc_one.add(Box.createVerticalStrut(120));
    pc_one.add(pcc_one);
    pc_one.add(pcc_two);
    pc_one.add(pcc_three);
    pc_one.add(Box.createVerticalStrut(120));
  
    pcc_one.setLayout(new FlowLayout());
    pcc_one.add(pc_label_one);
    pcc_one.add(pc_comboBox);
  
    pcc_two.setLayout(new FlowLayout());
    pcc_two.add(pc_label_two);
    pcc_two.add(pc_text_one);
    
 
    pcc_three.setLayout(new FlowLayout());
    pcc_three.add(pc_label_three);
    pcc_three.add(pc_text_two);

    return panel_center;
  }
  private JPanel panelSouth() {
    
    panel_south.setBorder(new TitledBorder("Panel South"));
    panel_south.setLayout(new FlowLayout());
    
    panel_south.add(ps_btn_login);
    panel_south.add(ps_btn_exit);
    
    ps_btn_login.addActionListener(this);
    ps_btn_login.addActionListener(this);
    
    return panel_south;
  }
  
  private static void connectToServer() {
    try {
      socket = new Socket(InetAddress.getByAddress("localhost", LOCAL_ADDRESS), SERVER_PORT);
      System.out.println("Connection to server established !!!");
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == ps_btn_exit) {
      System.exit(0);
    }
    if(e.getSource() == ps_btn_login) {
      this.setVisible(false);
      if(pc_comboBox.getSelectedItem() == usertype[0]) {
        new AdminGUI();
      }
      else if(pc_comboBox.getSelectedItem() == usertype[1]) {
        new UserGUI();
      }
    }
  }
  
  private class AdminGUI extends JFrame {
    
    public AdminGUI() {
      
      this.setTitle("Admin GUI");
      this.getContentPane().setLayout(new BorderLayout());
      this.setVisible(true);
      this.pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize(dimension);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
  }
  private class UserGUI extends JFrame {
    
    public UserGUI() {
      
      this.setTitle("User GUI");
      this.getContentPane().setLayout(new BorderLayout());
      this.setVisible(true);
      this.pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize(dimension);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
  }
  
  public static void main(String[] args) {
    new Client();
  }
}
