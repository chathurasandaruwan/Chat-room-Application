package lk.ijse.chatapplication;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggingFromController extends Application {

    @FXML
    private AnchorPane root;
    public TextField textUserName;
    static String name;

    @FXML
    private Button btnLogIn;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/chatapplication/logging_form.fxml"))));
        stage.show();

        new Thread(()->{
            ServerFromController serverFormContoller = new ServerFromController();
            try {
                serverFormContoller.Server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public static void main(String[] args) {
        launch();
    }
    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        name= textUserName.getText();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/chatapplication/client_form.fxml"))));
        stage.setTitle("CHAT ROOM");
        stage.show();
        textUserName.clear();

    }

}
