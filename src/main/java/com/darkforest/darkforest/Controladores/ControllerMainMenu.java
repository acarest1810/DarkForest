package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ControllerMainMenu {

    @FXML
    private Button bttStart;
    @FXML
    private Button bttCharacter;
    @FXML
    private Text txtTitle;

    public void initialize() throws IOException {
        File charinfo=new File("src/main/characterinfo/personaje.txt");
        //Información del personaje utilizado por defecto si no ha jugado antes
        if(!charinfo.exists()){
            charinfo.createNewFile();
            FileWriter charwritter=new FileWriter(charinfo);
            charwritter.write("Sir Qovun");
            charwritter.write("\n");
            charwritter.write("3");
            charwritter.write("\n");
            charwritter.write("1");
            charwritter.write("\n");
            charwritter.write("6");
            charwritter.write("\n");
            charwritter.write("black");
            charwritter.close();
        }
    }

    @FXML
    public void start(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("game-field.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),650,450);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void selectCharacter(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("select-character.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),650,450);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setTitle("Select Character");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}