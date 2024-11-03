import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            if (in.readObject().equals("GUEST_LOGIN"))
                guestUser((Users) in.readObject());
            else
                authenticate();

        }catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    public void authenticate() throws IOException, ClassNotFoundException {
        Users user = (Users) in.readObject();
        user = model.authenticateUser(user);
        out.writeObject(user);
        out.flush();
        if (user.getRole().equals("Guest")) {
            getDrinksFromDatabase();
        }
    }

    public void guestUser(Users user) throws IOException {
        model.addGuest(user);
        getDrinksFromDatabase();
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
}
