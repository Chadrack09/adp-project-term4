package za.ac.cput.client;

import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 8, 2021 | 10:05:21 AM
 *
 */
public class Test extends JFrame {

  JPanel panel_one = new JPanel();
  JPanel panel_two = new JPanel();
  JPanel panel_three = new JPanel();
  
  public Test() {
    
    this.setTitle("Client");
    this.getContentPane().setLayout(new FlowLayout());
    this.setVisible(true);
    this.pack();
    this.setSize(720, 580);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    panel_one.setBorder(new TitledBorder("Panel One"));
    panel_two.setBorder(new TitledBorder("Panel Two"));
    panel_three.setBorder(new TitledBorder("Panel Three"));
    this.add(panel_one);
    this.add(panel_two);
    this.add(panel_three);
//    
    panel_one.setLayout(new BoxLayout(panel_one, BoxLayout.Y_AXIS));
    panel_one.setLayout(new FlowLayout());
    panel_three.setLayout(new BoxLayout(panel_three, BoxLayout.Y_AXIS));
    
    panel_one.add(new JButton("Button 1"));
    panel_one.add(new JButton("Button 2"));
    panel_one.add(new JButton("Button 3"));
    
    panel_two.add(new JTextField("Text Field"));
    panel_two.add(new JTextField("Text Field"));
    
    panel_three.add(new JButton("Button 4"));
    panel_three.add(Box.createVerticalStrut(50));
    panel_three.add(new JButton("Button 5"));
    panel_three.add(new JButton("Button 6"));
  }
  
  public static void main(String[] args) {
    new Test();
  }
}
