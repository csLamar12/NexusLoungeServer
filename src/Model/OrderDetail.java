package Model;

/**
 * Represents the details of an order, including the order ID, the drink ID, and the quantity ordered.
 */
public class OrderDetail {

    private int orderId;
    private int drinkId;
    private int quantity;

    /**
     * Constructs an OrderDetail object with the given order ID, drink ID, and quantity.
     *
     * @param orderId   the ID of the order
     * @param drinkId   the ID of the drink
     * @param quantity  the quantity of the drink ordered
     */
    public OrderDetail(int orderId, int drinkId, int quantity) {
        this.orderId = orderId;
        this.drinkId = drinkId;
        this.quantity = quantity;
    }

    // Getter and Setter for orderId
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Getter and Setter for drinkId
    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the OrderDetail object, showing the order ID, drink ID, and quantity.
     */
    @Override
    public String toString() {
        return "OrderDetail [orderId=" + orderId + ", drinkId=" + drinkId + ", quantity=" + quantity + "]";
    }
}
