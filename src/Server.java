import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final List<ClientHandler> activeClients = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static final int PORT = 12350;

    public Server() {
        startServer();
        acceptClient();
    }
    public void startServer(){
        try{
            serverSocket = new ServerSocket(PORT);
            LOGGER.info("Server started");

        } catch(IOException e){
            LOGGER.error(e);
        }
    }
    public void acceptClient(){
        try{
            while(true){
                clientSocket = this.serverSocket.accept();
                LOGGER.info("Client connected {}", clientSocket.getInetAddress().getHostAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch(IOException e){
            LOGGER.error(e);
        }
    }

    public static void addClient(ClientHandler clientHandler) {
        activeClients.add(clientHandler);
    }

    public static void removeClient(ClientHandler clientHandler) {
        activeClients.remove(clientHandler);
    }

    public static List<ClientHandler> getActiveClients() {
        return activeClients;
    }
}
