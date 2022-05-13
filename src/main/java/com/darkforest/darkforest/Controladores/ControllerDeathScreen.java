package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerDeathScreen {
    @javafx.fxml.FXML
    private Button btReturn;

    @javafx.fxml.FXML
    public void returnToMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,400);
        Stage stage = new Stage();
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
