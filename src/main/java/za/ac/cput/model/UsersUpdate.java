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
public class UsersUpdate implements Serializable {
    private Users u;

    public UsersUpdate() {
    }

    public UsersUpdate(Users u) {
        this.u = u;
    }

    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    @Override
    public String toString() {
        return "UsersUpdate{" + "u=" + u + '}';
    }
}
