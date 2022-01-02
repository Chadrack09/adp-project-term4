/********************************************
 *                                          *
 * Copyright © 2021 - Open Source           *
 * Cape Peninsula university Of Technology  *
 *                                          *
 ********************************************/

package za.ac.cput.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
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
 * @uni         Cape Peninsula University Of Technology
 * @since       Oct 6, 2021 | 10:40:52 PM
 *
 */
public class Client {

  public static void main(String[] args) throws ClassNotFoundException, 
          InstantiationException, IllegalAccessException, 
          UnsupportedLookAndFeelException  {
    
    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    new Client().APP_START.start();
  }

  private static final int SERVER_PORT = 2000;
  private static Socket socket;

  private static JFrame _MAIN_FRAME = new JFrame();
  private static JPanel _PANEL_NORTH = new JPanel();

  private static final JPanel _PANEL_CENTER = new JPanel();
  private static final JPanel PC_ONE = new JPanel();
  private static final JPanel PC_ONE_1 = new JPanel();
  private static final JPanel PC_ONE_2 = new JPanel();
  private static final JPanel PC_ONE_3 = new JPanel();
  private static final JLabel PC_LABEL_1 = new JLabel("Usertype");
  private static final JLabel PC_LABEL_2 = new JLabel("Username");
  private static final JLabel PC_LABEL_3 = new JLabel("Password");
  private static final JTextField PC_INPUT_1 = new JTextField("Text field", 25);
  private static final JTextField PC_INPUT_2 = new JTextField("Text field", 25);
  private static final String[] USERTYPES = {"Admin", "User"};
  private static final JComboBox PC_COMBOBOX = new JComboBox(USERTYPES);

  private static final JPanel _PANEL_SOUTH = new JPanel();
  private static final JButton PS_BTN_LOGIN = new JButton("Login");
  private static final JButton PS_BTN_EXIT = new JButton("Exit");
  private static final List<Admin> ADMIN_DATA = new ArrayList<>();

  private static ObjectInputStream inputStream;
  private static ObjectOutputStream outputStream;

  AppStart APP_START = () -> {
    connectToServer();
    setGUI();
  };

  private static void connectToServer() {
    try {
      socket = new Socket("localhost", SERVER_PORT);
      System.out.println("Connection to server established !!!");

      outputStream = new ObjectOutputStream(socket.getOutputStream());
      inputStream = new ObjectInputStream(socket.getInputStream());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void setGUI() {
    _MAIN_FRAME.setTitle("LOGIN");
    _MAIN_FRAME.getContentPane().setLayout(new BorderLayout());
    _MAIN_FRAME.setVisible(true);
    _MAIN_FRAME.pack();
    _MAIN_FRAME.setSize(new Dimension(720, 520));
    _MAIN_FRAME.setLocationRelativeTo(null);
    _MAIN_FRAME.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    _MAIN_FRAME.add(panelCenter(), BorderLayout.CENTER);
    _MAIN_FRAME.add(panelSouth(), BorderLayout.SOUTH);
  }

  private JPanel panelNorth() {
    _PANEL_NORTH.setBorder(new TitledBorder("Panel North"));
    return _PANEL_NORTH;
  }

  private JPanel panelCenter() {
    _PANEL_CENTER.setLayout(new BoxLayout(_PANEL_CENTER, BoxLayout.X_AXIS));

    _PANEL_CENTER.add(Box.createHorizontalStrut(50));
    _PANEL_CENTER.add(PC_ONE);
    _PANEL_CENTER.add(Box.createHorizontalStrut(50));

    PC_ONE.setLayout(new BoxLayout(PC_ONE, BoxLayout.Y_AXIS));

    PC_ONE.add(Box.createVerticalStrut(120));
    PC_ONE.add(PC_ONE_1);
    PC_ONE.add(PC_ONE_2);
    PC_ONE.add(PC_ONE_3);
    PC_ONE.add(Box.createVerticalStrut(120));

    PC_ONE_1.setLayout(new FlowLayout());
    PC_ONE_1.add(PC_LABEL_1);
    PC_ONE_1.add(PC_COMBOBOX);

    PC_ONE_2.setLayout(new FlowLayout());
    PC_ONE_2.add(PC_LABEL_2);
    PC_ONE_2.add(PC_INPUT_1);

    PC_ONE_3.setLayout(new FlowLayout());
    PC_ONE_3.add(PC_LABEL_3);
    PC_ONE_3.add(PC_INPUT_2);

    return _PANEL_CENTER;
  }

  private JPanel panelSouth() {

    _PANEL_SOUTH.setBorder(new TitledBorder("Panel South"));
    _PANEL_SOUTH.setLayout(new FlowLayout());

    _PANEL_SOUTH.add(PS_BTN_LOGIN);
    _PANEL_SOUTH.add(PS_BTN_EXIT);

    PS_BTN_EXIT.addActionListener(e -> {
      
      System.exit(0);
    });
    
    PS_BTN_LOGIN.addActionListener(e -> {
      if (PC_COMBOBOX.getSelectedItem() == USERTYPES[0]) {
        try {
          String adminName = PC_INPUT_1.getText();
          String password = PC_INPUT_2.getText();
          Admin a = new Admin(adminName, password);

          outputStream.writeObject(a);
          Object obj = inputStream.readObject();

          if (obj instanceof Boolean) {
            Boolean IS_AUTHENTIC = (Boolean) obj;

            if (IS_AUTHENTIC == true) {
              _MAIN_FRAME.setVisible(false);
              new AdminGUI();
            } else {
              JOptionPane.showMessageDialog(_MAIN_FRAME,
                      "Name and Password not matching", "Message Dialog",
                      JOptionPane.INFORMATION_MESSAGE);
            }
          }
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }
      if (PC_COMBOBOX.getSelectedItem() == USERTYPES[1]) {
        try {
          String userName = PC_INPUT_1.getText();
          String password = PC_INPUT_2.getText();
          Users u = new Users(userName, password);

          outputStream.writeObject(u);
          Object obj = inputStream.readObject();

          if (obj instanceof Boolean) {
            Boolean IS_AUTHENTIC = (Boolean) obj;

            if (IS_AUTHENTIC == true) {
              _MAIN_FRAME.setVisible(false);
              new UserGUI();
            } else {
              JOptionPane.showMessageDialog(_MAIN_FRAME,
                      "Name and Password not matching", "Message Dialog",
                      JOptionPane.INFORMATION_MESSAGE);
            }
          }
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }
    });
    return _PANEL_SOUTH;
  }

  private static class AdminGUI extends JFrame {

    private static final JPanel _PANEL_NORTH = new JPanel();
    private static final JPanel ADMIN_MAIN_PANEL = new JPanel();
    private static final JScrollPane scrollPane = new JScrollPane(
            ADMIN_MAIN_PANEL, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private static final JLabel PANEL_NORTH_LABEL = new JLabel("Admin Query");
    private static final JPanel _PANEL_CENTER = new JPanel();
    private static final JPanel PANEL_CENTER_P1 = new JPanel();

    private static final JPanel PC_P1_ONE = new JPanel();
    private static final JTextField PC_P1_INPUT1 = new JTextField("Venue name");
    private static final JTextField PC_P1_INPUT2 = new JTextField("Location");
    private static final JTextField PC_P1_INPUT3 = new JTextField("type");
    private static final JTextField PC_P1_INPUT4 = new JTextField("Cost");
    private static final JTextField PC_P1_INPUT5 = new JTextField("Max Guest");
    private static final JPanel PC_P1_PAVAILABLE = new JPanel();
    private static final JLabel PC_PAVAILABLE_LABEL = new JLabel("Available");
    private static final JCheckBox PC_PAVAILABLE_CHECK = new JCheckBox();
    private static final JTextField PC_P1_INPUT7 = new JTextField("Date");
    private static final JButton PC_ADD_VENUE_BTN = new JButton("Add new venue");

    private static final JPanel PANEL_CENTER_P2 = new JPanel();
    private static final JTextField PC_P2_INPUT1 = new JTextField("Username");
    private static final JTextField PC_P2_INPUT2 = new JTextField("Password");
    private static final JButton PC_ADD_USER_BTN = new JButton("Add new user");

    private static final JPanel PANEL_CENTER_P3 = new JPanel();
    private static final JLabel PC_P3_LABEL1 = new JLabel("Admin Query");
    private static final String[] PC_P3_TABLECOL = {"Venue Id", "Venue Name",
      "Location", "Type", "Cost", "Max Guest",
      "Availability", "Date"};
    private static JTable PC_P3_VENUE_TABLE = null;
    private static String[][] PC_P3_ROWS = null;

    private static JPanel PANEL_CENTER_P4 = new JPanel();
    private static final String[] PC_P4_TABLECOL = {"User id", "Username", "Password"};
    private static String[][] PC_P4_ROWS = null;
    private static JTable PC_P4_USER_TABLE = null;
    private static final JButton PC_ADDING_FORM = new JButton(
            "Click to see form to Add to Database");
    
    private static final JButton GO_BACK = new JButton(
            "Go back to login");

    private static JTextField PC_P4_INPUT1 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT2 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT3 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT4 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT5 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT6 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT7 = new JTextField("Update house");
    private static JTextField PC_P4_INPUT8 = new JTextField("Update house");
    private static JPanel PC_P4_UPDATE_PANEL = new JPanel();

    private static JTextField PC_P5_INPUT1 = new JTextField("Update User");
    private static JTextField PC_P5_INPUT2 = new JTextField("Update User");
    private static JTextField PC_P5_INPUT3 = new JTextField("Update User");
    private static JPanel PC_P5_UPDATE_USER = new JPanel();

    private static JButton PC_UPDATE_HOUSE = new JButton("Update House");
    private static JButton PC_UPDATE_USER = new JButton("Update User");

    public AdminGUI() {
      this.setTitle("Admin GUI");
      this.getContentPane().setLayout(new BorderLayout());
      this.setVisible(true);
      this.pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize(dimension);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.add(scrollPanel());
    }

    private JScrollPane scrollPanel() {

//      adminMainPanel.setBorder(new TitledBorder("New Border"));
      ADMIN_MAIN_PANEL.setLayout(new BorderLayout());
      ADMIN_MAIN_PANEL.add(setPanelNorth(), BorderLayout.NORTH);
      ADMIN_MAIN_PANEL.add(setPanelCenter(), BorderLayout.CENTER);

      return scrollPane;
    }

    private JPanel setPanelNorth() {

      _PANEL_NORTH.setLayout(new FlowLayout());
//      pn.setBorder(new TitledBorder("Panel North"));
      _PANEL_NORTH.add(PANEL_NORTH_LABEL);
      _PANEL_NORTH.add(GO_BACK);
      
      GO_BACK.addActionListener(e -> {
        
        this.setVisible(false);
        Client._MAIN_FRAME.setVisible(true);
      });
      
      return _PANEL_NORTH;
    }

    private JPanel setPanelCenter() {
      _PANEL_CENTER.setLayout(new BoxLayout(_PANEL_CENTER, BoxLayout.Y_AXIS));
//      pc.setBorder(new TitledBorder("Panel Center"));

      PANEL_CENTER_P1.setBorder(new TitledBorder("Add data to database"));
      PANEL_CENTER_P1.setLayout(new BoxLayout(PANEL_CENTER_P1, BoxLayout.PAGE_AXIS));
      PANEL_CENTER_P1.add(PC_ADD_VENUE_BTN, Box.CENTER_ALIGNMENT);
      _PANEL_CENTER.add(PANEL_CENTER_P1);

      PANEL_CENTER_P1.add(PC_ADDING_FORM);
      PANEL_CENTER_P1.add(Box.createVerticalStrut(20));
      PANEL_CENTER_P1.add(addNewVenue());
      PANEL_CENTER_P1.add(addNewUser());

      PANEL_CENTER_P3.setLayout(new BoxLayout(PANEL_CENTER_P3, BoxLayout.Y_AXIS));
      PANEL_CENTER_P3.setBorder(new TitledBorder("Update House"));

      PANEL_CENTER_P4.setLayout(new BoxLayout(PANEL_CENTER_P4, BoxLayout.Y_AXIS));
      PANEL_CENTER_P4.setBorder(new TitledBorder("Update User"));

      PC_ADDING_FORM.addActionListener(e -> {
        if (e.getSource() == PC_ADDING_FORM) {
          if (PC_P1_ONE.isVisible() && PANEL_CENTER_P2.isVisible()) {
            PC_P1_ONE.setVisible(false);
            PANEL_CENTER_P2.setVisible(false);
          } else {
            PC_P1_ONE.setVisible(true);
            PANEL_CENTER_P2.setVisible(true);
          }
        }
      });

      if (PC_P3_VENUE_TABLE == null) {
        try {
          outputStream.writeObject(new VenueSelectAll("select all"));

          List<Venue> list = new ArrayList<>();
          list = (ArrayList<Venue>) inputStream.readObject();

          PC_P3_ROWS = new String[list.size()][PC_P3_TABLECOL.length];

          if (inputStream != null) {
            if (list instanceof List) {
              for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < PC_P3_TABLECOL.length; j++) {
                  if (j == 0) {
                    PC_P3_ROWS[i][j] = String.valueOf(list.get(i).getVenueId());
                  }
                  if (j == 1) {
                    PC_P3_ROWS[i][j] = list.get(i).getName();
                  }
                  if (j == 2) {
                    PC_P3_ROWS[i][j] = list.get(i).getLocation();
                  }
                  if (j == 3) {
                    PC_P3_ROWS[i][j] = list.get(i).getType();
                  }
                  if (j == 4) {
                    PC_P3_ROWS[i][j] = list.get(i).getCost();
                  }
                  if (j == 5) {
                    PC_P3_ROWS[i][j] = String.valueOf(list.get(i).getMaxGuest());
                  }
                  if (j == 6) {
                    PC_P3_ROWS[i][j] = String.valueOf(list.get(i).isAvailability());
                  }
                  if (j == 7) {
                    PC_P3_ROWS[i][j] = String.valueOf(list.get(i).getDate());
                  }
                }
              }
            }
          }
          PC_P3_VENUE_TABLE = new JTable(PC_P3_ROWS, PC_P3_TABLECOL);

          PC_P3_VENUE_TABLE.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              int row = PC_P3_VENUE_TABLE.getSelectedRow();
              TableModel tm = PC_P3_VENUE_TABLE.getModel();
              String venueId = (String) tm.getValueAt(row, 0);
              String venueName = (String) tm.getValueAt(row, 1);
              String location = (String) tm.getValueAt(row, 2);
              String type = (String) tm.getValueAt(row, 3);
              String cost = (String) tm.getValueAt(row, 4);
              String maxGuest = (String) tm.getValueAt(row, 5);
              String availabiity = (String) tm.getValueAt(row, 6);
              String venueDate = (String) tm.getValueAt(row, 7);

              PC_P4_UPDATE_PANEL.setVisible(true);
              PC_P4_INPUT1.setText(venueId);
              PC_P4_INPUT2.setText(venueName);
              PC_P4_INPUT3.setText(location);
              PC_P4_INPUT4.setText(type);
              PC_P4_INPUT5.setText(cost);
              PC_P4_INPUT6.setText(maxGuest);
              PC_P4_INPUT7.setText(availabiity);
              PC_P4_INPUT8.setText(venueDate);
            }
          });

          PC_UPDATE_USER.addActionListener(e -> {
            String userId = PC_P5_INPUT1.getText();
            String userName = PC_P5_INPUT2.getText();
            String password = PC_P5_INPUT3.getText();

            Users u = new Users(Integer.parseInt(userId), userName, password);

            UsersUpdate uq = new UsersUpdate(u);

            try {
              outputStream.writeObject(uq);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          });
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }

      if (PC_P4_USER_TABLE == null) {
        try {
          outputStream.writeObject(new UsersSelectAll("select all"));
          List<Users> list = new ArrayList();
          list = (ArrayList<Users>) inputStream.readObject();

          PC_P4_ROWS = new String[list.size()][PC_P4_TABLECOL.length];

          if (inputStream != null) {
            if (list instanceof List) {
              for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < PC_P4_TABLECOL.length; j++) {
                  if (j == 0) {
                    PC_P4_ROWS[i][j] = String.valueOf(list.get(i).getUserId());
                  }
                  if (j == 1) {
                    PC_P4_ROWS[i][j] = list.get(i).getUserName();
                  }
                  if (j == 2) {
                    PC_P4_ROWS[i][j] = list.get(i).getPassword();
                  }
                }
              }
            }
          }
          PC_P4_USER_TABLE = new JTable(PC_P4_ROWS, PC_P4_TABLECOL);
        } catch (IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }

      PANEL_CENTER_P3.add(PC_UPDATE_HOUSE);

      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT1);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT2);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT3);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT4);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT5);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT6);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT7);
      PC_P4_UPDATE_PANEL.add(PC_P4_INPUT8);
      PC_P4_UPDATE_PANEL.setVisible(false);
      PANEL_CENTER_P3.add(PC_P4_UPDATE_PANEL);

      PC_UPDATE_HOUSE.addActionListener(e -> {
        String venueId = PC_P4_INPUT1.getText();
        String venueName = PC_P4_INPUT2.getText();
        String location = PC_P4_INPUT3.getText();
        String type = PC_P4_INPUT4.getText();
        String cost = PC_P4_INPUT5.getText();
        String maxGuest = PC_P4_INPUT6.getText();
        String availability = PC_P4_INPUT7.getText();
        String venueDate = PC_P4_INPUT8.getText();

        try {
          outputStream.writeObject(new Venue(Integer.parseInt(venueId),
                  venueName, location, type, cost, Integer.valueOf(maxGuest),
                  Boolean.valueOf(availability), Date.valueOf(venueDate)));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      });
      PANEL_CENTER_P3.add(new JScrollPane(PC_P3_VENUE_TABLE));
      _PANEL_CENTER.add(PANEL_CENTER_P3);

      PANEL_CENTER_P4.add(PC_UPDATE_USER);
      PANEL_CENTER_P4.add(PC_P5_UPDATE_USER);
      PC_P5_UPDATE_USER.setVisible(false);
      PC_P5_UPDATE_USER.add(PC_P5_INPUT1);
      PC_P5_UPDATE_USER.add(PC_P5_INPUT2);
      PC_P5_UPDATE_USER.add(PC_P5_INPUT3);

      PC_P4_USER_TABLE.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          int row = PC_P4_USER_TABLE.getSelectedRow();
          TableModel tm = PC_P4_USER_TABLE.getModel();

          PC_P5_UPDATE_USER.setVisible(true);
          String userId = (String) tm.getValueAt(row, 0);
          String userName = (String) tm.getValueAt(row, 1);
          String password = (String) tm.getValueAt(row, 2);

          PC_P5_INPUT1.setText(userId);
          PC_P5_INPUT2.setText(userName);
          PC_P5_INPUT3.setText(password);
        }
      });

      PANEL_CENTER_P4.add(new JScrollPane(PC_P4_USER_TABLE));
      _PANEL_CENTER.add(PANEL_CENTER_P4);

      return _PANEL_CENTER;
    }

    private JPanel addNewVenue() {

      PC_P1_ONE.setVisible(false);
      PC_P1_ONE.setBorder(new TitledBorder("Add new venue"));
      PC_P1_ONE.setLayout(new BoxLayout(PC_P1_ONE, BoxLayout.PAGE_AXIS));
      PC_P1_ONE.add(PC_P1_INPUT1, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(PC_P1_INPUT2, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(PC_P1_INPUT3, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(PC_P1_INPUT4, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(PC_P1_INPUT5, Box.CENTER_ALIGNMENT);

      PC_P1_PAVAILABLE.setLayout(new FlowLayout());
      PC_P1_PAVAILABLE.add(PC_PAVAILABLE_LABEL);
      PC_P1_PAVAILABLE.add(PC_PAVAILABLE_CHECK);

      PC_P1_ONE.add(PC_P1_PAVAILABLE, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(PC_P1_INPUT7, Box.CENTER_ALIGNMENT);
      PC_P1_ONE.add(Box.createVerticalStrut(20));
      PC_P1_ONE.add(PC_ADD_VENUE_BTN, Box.CENTER_ALIGNMENT);

      PC_ADD_VENUE_BTN.addActionListener(e -> {
        String venueName = PC_P1_INPUT1.getText();
        String venueLocation = PC_P1_INPUT2.getText();
        String venueType = PC_P1_INPUT3.getText();
        String venueCost = PC_P1_INPUT4.getText();
        String maxGuest = PC_P1_INPUT5.getText();
        boolean checkNumber = maxGuest.matches("[0-9]+");

        boolean availability = PC_PAVAILABLE_CHECK.isSelected();
        String date = PC_P1_INPUT7.getText();

        if (checkNumber && venueName != null && venueLocation != null
                && venueType != null && venueCost != null && maxGuest != null
                && date != null && !venueName.equals("Venue name")
                && !venueLocation.equals("Location") && !date.equals("Date")) {

          Venue v = new Venue(venueName, venueLocation, venueType, venueCost,
                  Integer.parseInt(maxGuest), availability, Date.valueOf(date));
          String query = PC_ADD_VENUE_BTN.getText().toLowerCase();

          VenueQuery vq = new VenueQuery(query, v);
          try {
            outputStream.writeObject(vq);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        } else {
          JOptionPane.showMessageDialog(null, "Please fill in with correct details");
        }
      });
      
      return PC_P1_ONE;
    }

    private JPanel addNewUser() {

      PANEL_CENTER_P2.setVisible(false);
      PANEL_CENTER_P2.setBorder(new TitledBorder("Add new user"));
      PANEL_CENTER_P2.setLayout(new BoxLayout(PANEL_CENTER_P2, BoxLayout.PAGE_AXIS));
      PANEL_CENTER_P2.add(PC_P2_INPUT1, Box.CENTER_ALIGNMENT);
      PANEL_CENTER_P2.add(PC_P2_INPUT2, Box.CENTER_ALIGNMENT);
      PANEL_CENTER_P2.add(Box.createVerticalStrut(20));
      PANEL_CENTER_P2.add(PC_ADD_USER_BTN, Box.CENTER_ALIGNMENT);

      PC_ADD_USER_BTN.addActionListener(e -> {

        String userName = PC_P2_INPUT1.getText();
        String password = PC_P2_INPUT2.getText();

        String query = PC_ADD_USER_BTN.getText().toLowerCase();
        Users u = new Users(userName, password);

        if (userName != null && password != null
                && !userName.equals("Username") && !password.equals("Password")) {
          try {
            outputStream.writeObject(new UsersQuery(query, u));
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      });
      return PANEL_CENTER_P2;
    }
  }

  private static class UserGUI extends JFrame {

    private static final JPanel _PANEL_NORTH = new JPanel();
    private static final JPanel _USER_MAIN_PANEL = new JPanel();
    private static final JScrollPane scrollPane = new JScrollPane(
            _USER_MAIN_PANEL, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private static final JLabel PN_LABEL1 = new JLabel("User Query");
    private static final JPanel _PANEL_CENTER = new JPanel();
    private static final JPanel PC_PANEL1 = new JPanel();

    private static final JPanel PC_P1_PANEL1 = new JPanel();
    private static final JTextField PC_P1_INPUT1 = new JTextField("Customer name");
    private static final JTextField PC_P1_INPUT2 = new JTextField("Venue name");
    private static final JTextField PC_P1_INPUT3 = new JTextField("Venue date");
    private static final JButton PC_ADD_CUST_BTN = new JButton("Add new customer");

    private static final JPanel PC_P1_PANEL2 = new JPanel();

    private static final JPanel PC_PANEL2 = new JPanel();
    private static final String[] PC_TABLE_COLUMNS = {"Customer Id",
      "Customer Name", "Venue Number", "Venue Date"};
    private static String[][] TBL_ROWS = null;
    private static JTable PC_TABLE = null;

    private static final JButton PC_ADD_FORM_BTN = new JButton(
            "Click to see form to Add to Database");
    private static final JButton GO_BACK = new JButton(
            "Go back to login");

//    private static JButton updateHouseBtn = new JButton("Update House");
//    private static JButton updateAgentBtn = new JButton("Update Agent");

    public UserGUI() {

      this.setTitle("User GUI");
      this.getContentPane().setLayout(new BorderLayout());
      this.setVisible(true);
      this.pack();
      Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
      this.setSize(dimension);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.add(scrollPanel());
    }

    private JScrollPane scrollPanel() {

      _USER_MAIN_PANEL.setBorder(new TitledBorder("New Border"));
      _USER_MAIN_PANEL.setLayout(new BorderLayout());
      _USER_MAIN_PANEL.add(setPanelNorth(), BorderLayout.NORTH);
      _USER_MAIN_PANEL.add(setPanelCenter(), BorderLayout.CENTER);

      return scrollPane;
    }

    private JPanel setPanelNorth() {

      _PANEL_NORTH.setLayout(new FlowLayout());
      _PANEL_NORTH.setBorder(new TitledBorder("Panel North"));
      _PANEL_NORTH.add(PN_LABEL1);
      _PANEL_NORTH.add(GO_BACK);
      
      GO_BACK.addActionListener(e -> {
        
        Client._MAIN_FRAME.setVisible(true);
        this.setVisible(false);
      });
      return _PANEL_NORTH;
    }

    private JPanel setPanelCenter() {
      _PANEL_CENTER.setLayout(new BoxLayout(_PANEL_CENTER, BoxLayout.Y_AXIS));
      _PANEL_CENTER.setBorder(new TitledBorder("Panel Center"));

      PC_PANEL1.setBorder(new TitledBorder("Panel One"));
      PC_PANEL1.setLayout(new BoxLayout(PC_PANEL1, BoxLayout.PAGE_AXIS));
      PC_PANEL1.add(PC_ADD_CUST_BTN, Box.CENTER_ALIGNMENT);
      _PANEL_CENTER.add(PC_PANEL1);

      PC_PANEL1.add(PC_ADD_FORM_BTN);
      PC_PANEL1.add(Box.createVerticalStrut(20));
      PC_PANEL1.add(addNewCustomer());

      PC_PANEL2.setLayout(new BoxLayout(PC_PANEL2, BoxLayout.Y_AXIS));
      PC_PANEL2.setBorder(new TitledBorder("Panel Two"));

//      pc_panel3.setLayout(new BoxLayout(pc_panel3, BoxLayout.Y_AXIS));
//      pc_panel3.setBorder(new TitledBorder("Panel Three"));


////            pc_panel3.add(updateAgentBtn);
//      pc_panel3.add(new JScrollPane(pc_table2));
//      pc.add(pc_panel3);

      PC_ADD_FORM_BTN.addActionListener(e -> {
        if (e.getSource() == PC_ADD_FORM_BTN) {
          if (PC_P1_PANEL1.isVisible() && PC_P1_PANEL2.isVisible()) {
            PC_P1_PANEL1.setVisible(false);
            PC_P1_PANEL2.setVisible(false);
          } else {
            PC_P1_PANEL1.setVisible(true);
            PC_P1_PANEL2.setVisible(true);
          }
        }
      });

      PC_ADD_CUST_BTN.addActionListener(e -> {
        String customerName = PC_P1_INPUT1.getText();
        String venueName = PC_P1_INPUT2.getText();
        String venueDate = PC_P1_INPUT3.getText();

        String query = PC_ADD_CUST_BTN.getText().toLowerCase();
        Customer c = new Customer(customerName, venueName, Date.valueOf(venueDate));
        if (customerName != null && !customerName.equals("Customer name")
                && venueName != null && !venueName.equals("Venue name") && venueDate != null) {
          CustomerQuery cq = new CustomerQuery(query, c);
          try {
            outputStream.writeObject(cq);
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      });

      if(PC_TABLE == null) {
        try {
          outputStream.writeObject(new CustomerSelectAll("select all"));
          
          List<Customer> list = new ArrayList();
          list = (ArrayList<Customer>) inputStream.readObject();

          TBL_ROWS = new String[list.size()][PC_TABLE_COLUMNS.length];
          
          if (inputStream != null) {
            if (list instanceof List) {
              for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < PC_TABLE_COLUMNS.length; j++) {
                  if (j == 0) {
                    TBL_ROWS[i][j] = String.valueOf(list.get(i).getCustomerId());
                  }
                  if (j == 1) {
                    TBL_ROWS[i][j] = list.get(i).getCustomerName();
                  }
                  if (j == 2) {
                    TBL_ROWS[i][j] = list.get(i).getVenueName();
                  }
                  if (j == 3) {
                    TBL_ROWS[i][j] = String.valueOf(list.get(i).getVenueDate());
                  }
                }
              }
            }
          }
          PC_TABLE = new JTable(TBL_ROWS, PC_TABLE_COLUMNS);
        }
        catch(IOException | ClassNotFoundException ex) {
          ex.printStackTrace();
        }
      }
      
      
//            pc_panel2.add(updateHouseBtn);
      PC_PANEL2.add(new JScrollPane(PC_TABLE));
      _PANEL_CENTER.add(PC_PANEL2);
      return _PANEL_CENTER;
    }

    private JPanel addNewCustomer() {

      PC_P1_PANEL1.setVisible(false);
      PC_P1_PANEL1.setBorder(new TitledBorder("Add new customer"));
      PC_P1_PANEL1.setLayout(new BoxLayout(PC_P1_PANEL1, BoxLayout.PAGE_AXIS));
      PC_P1_PANEL1.add(PC_P1_INPUT1, Box.CENTER_ALIGNMENT);
      PC_P1_PANEL1.add(PC_P1_INPUT2, Box.CENTER_ALIGNMENT);
      PC_P1_PANEL1.add(PC_P1_INPUT3, Box.CENTER_ALIGNMENT);

      PC_P1_PANEL1.add(Box.createVerticalStrut(20));
      PC_P1_PANEL1.add(PC_ADD_CUST_BTN, Box.CENTER_ALIGNMENT);

      return PC_P1_PANEL1;
    }
  }
  
  /**
   * Functional Interfaces
   */
  @FunctionalInterface
  private interface AppStart {
    public void start();
  }
}
