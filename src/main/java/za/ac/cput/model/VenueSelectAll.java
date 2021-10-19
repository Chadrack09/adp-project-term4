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
