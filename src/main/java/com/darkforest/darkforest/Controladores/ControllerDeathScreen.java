package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDeathScreen {
    @javafx.fxml.FXML
    private Button btReturn;
    @javafx.fxml.FXML
    private Pane pane;
    @javafx.fxml.FXML
    private ImageView imgView;

    @javafx.fxml.FXML
    public void returnToMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),650,450);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.setTitle("Dark Forest");
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
