package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The UsersSQLProvider class provides SQL database operations for the Users entity,
 * including inserting, retrieving, updating and deleting users in the database.
 * This class also interacts with the Users table in the database.
 */
public class UsersSQLProvider extends SQLProvider implements IUsersSVC {

    //Logger for logging messages and errors
    private static final Logger LOGGER = LogManager.getLogger(UsersSQLProvider.class);

    private String query;

    /**
     * Initializes the UsersSQLProvider with a database connection.
     */
    public UsersSQLProvider(){
        super();
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the Users object to be inserted.
     */
    @Override
    public int insertUser(Users user) {
        query = "INSERT INTO Users (Name, Username, Password, Role) VALUES (?, ?, ?, ?)";
        try{
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            LOGGER.info("User inserted successfully: {}", user.getName());
            try {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert a user: {}", user.getName());
        }
        return 0;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the Users object containing the updated information.
     */
    @Override
    public void updateUser(Users user) {
        query = "UPDATE Users SET Name = ?, Username = ?, Password = ?, Role = ? WHERE Id = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            LOGGER.info("User inserted successfully: {}", user.getName());
        }
        catch(SQLException e){
            LOGGER.error("Failed to update user: {}", user.getName());
        }
    }

    /**
     * Deletes a user based on their ID.
     *
     * @param user Users object
     */
    @Override
    public void deleteUser(Users user) {
        query = "DELETE FROM Users WHERE Id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            LOGGER.info("User deleted with ID: {}", user.getId());
        } catch (SQLException e) {
            LOGGER.error("Failed to delete user with ID: {}", user.getId(), e);
        }
    }

    /**
     * Retrieves a user from the database based on the user ID.
     *
     * @param user Users object
     * @return the user object corresponding to the given ID, or null if no such user exists
     */
    @Override
    public Users getUserById(Users user) {
        query = "SELECT * FROM Users WHERE id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                Users user1 = new Users(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Role")
                );
                LOGGER.info("User retrieved: {}", user1.getName());
                return user1;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve user with ID: {}", user.getId(), e);
        }
        return null;
    }

    /**
     * Retrieves a user from the database based on the username and password.
     *
     * @param user Users object
     * @return the user object corresponding to the given credentials, or null if no such user exists
     */
    public Users getUserByAccount(Users user) {
        query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            rs = ps.executeQuery();
            if (rs.next()) {
                Users user1 = new Users(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Role")
                );
                LOGGER.info("User retrieved: {}", user1.getName());
                return user1;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve user with given credentials", e);
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects representing all users in the database.
     */
    @Override
    public List<Users> getAllUsers() {
        query = "SELECT * FROM Users";
        List<Users> users = new ArrayList<>();
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Role")
                );
                users.add(user);
            }
            LOGGER.info("All users were retrieved successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve all users", e);
        }
        return users;
    }
}
