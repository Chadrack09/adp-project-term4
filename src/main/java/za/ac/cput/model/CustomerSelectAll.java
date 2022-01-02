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
public class CustomerSelectAll implements Serializable {
  
  private String query;

  public CustomerSelectAll(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return "CustomerSelectAll{" + "query=" + query + '}';
  }
  
}
