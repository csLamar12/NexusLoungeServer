import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
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
}
