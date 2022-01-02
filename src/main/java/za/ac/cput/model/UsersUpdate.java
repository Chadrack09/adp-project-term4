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
