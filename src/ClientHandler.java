import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private Socket clientSocket;
    private DataModel model;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.model = new DataModel();
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
                while (true) {
                    if (in.readObject().equals("ORDER")) {
                        receiveOrder();
                    } else if (in.readObject().equals("ORDER_DETAIL")) {
                        receiveOrderDetail();
                    }
                }
            }


        }catch (IOException | ClassNotFoundException | NullPointerException e) {
            LOGGER.error(e);
        } finally {
            try {
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
    }

    public void receiveOrderDetail() throws IOException, ClassNotFoundException {
        OrderDetail orderDetail = (OrderDetail) in.readObject();
    }
}
