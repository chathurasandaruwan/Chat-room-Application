package lk.ijse.chatapplication.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFromController {
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String message = "";
    public void Server() throws IOException {

            ServerSocket serverSocket =new ServerSocket(3000);
            System.out.println("Server Started !");
            Socket socket = serverSocket.accept();
            System.out.println("\nClient Connected !");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            while (!message.equals("End")){
                message = dataInputStream.readUTF();
                System.out.println("\nClient: " + message);
            }

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
    }
}
