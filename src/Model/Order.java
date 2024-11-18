package Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an order with details such as order ID, guest ID, bartender ID, order date, and status.
 */
public class Order implements Serializable {

    private int orderId;
    private int guestId;
    private int bartenderId;
    private Date orderDate;
    private boolean status;

    /**
     * Constructs an Order object with the specified details.
     *
     * @param orderId      the unique ID of the order
     * @param guestId      the ID of the guest who placed the order
     * @param bartenderId  the ID of the bartender handling the order
     * @param orderDate    the date the order was placed
     * @param status       the status of the order (e.g., completed or pending)
     */
    public Order(int orderId, int guestId, int bartenderId, Date orderDate, boolean status) {
        this.orderId = orderId;
        this.guestId = guestId;
        this.bartenderId = bartenderId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getBartenderId() {
        return bartenderId;
    }

    public void setBartenderId(int bartenderId) {
        this.bartenderId = bartenderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOrderIdAsString() {
        return String.format("%03d", orderId);
    }

    /**
     * Returns a string representation of the Order object.
     */
    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", guestId=" + guestId + ", bartenderId=" + bartenderId
                + ", orderDate=" + orderDate + ", status=" + status + "]";
    }
}
