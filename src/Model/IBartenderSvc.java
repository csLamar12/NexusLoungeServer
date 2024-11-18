package Model;

import java.util.List;

/**
 * Interface for Bartender service operations.
 * Defines the methods for interacting with the Bartender table in the database.
 */
public interface IBartenderSvc {

    /**
     * Inserts a new bartender into the database.
     *
     * @param bartenderId the ID of the bartender to be inserted.
     * @return true if the bartender was inserted successfully, false otherwise.
     */
    boolean insertBartender(int bartenderId);

    /**
     * Deletes a bartender from the database based on their ID.
     *
     * @param bartenderId the ID of the bartender to delete.
     */
    void deleteBartender(int bartenderId);

    /**
     * Retrieves a bartender from the database based on their ID.
     *
     * @param bartenderId the ID of the bartender to retrieve.
     * @return true if the bartender exists, false otherwise.
     */
    boolean getBartenderById(int bartenderId);

    /**
     * Retrieves all bartenders from the database.
     *
     * @return a list of bartender IDs.
     */
    List<Integer> getAllBartenders();
}
