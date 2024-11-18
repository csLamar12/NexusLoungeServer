import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private Socket clientSocket;
    private DataModel model;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isGuest;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.model = new DataModel();
        Server.addClient(this);
    }

    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            int x = 2;
            do {
                if (in.readObject().equals("GUEST_LOGIN"))
                    x = guestUser((Users) in.readObject());
                else
                    x = authenticate();
            } while (x != 0 && x != 1);
            if (x == 0) {
                isGuest = true;
                while (true) {
                    x++;
                    if (in.readObject().equals("ORDER")) {
                        receiveOrder();
                    } else {
                        x = 0;
                        receiveOrderDetail();
                        break;
                    }
                }
            } else {
                isGuest = false;
                while (true) {
                    if (in.readObject().equals("GET_PENDING_ORDER")) {
                        sendPendingOrders();
                    } else {
                        sendServedOrders();
                        break;
                    }
                }
                while (true){
                    Object obj = in.readObject();
                    if (obj.equals("GET_ORDER_DETAIL")) {
                        sendOrderDetails();
                    } else if (obj.equals("SERVED_ORDER")) {
                        receiveServedOrder();
                    } else if (obj.equals("GET_PENDING_ORDER")) {
                        sendPendingOrders();
                    } else
                        break;
                }
            }


        }catch (IOException | ClassNotFoundException | NullPointerException e) {
            LOGGER.error(e);
        } finally {
            try {
                Server.removeClient(this);
                clientSocket.close();

            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    public int authenticate() throws IOException, ClassNotFoundException, NullPointerException {
        Users user = (Users) in.readObject();
        user = model.authenticateUser(user);
        if (user == null){
            user = new Users();
            out.writeObject(user);
            out.flush();
            return 2;
        }
        if (user.getRole().equals("Guest")) {
            System.out.println("Guest came back from db");
            out.writeObject(
                    new Guests(
                            user.getId(), user.getName(), user.getUsername(), user.getPassword(), model.getGuestDOB(user)
                    )
            );
            out.flush();
            getDrinksFromDatabase();
            return 0;
        } else {
            System.out.println("User came back from db");
            out.writeObject(user);
            out.flush();
            return 1;
        }
    }

    public int getUserAge(Users user){
        return model.getGuestAge((Guests) user);
    }

    public int guestUser(Users user) throws IOException {
        Guests guest = model.addGuest(user);
        out.writeObject(guest);
        out.flush();
        getDrinksFromDatabase();
        return 0;
    }

    public void getDrinksFromDatabase() throws IOException {
        out.writeObject(getAlcoholicDrinks());
        out.writeObject(getNonAlcoholicDrinks());
        out.flush();
    }
    public List<Drink> getAlcoholicDrinks() {
        return model.getAlcoholicDrinks();
    }
    public List<Drink> getNonAlcoholicDrinks() {
        return model.getNonAlcoholicDrinks();
    }

    public void receiveOrder() throws IOException, ClassNotFoundException {
        Order order = (Order) in.readObject();
        OrderSQLProvider orderSQLProvider = new OrderSQLProvider();
        order.setOrderId(orderSQLProvider.insertOrder(order));
        out.writeObject(order);
        out.flush();
//        broadcastNewOrder(order);
    }

    public void receiveOrderDetail() throws IOException, ClassNotFoundException {
        List<OrderDetail> orderDetails = (List<OrderDetail>) in.readObject();

        for (OrderDetail orderDetail : orderDetails) {
            System.out.println(orderDetail.toString());
            new OrderDetailSQLProvider().insertOrderDetail(orderDetail);
        }
    }

    public void receiveServedOrder() throws IOException, ClassNotFoundException {
        Order order = (Order) in.readObject();
        order.setStatus(true);
        new OrderSQLProvider().updateOrder(order);
    }

    public void sendPendingOrders() throws IOException {
        OrderSQLProvider orderSQLProvider = new OrderSQLProvider();
        List<Order> orders = orderSQLProvider.getPendingOrders();
        out.writeObject(orders);
        out.flush();
    }

    public void notifyPendingOrders() {
        try {
            sendPendingOrders();
            LOGGER.info("Notify pending orders");
        } catch (IOException e) {
            LOGGER.error("Failed to notify client about pending orders", e);
        }
    }

    public void sendServedOrders() throws IOException {
        OrderSQLProvider orderSQLProvider = new OrderSQLProvider();
        List<Order> orders = orderSQLProvider.getServedOrders();
        out.writeObject(orders);
        out.flush();
    }

    public void sendOrderDetails() throws IOException, ClassNotFoundException {
        OrderDetailSQLProvider orderDetailSQLProvider = new OrderDetailSQLProvider();
        Order order = (Order) in.readObject();
        List<OrderDetail> orderDetails = orderDetailSQLProvider.getOrderDetailsByOrderId(order.getOrderId());
        out.writeObject(orderDetails);
        out.flush();
        List<Drink> drinks = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            drinks.add(new DrinkSQLProvider().getDrinkById(orderDetail.getDrinkId()));
        }
        out.writeObject(drinks);
        out.flush();
    }

    public void broadcastNewOrder(Order order) {
        for (ClientHandler client : Server.getActiveClients()) {
            if (client != this && !client.isGuest) {
                try {
                    client.out.writeObject("NEW_ORDER");
                    client.out.flush();
                    client.out.writeObject(order);
                    client.out.flush();
                } catch (IOException e) {
                    LOGGER.error("Error notifying Manager/Bartender about new order", e);
                }
            }
        }
    }


}
