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
