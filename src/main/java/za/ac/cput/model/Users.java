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
public class Users implements Serializable {

  private int userId;
  private String userName;
  private String password;

  public Users() {/** empty constructor **/}

  public Users(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public Users(int userId, String userName, String password) {
    this.userId = userId;
    this.userName = userName;
    this.password = password;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Users{" + "userId=" + userId + ", userName=" + userName + ", "
            + "password=" + password + '}';
  }
}
