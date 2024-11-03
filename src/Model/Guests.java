package Model;

import java.io.Serializable;
import java.util.Date;

/*
Add methods and attributes as needed, JUST COMMUNICATE BEFORE DOING SO!!!
 */

public class Guests extends Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date dOB;

    public Guests() {
        super();
        dOB = new Date();
    }

    /**
     * Primary constructor for the Guests class
     *
     * @param id  Unique ID number for a user
     * @param name  Name of the guest (Nullable)
     * @param username  Guest's username (Nullable)
     * @param password  Guest's Password (Nullable)
     * @param dOB   Guest's date of birth
     */
    public Guests(int id, String name, String username, String password, Date dOB){
        super(id, name, username, password, "Guest");
        this.dOB = dOB;
    }

    /**
     * Primary constructor for the Guests class (Without ID Field)
     *
     * @param name  Name of the guest (Nullable)
     * @param username  Guest's username (Nullable)
     * @param password  Guest's Password (Nullable)
     * @param dOB   Guest's date of birth
     */
    public Guests(String name, String username, String password, Date dOB){
        super(name, username, password, "Guest");
        this.dOB = dOB;
    }

    public Date getdOB() {
        return dOB;
    }

    public void setdOB(Date dOB) {
        this.dOB = dOB;
    }
}
