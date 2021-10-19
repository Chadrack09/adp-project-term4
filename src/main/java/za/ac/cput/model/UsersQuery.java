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
public class UsersQuery implements Serializable {

  private String query;
  private Users user;

  public UsersQuery(String query, Users user) {
    this.query = query;
    this.user = user;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "UsersQuery{" + "query=" + query + ", user=" + user + '}';
  }
}
