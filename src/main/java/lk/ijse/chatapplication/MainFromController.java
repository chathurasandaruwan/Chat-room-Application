package lk.ijse.chatapplication;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;

public class MainFromController {

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

    @FXML
    void btnSendOnAction(ActionEvent event) {

    }

    @FXML
    void sendImage(MouseEvent event) {
        System.out.print("Image send");
    }

}
