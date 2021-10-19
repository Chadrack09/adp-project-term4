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
