package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles SQL operations related to the Order entity, such as inserting, retrieving,
 * updating, and deleting orders in the database.
 */
public class OrderSQLProvider extends SQLProvider implements IOrderSvc{

    // Connection to the database
    // Logger for logging information and errors
    private static final Logger logger = Logger.getLogger(OrderSQLProvider.class.getName());

    /**
     * Initializes the OrderSQLProvider with a database connection.
     *
     */
    public OrderSQLProvider() {
        super();
    }

    /**
     * Inserts a new order into the Orders table and returns the generated orderID.
     *
     * @param order the Order object containing the order details to be inserted
     * @return the generated primary key (orderID) for the new order, or -1 if the operation failed
     */
    public int insertOrder(Order order) {
        String query = "INSERT INTO Orders (guestId, bartenderId, orderDate, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getGuestId());
            stmt.setInt(2, order.getBartenderId());
            stmt.setDate(3, new Date(order.getOrderDate().getTime()));
            stmt.setBoolean(4, order.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting order failed, no rows affected.");
            }

            // Retrieve the generated keys
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1); // Get the first column value as the primary key
                    logger.info("Order inserted successfully with order ID: " + orderId);
                    return orderId;
                } else {
                    throw new SQLException("Inserting order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert order", e);
        }
        return -1; // Return -1 to indicate failure
    }


    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to be retrieved
     * @return the Order object corresponding to the specified ID, or null if not found
     */
    public Order getOrderById(int id) {
        String query = "SELECT * FROM Orders WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order(
                            rs.getInt("id"),
                            rs.getInt("guestId"),
                            rs.getInt("bartenderId"),
                            rs.getDate("orderDate"),
                            rs.getBoolean("status")
                    );
                    logger.info("Order retrieved with ID: " + id);
                    return order;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve order with ID: " + id, e);
        }
        return null;
    }

    /**
     * Retrieves all orders from the Orders table where status is false.
     *
     * @return a list of Order objects with status set to false
     */
    public List<Order> getPendingOrders() {
        String query = "SELECT * FROM Orders WHERE status = FALSE";
        List<Order> pendingOrders = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("guestId"),
                        rs.getInt("bartenderId"),
                        rs.getDate("orderDate"),
                        rs.getBoolean("status")
                );
                pendingOrders.add(order);
            }
            logger.info("Pending orders retrieved successfully");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve pending orders", e);
        }
        return pendingOrders;
    }

    /**
     * Retrieves all orders from the Orders table where status is true,
     * ordered by orderDate in descending order (most recent first).
     *
     * @return a list of Order objects with status set to true
     */
    public List<Order> getServedOrders() {
        String query = "SELECT * FROM Orders WHERE status = TRUE ORDER BY orderDate DESC";
        List<Order> servedOrders = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("guestId"),
                        rs.getInt("bartenderId"),
                        rs.getDate("orderDate"),
                        rs.getBoolean("status")
                );
                servedOrders.add(order);
            }
            logger.info("Served orders retrieved successfully and ordered by date (most recent first)");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve served orders", e);
        }
        return servedOrders;
    }



    /**
     * Retrieves all orders from the Orders table.
     *
     * @return a list of Order objects representing all orders in the database
     */
    public List<Order> getAllOrders() {
        String query = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("guestId"),
                        rs.getInt("bartenderId"),
                        rs.getDate("orderDate"),
                        rs.getBoolean("status")
                );
                orders.add(order);
            }
            logger.info("All orders retrieved successfully");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve all orders", e);
        }
        return orders;
    }

    /**
     * Updates an existing order in the database.
     *
     * @param order the Order object containing the updated order information
     */
    public void updateOrder(Order order) {
        String query = "UPDATE Orders SET guestId = ?, bartenderId = ?, orderDate = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, order.getGuestId());
            stmt.setInt(2, order.getBartenderId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setBoolean(4, order.getStatus());
            stmt.setInt(5, order.getOrderId());
            stmt.executeUpdate();
            logger.info("Order updated successfully with ID: " + order.getOrderId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update order", e);
        }
    }
}
