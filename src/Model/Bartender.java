package Model;


/*
Add methods and attributes as needed, JUST COMMUNICATE BEFORE DOING SO!!!
 */
public class Bartender extends Users {

    public Bartender() {
        super();
    }

    /**
     * Primary constructor for the Guests class
     *
     * @param id  Unique ID number for a Bartender
     * @param name  Name of the bartender
     * @param username  Bartender's username
     * @param password  Bartender's Password
     */
    public Bartender(int id, String name, String username, String password) {
        super(id, name, username, password, "Bartender");
    }

    /**
     * Primary constructor for the Guests class (Without ID Field)
     *
     * @param name  Name of the bartender
     * @param username  Bartender's username
     * @param password  Bartender's Password
     */
    public Bartender(String name, String username, String password) {
        super(name, username, password, "Bartender");
    }
}
