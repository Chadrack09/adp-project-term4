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
public class VenueSelectAll implements Serializable {
  
  private String sendQuery;

  public VenueSelectAll(String sendQuery) {
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
    return "VenueSelectAll{" + "sendQuery=" + sendQuery + '}';
  }
}
