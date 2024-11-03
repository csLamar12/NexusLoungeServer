package Model;
/*
Add methods and attributes as needed, JUST COMMUNICATE BEFORE DOING SO!!!
 */

import java.io.Serializable;

/**
 * Represents a user with an id, name, role, username and password
 *
 * Note: The `username` and `password` are optional for some users,
 * such as guests who may not create login credentials.
*/
public class Users implements Serializable {
    /**
     * The attributes are marked as `protected` to allow subclasses to access
     * and modify the value directly without needing the getter/setter methods.
     * It should not be accessed from outside the package or from unrelated classes.
     */
    protected int id;
    protected String name;
    protected String username;
    protected String password;
    protected String role;

    // Default constructor
    public Users() {
      this.id = 0;
      this.name = null;
      this.username = null;
      this.password = null;
      this.role = null;
    }

  /**
   * Primary Controller for the Users Class
   *
   * @param id  Unique ID number for a user
   * @param name  Name of the user (Nullable)
   * @param username  Users's username (Nullable)
   * @param password  Users's Password (Nullable)
   * @param role  Users's role (Guest, Manager, Bartender)
   */
    public Users(int id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

  /**
   * Primary Controller for the Users Class (Without ID field)
   *
   * @param name  Name of the user (Nullable)
   * @param username  Users's username (Nullable)
   * @param password  Users's Password (Nullable)
   * @param role  Users's role (Guest, Manager, Bartender)
   */
    public Users(String name, String username, String password, String role) {
      this.name = name;
      this.username = username;
      this.password = password;
      this.role = role;
    }

    // Copy Constructor
    public Users(Users copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.username = copy.username;
        this.password = copy.password;
        this.role = copy.role;
    }

    //Getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        if(password.length() >= 8 && password.length() <= 12){
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password must be between 8 and 12 characters");
        }
    }

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users [id= " + id + ", name=" + name + ", username=" + username + ", password= " + password + ", role= " + role + "]";
    }
}
