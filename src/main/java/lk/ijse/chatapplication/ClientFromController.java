package lk.ijse.chatapplication;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientFromController extends Thread{
    @FXML
    private Label lblUserName;

    @FXML
    private static VBox messageArea;

    @FXML
    private ImageView cameraIcon;

    @FXML
    private TextField textMessage;

    @FXML
    private Button btnSend;
    private BufferedReader in;
    private PrintWriter out;
    Socket socket;
    public void initialize() {
        lblUserName.setText(LoggingFromController.name);
        showMessage();


    }
    public void showMessage(){

            try {
                socket = new Socket("localhost", 3000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                this.start();

            } catch(IOException e){
                throw new RuntimeException(e);
            }
    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        String msg = textMessage.getText();
        out.println(lblUserName.getText() + ": " + msg);
        textMessage.clear();
    }

    @FXML
    void sendImage(MouseEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String msg = in.readLine();
            String imagePath = selectedFile.getAbsolutePath();
            System.out.println("Selected Image Path: " + imagePath);
            imagePath = msg.substring(6);
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ClientFromController.sendImageMessage(image);
        } else {
            System.out.println("No file selected.");
        }
    }

    @Override
    public void run() {
       while (true){
            try {

                String msg = in.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                    StringBuilder fullMsg = new StringBuilder();
                    for (int i = 1; i < tokens.length; i++) {
                        fullMsg.append(tokens[i] + " ");
                    }

                    String[] msgToAr = msg.split(" ");
                    String st = "";
                    for (int i = 0; i < msgToAr.length - 1; i++) {
                        st += msgToAr[i + 1] + " ";
                    }

                    Text text = new Text(st);

                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        messageArea.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
                        flow.setStyle("-fx-background-color:  #1988bb;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
                        hBox.getChildren().add(flow);
                    } else {
                        Text text2 = new Text(fullMsg + ": Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 860;-fx-max-width: 860;-fx-padding: 10");
                        flow2.setStyle("-fx-background-color:  #3dd3d0 ;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
                        hBox.getChildren().add(flow2);
                    }
                    Platform.runLater(() -> messageArea.getChildren().addAll(hBox));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void sendImageMessage(Image image) {
        ImageView newImageView = new ImageView(image);
        newImageView.setFitWidth(100);
        newImageView.setFitHeight(100);
        newImageView.setPreserveRatio(true);

        HBox newImageMessageBox = new HBox(newImageView);
        messageArea.getChildren().add(newImageMessageBox);
    }
    @FXML
    void textMessageOnAction(ActionEvent event) throws IOException {
        btnSendOnAction(event);
    }
}
