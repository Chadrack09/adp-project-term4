package za.ac.cput.model;

import java.io.Serializable;

/**
 *
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
public class Admin implements Serializable {

  private int adminId;
  private String adminName;
  private String password;

  public Admin(String adminName, String password) {
    this.adminName = adminName;
    this.password = password;
  }

  public Admin(int adminId, String adminName, String password) {
    this.adminId = adminId;
    this.adminName = adminName;
    this.password = password;
  }

  public int getAdminId() {
    return adminId;
  }

  public void setAdminId(int adminId) {
    this.adminId = adminId;
  }

  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Admin{" + "adminId=" + adminId + ", adminName=" + adminName + ", "
            + "password=" + password + '}';
  }
}
