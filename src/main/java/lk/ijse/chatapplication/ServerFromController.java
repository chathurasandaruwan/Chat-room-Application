package lk.ijse.chatapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerFromController {
    DataInputStream dataInputStream;
    String message = "";
    private ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();
    public void Server() throws IOException {
        ServerSocket serverSocket =new ServerSocket(3000);
        Socket socket;
        while (true) {
            System.out.println("Server Started !");
            socket = serverSocket.accept();
            System.out.println("\nClient Connected !");
            ConnectionHandler connectionHandler = new ConnectionHandler(socket,connectionHandlers);
            connectionHandlers.add(connectionHandler);
            connectionHandler.start();
        }
    }
}
