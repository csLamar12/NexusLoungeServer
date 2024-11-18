package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/*
Add methods and attributes as needed, JUST COMMUNICATE BEFORE DOING SO!!!
 */

public class Guests extends Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date dOB;
    private int age;

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

    public int getAge(){
        return age;
    }
    public void calculateAge() {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(dOB);

        Calendar today = Calendar.getInstance();

        int years = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        // Adjust if the birth date has not occurred yet this year
        if (today.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            years--;
        }

        this.age = years;
    }
    public void setAge(int age){
        this.age = age;
    }
}
