package lk.ijse.chatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable{
    private Socket client;
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
            out =new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String message;
            while ((message= in.readLine()) != null) {
                if (message.startsWith("/quit")) {
                    shutDown();
                }else {
                    sendMessage(message);
                }
            }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
    public void sendMessage(String message){
        for (ConnectionHandler connectionHandler : clientsArrayList) {
            connectionHandler.out.println(message);
        }
    }

    public void shutDown() {
        try {
            in.close();
            out.close();
            if (!client.isClosed()){
                client.close();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

