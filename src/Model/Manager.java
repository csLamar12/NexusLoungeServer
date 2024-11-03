package Model;

/*
Add methods and attributes as needed, JUST COMMUNICATE BEFORE DOING SO!!!
 */
public class Manager extends Users {

    public Manager() {
        super();
    }

    /**
     * Primary constructor for the Guests class
     *
     * @param id  Unique ID number for a Manager
     * @param name  Name of the manager
     * @param username  Manager's username
     * @param password  Manager's Password
     */
    public Manager(int id, String name, String username, String password){
        super(id, name, username, password, "Manager");
    }

    /**
     * Primary constructor for the Guests class (Without ID Field)
     *
     * @param name  Name of the manager
     * @param username  Manager's username
     * @param password  Manager's Password
     */
    public Manager(String name, String username, String password){
        super(name, username, password, "Manager");
    }
}
