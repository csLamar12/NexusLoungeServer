package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The DrinkSQLProvider class provides SQL database operations for the Drink entity,
 * including inserting, retrieving, updating, and deleting drinks in the database.
 * It interacts with the Drinks table in the database.
 *
 * @author Danielle Johns
 */
public class DrinkSQLProvider extends SQLProvider implements IDrinkSvc {

    // Logger for logging messages and errors
    private static final Logger LOGGER = LogManager.getLogger(DrinkSQLProvider.class);
    
    private String query;

    /**
     * Initializes the DrinkSQLProvider with a database connection.
     */
    public DrinkSQLProvider() {
        super();
    }

    /**
     * Inserts a new drink into the database.
     *
     * @param drink the Drink object containing the information to be inserted.
     */
    public void insertDrink(Drink drink) {
        query = "INSERT INTO Drink (Name, IsAlcoholic, Description) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, drink.getName());
            ps.setBoolean(2, drink.isAlcoholic());
            ps.setString(3, drink.getDescription());
            ps.executeUpdate();
            LOGGER.info("Drink inserted successfully: {}", drink.getName());
        } catch (SQLException e) {
            LOGGER.error("Failed to insert drink: {}", drink.getName(), e);
        }
    }

    /**
     * Retrieves a drink by its ID.
     *
     * @param id the ID of the drink to be retrieved.
     * @return the Drink object corresponding to the specified ID, or null if not found.
     */
    public Drink getDrinkById(int id) {
        query = "SELECT * FROM Drink WHERE id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Drink drink = new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isAlcoholic"),
                        rs.getString("Description"),
                        0
                );
                LOGGER.info("Drink retrieved: {}", drink.getName());
                return drink;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve drink with ID: {}", id, e);
        }
        return null;
    }

    /**
     * Retrieves a drink by its ID.
     *
     * @param isAlcoholic the type of the drink to be retrieved.
     * @return the Drink object corresponding to the specified type, or null if not found.
     */
    public List<Drink> getDrinkByType(boolean isAlcoholic) {
        query = "SELECT * FROM Drink WHERE IsAlcoholic = ?";
        List<Drink> drinks = new ArrayList<>();
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, isAlcoholic ? 1 : 0);
            rs = ps.executeQuery();
            while (rs.next()) {
                Drink drink = new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isAlcoholic"),
                        rs.getString("Description"),
                        0
                );
                LOGGER.info("Drink retrieved: {}", drink.getName());
                drinks.add(drink);
            }
            return drinks;
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve drink with type: {}", isAlcoholic, e);
            return null;
        }
    }

    /**
     * Retrieves all drinks from the database.
     *
     * @return a list of Drink objects representing all drinks in the database.
     */
    public List<Drink> getAllDrinks() {
        query = "SELECT * FROM Drink";
        List<Drink> drinks = new ArrayList<>();
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                Drink drink = new Drink(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("isAlcoholic"),
                        rs.getString("Description"),
                        0
                );
                drinks.add(drink);
            }
            LOGGER.info("All drinks retrieved successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve all drinks", e);
        }
        return drinks;
    }

    /**
     * Updates an existing drink in the database.
     *
     * @param drink the Drink object containing the updated information.
     */
    public void updateDrink(Drink drink) {
        String query = "UPDATE Drink SET name = ?, isAlcoholic = ?, Description = ? WHERE id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, drink.getName());
            ps.setBoolean(2, drink.isAlcoholic());
            ps.setString(3, drink.getDescription());
            ps.setInt(4, drink.getId());
            ps.executeUpdate();
            LOGGER.info("Drink updated successfully: {}", drink.getName());
        } catch (SQLException e) {
            LOGGER.error("Failed to update drink: {}", drink.getName(), e);
        }
    }

    /**
     * Deletes a drink by its ID.
     *
     * @param id the ID of the drink to be deleted.
     */
    public void deleteDrink(int id) {
        String query = "DELETE FROM Drink WHERE id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info("Drink deleted with ID: {}", id);
        } catch (SQLException e) {
            LOGGER.error("Failed to delete drink with ID: {}", id, e);
        }
    }
}
