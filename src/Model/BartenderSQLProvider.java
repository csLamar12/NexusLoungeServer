package Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The BartenderSQLProvider class provides SQL database operations for the Bartender entity,
 * including inserting, retrieving, and deleting bartenders in the database.
 */
public class BartenderSQLProvider extends SQLProvider {

    // Logger for logging messages and errors
    private static final Logger LOGGER = LogManager.getLogger(BartenderSQLProvider.class);

    private String query;

    /**
     * Initializes the BartenderSQLProvider with a database connection.
     */
    public BartenderSQLProvider() {
        super();
    }

    /**
     * Inserts a new bartender into the database.
     *
     * @param bartenderId the ID of the bartender to be inserted.
     * @return true if the bartender was inserted successfully, false otherwise.
     */
    public boolean insertBartender(int bartenderId) {
        query = "INSERT INTO Bartender (Id) VALUES (?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, bartenderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Bartender inserted successfully with ID: {}", bartenderId);
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert bartender with ID: {}", bartenderId, e);
        }
        return false;
    }

    /**
     * Deletes a bartender based on their ID.
     *
     * @param bartenderId the ID of the bartender to delete.
     */
    public void deleteBartender(int bartenderId) {
        query = "DELETE FROM Bartender WHERE Id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, bartenderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Bartender deleted successfully with ID: {}", bartenderId);
            } else {
                LOGGER.warn("No bartender found with ID: {}", bartenderId);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to delete bartender with ID: {}", bartenderId, e);
        }
    }

    /**
     * Retrieves a bartender from the database based on their ID.
     *
     * @param bartenderId the ID of the bartender to retrieve.
     * @return true if the bartender exists, false otherwise.
     */
    public boolean getBartenderById(int bartenderId) {
        query = "SELECT * FROM Bartender WHERE Id = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, bartenderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.info("Bartender retrieved successfully with ID: {}", bartenderId);
                return true; // Bartender exists
            } else {
                LOGGER.warn("No bartender found with ID: {}", bartenderId);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve bartender with ID: {}", bartenderId, e);
        }
        return false; // Bartender does not exist
    }

    /**
     * Retrieves all bartenders from the database.
     *
     * @return a list of bartender IDs.
     */
    public List<Integer> getAllBartenders() {
        query = "SELECT Id FROM Bartender";
        List<Integer> bartenderIds = new ArrayList<>();
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while (rs.next()) {
                bartenderIds.add(rs.getInt("Id"));
            }
            LOGGER.info("All bartenders were retrieved successfully");
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve all bartenders", e);
        }
        return bartenderIds;
    }
}
