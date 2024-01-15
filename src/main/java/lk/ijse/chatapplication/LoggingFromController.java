package lk.ijse.chatapplication;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoggingFromController extends Application {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField textUserName;
    String name;

    @FXML
    private Button btnLogIn;

    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(LoggingFromController.class.getResource("/lk/ijse/chatapplication/logging_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("chat room");
        stage.setScene(scene);
        stage.show();*/
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/chatapplication/logging_form.fxml"))));
        stage.show();

            ServerFromController serverFormContoller = new ServerFromController();
            try {
                serverFormContoller.Server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    public static void main(String[] args) {
        launch();
    }
    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        name= textUserName.getText();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/chatapplication/client_form.fxml"))));
        stage.setTitle(name);
        stage.show();
        textUserName.clear();

    }

}
