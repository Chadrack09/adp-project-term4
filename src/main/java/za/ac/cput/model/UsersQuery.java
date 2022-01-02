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
public class UsersQuery implements Serializable {

  private String query;
  private Users user;

  public UsersQuery(String query, Users user) {
    this.query = query;
    this.user = user;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "UsersQuery{" + "query=" + query + ", user=" + user + '}';
  }
}
