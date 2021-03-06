package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

//Proyecto de Fin de curso creador por Antonio Cara Esteban
public class ControllerHowToPlay {

    @javafx.fxml.FXML
    private Button btBack;
    @javafx.fxml.FXML
    private Pane pane;

    //Vuelve al main menu
    @javafx.fxml.FXML
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
