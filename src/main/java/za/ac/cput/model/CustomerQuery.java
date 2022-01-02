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
public class CustomerQuery implements Serializable {
  
  private String query;
  private Customer c;

  public CustomerQuery() {
  }

  public CustomerQuery(String query, Customer c) {
    this.query = query;
    this.c = c;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Customer getC() {
    return c;
  }

  public void setC(Customer c) {
    this.c = c;
  }

  @Override
  public String toString() {
    return "CustomerQuery{" + "query=" + query + ", c=" + c + '}';
  }
}
