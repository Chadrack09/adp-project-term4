package za.ac.cput.model;

import java.io.Serializable;

/**
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
