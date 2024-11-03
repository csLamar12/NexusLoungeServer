package Model;

import java.util.List;


/**
 * This interface defines the service layer for performing CRUD operations on User objects.
 * It provides methods for inserting, updating, deleting, and retrieving users.
 */
public interface IUsersSVC {
    public int insertUser(Users user);
    public void updateUser(Users user);
    public void deleteUser(Users user);
    public Users getUserById(Users user);
    public Users getUserByAccount(Users user);
    public List<Users> getAllUsers();
}
