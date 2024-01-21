package lk.ijse.chatapplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler extends Thread{
    Socket client;
    private BufferedReader in;
    private PrintWriter out;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ArrayList<ConnectionHandler> clientsArrayList;


    public ConnectionHandler(Socket socket, ArrayList<ConnectionHandler> clientsArrayList) throws IOException {
        this.client = socket;
        this.clientsArrayList = clientsArrayList;
        /*this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());*/
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    @Override
    public void run() {
        try {
           /* dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataInputStream = new DataInputStream(client.getInputStream());*/


            String message;
            while ((message= in.readLine()) != null) {
                if (message.equalsIgnoreCase( "exit")) {
                    break;
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
            /*connectionHandler.dataOutputStream.writeUTF(message);
            dataOutputStream.flush();*/
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

