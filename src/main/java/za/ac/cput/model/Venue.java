package za.ac.cput.model;

import java.sql.Date;

/**
 *
 * @author    Chadrack B. Boudzoumou
 * @email     219383847@mycput.ac.za
 * @student   219383847
 * @uni       Cape Peninsula University Of Technology
 * @since     Oct 7, 2021 | 2:03:15 AM
 *
 */
public class Venue {
  
  private int venueId;
  private String name;
  private String location;
  private String type;
  private String cost;
  private int maxGuest;
  private boolean availability;
  private Date date;

  public Venue(String name, String location, String type, String cost, int maxGuest, boolean availability, Date date) {
    this.name = name;
    this.location = location;
    this.type = type;
    this.cost = cost;
    this.maxGuest = maxGuest;
    this.availability = availability;
    this.date = date;
  }

  public Venue(int venueId, String name, String location, String type, String cost, int maxGuest, boolean availability, Date date) {
    this.venueId = venueId;
    this.name = name;
    this.location = location;
    this.type = type;
    this.cost = cost;
    this.maxGuest = maxGuest;
    this.availability = availability;
    this.date = date;
  }

  public int getVenueId() {
    return venueId;
  }

  public void setVenueId(int venueId) {
    this.venueId = venueId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  public int getMaxGuest() {
    return maxGuest;
  }

  public void setMaxGuest(int maxGuest) {
    this.maxGuest = maxGuest;
  }

  public boolean isAvailability() {
    return availability;
  }

  public void setAvailability(boolean availability) {
    this.availability = availability;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Venue{" + "venueId=" + venueId + ", name=" + name + ", location=" 
            + location + ", type=" + type + ", cost=" + cost + ", maxGuest=" 
            + maxGuest + ", availability=" + availability + ", date=" + date + '}';
  }
}
