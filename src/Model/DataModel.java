package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Main Data model for the application
 */
public class DataModel {
    private static final Logger LOGGER = LogManager.getLogger(DataModel.class);
    private UsersSQLProvider usersSQLProvider;

    public DataModel() {
        usersSQLProvider = new UsersSQLProvider();
    }
    /**
     * Delegate login check responsibilities to UsersSQLProvider class
     * @param user Users object
     * @return  Returns a boolean value to represent success of authentication
     *
     */
    public Users authenticateUser(Users user) {
        // Call UsersSQLProvider to implement user authentication logic
        Users user1 = usersSQLProvider.getUserByAccount(user);
        if(user1 == null){
            LOGGER.error("UserSQLProvider returned null");
            return null;
        }else if (user.getUsername().equals(user1.getUsername()) && user.getPassword().equals(user1.getPassword())) {
            LOGGER.info("User was authenticated");
            return user1;
        } else {
            LOGGER.info("User was not found in database");
            return null;
        }
    }

    public Guests addGuest(Users user){
        Guests guest = (Guests) user;
        int id = new UsersSQLProvider().insertUser(user);
        guest.setId(id);
        new GuestsSQLProvider().insertGuests(guest);
        return guest;
    }

    public int getGuestAge(Guests guest){
        guest = new GuestsSQLProvider().searchGuests(guest);
        guest.calculateAge();
        return guest.getAge();
    }

    public Date getGuestDOB(Users user){
        Guests guest = new Guests();
        guest.setId(user.getId());
        guest = new GuestsSQLProvider().searchGuests(guest);
        return guest.getdOB();

    }

    public List<Drink> getAlcoholicDrinks() {
        return new DrinkSQLProvider().getDrinkByType(true);
    }
    public List<Drink> getNonAlcoholicDrinks() {
        return new DrinkSQLProvider().getDrinkByType(false);
    }
}
