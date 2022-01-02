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
public class UsersSelectAll implements Serializable {
  
  private String sendQuery;

  public UsersSelectAll(String sendQuery) {
    this.sendQuery = sendQuery;
  }

  public String getSendQuery() {
    return sendQuery;
  }

  public void setSendQuery(String sendQuery) {
    this.sendQuery = sendQuery;
  }

  @Override
  public String toString() {
    return "UsersSelectAll{" + "sendQuery=" + sendQuery + '}';
  }
}
