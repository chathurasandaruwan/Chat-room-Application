package lk.ijse.chatapplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler extends Thread{
    Socket client;
    private BufferedReader in;
    private PrintWriter out;
    ArrayList<ConnectionHandler> clientsArrayList;


    public ConnectionHandler(Socket socket, ArrayList<ConnectionHandler> clientsArrayList) throws IOException {
        this.client = socket;
        this.clientsArrayList = clientsArrayList;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    @Override
    public void run() {
        try {
            String message;
            while ((message= in.readLine()) != null) {
                if (message.endsWith("bye")) {
                   System.exit(0);
                }else {
                    sendMessage(message);
                }
            }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }finally {
            shutDown();
        }

    }
    public void sendMessage(String message) throws IOException {
        for (ConnectionHandler connectionHandler : clientsArrayList) {
            connectionHandler.out.println(message);
            System.out.println(message);
        }
    }

    public void shutDown() {

        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

