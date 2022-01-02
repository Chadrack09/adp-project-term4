/********************************************
 *                                          *
 * Copyright © 2021 - Open Source           *
 * Cape Peninsula university Of Technology  *
 *                                          *
 ********************************************/
package za.ac.cput.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * @university    Cape Peninsula University Of Technology
 * @since         Oct 6, 2021 | 10:40:52 PM
 *
 */
public class Customer implements Serializable {

  private int customerId;
  private String customerName;
  private String venueName;
  private Date venueDate;

  public Customer() {/** empty constructor **/}

  public Customer(String customerName, String venueName, Date venueDate) {
    this.customerName = customerName;
    this.venueName = venueName;
    this.venueDate = venueDate;
  }

  public Customer(int customerId, String customerName, String venueName, Date venueDate) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.venueName = venueName;
    this.venueDate = venueDate;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  public Date getVenueDate() {
    return venueDate;
  }

  public void setVenueDate(Date venueDate) {
    this.venueDate = venueDate;
  }

  @Override
  public String toString() {
    return "Customer{" + "customerId=" + customerId + ", customerName=" + 
            customerName + ", venueName=" + venueName + ", venueDate=" + venueDate + '}';
  }
  
}
