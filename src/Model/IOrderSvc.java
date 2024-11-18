package Model;

import java.util.List;

/**
 * Interface for Order data access object, defining operations
 * related to orders in the database.
 */
public interface IOrderSvc {

    /**
     * Inserts a new order into the database.
     *
     * @param order the Order object containing details to be inserted
     * @return the generated primary key (orderID) for the new order, or -1 if the operation fails
     */
    int insertOrder(Order order);

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to be retrieved
     * @return the Order object corresponding to the specified ID, or null if not found
     */
    Order getOrderById(int id);

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all Order objects
     */
    List<Order> getAllOrders();

    /**
     * Updates an existing order in the database.
     *
     * @param order the Order object containing the updated order information
     */
    void updateOrder(Order order);

    /**
     * Retrieves all orders where the status is true,
     * ordered by orderDate in descending order (most recent first).
     *
     * @return a list of Order objects with status set to true
     */
    List<Order> getServedOrders();
}
