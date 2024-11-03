package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles SQL operations related to the OrderDetail entity,
 * such as inserting, retrieving, updating, and deleting order details in the database.
 */
public class OrderDetailSQLProvider {

    private final Connection connection;
    private static final Logger logger = Logger.getLogger(OrderDetailSQLProvider.class.getName());

    /**
     * Initializes the OrderDetailSQLProvider with a database connection.
     *
     * @param connection the database connection
     */
    public OrderDetailSQLProvider(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a new order detail into the OrderDetail table.
     *
     * @param orderDetail the OrderDetail object containing order information
     */
    public void insertOrderDetail(OrderDetail orderDetail) {
        String query = "INSERT INTO OrderDetail (OrderID, DrinkID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getDrinkId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.executeUpdate();
            logger.info("Order detail inserted successfully for Order ID: " + orderDetail.getOrderId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert order detail", e);
        }
    }

    /**
     * Retrieves order details for a given OrderID.
     *
     * @param orderId the ID of the order
     * @return a list of OrderDetail objects for the specified order
     */
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        String query = "SELECT * FROM OrderDetail WHERE OrderID = ?";
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = new OrderDetail(
                            rs.getInt("OrderID"),
                            rs.getInt("DrinkID"),
                            rs.getInt("Quantity")
                    );
                    orderDetails.add(orderDetail);
                }
                logger.info("Order details retrieved successfully for Order ID: " + orderId);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve order details for Order ID: " + orderId, e);
        }
        return orderDetails;
    }

    /**
     * Updates an existing order detail in the database.
     *
     * @param orderDetail the updated OrderDetail object
     */
    public void updateOrderDetail(OrderDetail orderDetail) {
        String query = "UPDATE OrderDetail SET DrinkID = ?, Quantity = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderDetail.getDrinkId());
            stmt.setInt(2, orderDetail.getQuantity());
            stmt.setInt(3, orderDetail.getOrderId());
            stmt.executeUpdate();
            logger.info("Order detail updated successfully for Order ID: " + orderDetail.getOrderId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update order detail", e);
        }
    }
}
