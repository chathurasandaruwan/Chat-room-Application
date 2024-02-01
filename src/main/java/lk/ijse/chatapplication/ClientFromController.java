package lk.ijse.chatapplication;

import com.vdurmont.emoji.Emoji;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import com.vdurmont.emoji.EmojiLoader;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    private VBox messageArea;

    @FXML
    private ImageView cameraIcon;

    @FXML
    private TextField textMessage;

    @FXML
    private Button btnSend;
    @FXML
    private ImageView emogiIcon;

    private BufferedReader in;
    private PrintWriter out;
    Socket socket;
    FileChooser fileChooser = new FileChooser();
    File selectedFile;
    Stage primaryStage = new Stage();
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
        fileChooser.setTitle("Choose an Image File");/*
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );*/
        this.selectedFile = fileChooser.showOpenDialog(primaryStage);
        out.println(lblUserName.getText() + " " + "img" + selectedFile.getPath());

    }
    @FXML
    void emogiIconOnAction(MouseEvent event) {
        ObservableList<Emoji> emojiList = FXCollections.observableArrayList(EmojiManager.getAll());
//        Stage primaryStage = new Stage();
        ListView<Emoji> listView = new ListView<>(emojiList);
        listView.setCellFactory(param -> new EmojiCell());

        VBox root = new VBox(listView);
        Scene scene = new Scene(root, 60, 400);

        primaryStage.setTitle("Emoji Selector");
        primaryStage.setScene(scene);
        primaryStage.show();



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
                        fullMsg.append(tokens[i]).append(" ");
                    }

                    String[] msgToAr = msg.split(" ");
                    String st = "";
                    for (int i = 0; i < msgToAr.length - 1; i++) {
                        st += msgToAr[i + 1] + " ";
                    }

                    Text text = new Text(st);

                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }
      /*          if (firstChars.equalsIgnoreCase("emj")){
                    //String emojiCode = ":smile:";

                    //String emojiCharacter = EmojiParser.parseToUnicode(emojiCode);
                    //out.println(emojiCharacter);
                    TextFlow tempFlow = new TextFlow();
                   // Text emojiText = new Text(emojiCharacter + " ");
                  //  tempFlow.getChildren().add(emojiText);

                    tempFlow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        messageArea.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 860;-fx-max-width: 860;-fx-padding: 10");
                        flow.setStyle("-fx-background-color: white;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: black;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
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



                }else*/ if (firstChars.equalsIgnoreCase("img")) {

                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                        ImageView newImageView = new ImageView(image);
                        newImageView.setFitWidth(100);
                        newImageView.setFitHeight(100);

                        HBox newImageMessageBox = new HBox(12);
                        newImageMessageBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblUserName.getText())) {
                        messageArea.setAlignment(Pos.TOP_LEFT);
                        newImageMessageBox.setAlignment(Pos.CENTER_LEFT);
                        Text text1 = new Text("  " + cmd + " :");
                        newImageMessageBox.getChildren().add(text1);
                        newImageMessageBox.getChildren().add(newImageView);
                        newImageMessageBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 860;-fx-max-width: 860;-fx-padding: 10");
                    }else {
                        newImageMessageBox.setAlignment(Pos.BOTTOM_RIGHT);
                        Text text1 = new Text(": Me");
                        newImageMessageBox.getChildren().add(text1);
                        newImageMessageBox.getChildren().add(newImageView);
                        newImageMessageBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 860;-fx-max-width: 860;-fx-padding: 10");
                    }
                    Platform.runLater(() -> messageArea.getChildren().addAll(newImageMessageBox));

                }else {

                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(100);

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(lblUserName.getText() + ":")) {
                        messageArea.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 860;-fx-max-width: 860;-fx-padding: 10");
                        flow.setStyle("-fx-background-color: white;-fx-background-radius:15;-fx-font-size: 15;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
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
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @FXML
    void textMessageOnAction(ActionEvent event) throws IOException {
        btnSendOnAction(event);
    }
    //for get emoji and set to text
    private class EmojiCell extends javafx.scene.control.ListCell<Emoji> {
        @Override
        protected void updateItem(Emoji item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
            } else {
                Text emojiText = new Text(item.getUnicode());
                emojiText.setFont(Font.font(30.0));
                emojiText.setFill(Color.BLACK);
                Rectangle backgroundRect = new Rectangle(30.0, 30.0);
                // Set the background color
                backgroundRect.setFill(Color.YELLOW);

                StackPane stackPane = new StackPane(backgroundRect, emojiText);
                setGraphic(stackPane);

                setOnMouseClicked(event -> {
                    System.out.println("Clicked Emoji Code: " + item.getAliases().get(0));
                    String emojiCharacter = EmojiParser.parseToUnicode(":"+item.getAliases().get(0)+":");
                    //System.out.println("text mssg "+textMessage.getText());
                  /*  if (textMessage.getText()!=null){
                        textMessage.setText(emojiCharacter);
                    }else {*/
                    textMessage.setText(textMessage.getText()+emojiCharacter);
                        primaryStage.close();
                  //  }
                });
            }
        }
    }
}
