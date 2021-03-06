/********************************************
 *                                          *
 * Copyright © 2021 - Open Source           *
 * Cape Peninsula university Of Technology  *
 *                                          *
 ********************************************/
package za.ac.cput.model;

import java.io.Serializable;

/**
 * 
 * @university    Cape Peninsula University Of Technology
 * @since         Oct 6, 2021 | 10:40:52 PM
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
