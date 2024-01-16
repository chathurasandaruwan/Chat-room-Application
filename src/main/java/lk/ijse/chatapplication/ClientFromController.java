package lk.ijse.chatapplication;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFromController {
    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private ImageView cameraIcon;

    @FXML
    private TextField textMessage;

    @FXML
    private Button btnSend;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String message = "";
    public void initialize() {
        lblUserName.setText(LoggingFromController.name);
        showMessage();
    }
    public void showMessage(){
        new Thread(()->{
            try {
                Socket socket = new Socket("localhost", 3000);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());


                while (!message.equals("End")){
                    message = dataInputStream.readUTF();
                    textArea.appendText("\nServer: "+message);
                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();

            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }).start();

    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        message = textMessage.getText();
        textArea.appendText("\nMe : "+message);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }

    @FXML
    void sendImage(MouseEvent event) {
        System.out.print("Image send");
    }

}
