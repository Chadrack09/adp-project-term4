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
public class VenueQuery implements Serializable {
  
  private String query;
  private Venue venue;

  public VenueQuery(String query, Venue venue) {
    this.query = query;
    this.venue = venue;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Venue getVenue() {
    return venue;
  }

  public void setVenue(Venue venue) {
    this.venue = venue;
  }

  @Override
  public String toString() {
    return "VenueQuery{" + "query=" + query + ", venue=" + venue + '}';
  }
}
