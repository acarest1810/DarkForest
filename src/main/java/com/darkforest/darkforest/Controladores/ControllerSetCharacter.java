package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;

public class ControllerSetCharacter {
    @javafx.fxml.FXML
    private ToggleGroup select;
    @javafx.fxml.FXML
    private Button btConfirm;
    @javafx.fxml.FXML
    private ImageView imgviewSirQovun;
    @javafx.fxml.FXML
    private RadioButton rbSirQovun;
    @javafx.fxml.FXML
    private RadioButton rbDarki;
    private File charinfo = new File(System.getProperty("user.dir"), "personaje.txt");
    @javafx.fxml.FXML
    private ImageView imgviewDarki;
    @javafx.fxml.FXML
    private ImageView imgviewMark;
    @javafx.fxml.FXML
    private RadioButton rbMark;
    @javafx.fxml.FXML
    private Pane pane;

    public void initialize() throws IOException {
        detectCharacter();
        setImgview("sir_qovun.png",imgviewSirQovun);
        setImgview("Darki.png",imgviewDarki);
        setImgview("Mark.png",imgviewMark);
    }

    //Setea la imagen, ahorra código
    public void setImgview(String path,ImageView imgv) {
        Image image = new Image(this.getClass().getResource("/Characters/"+path).toString());
        imgv.setImage(image);
    }

    //Detecta que personaje se ha seleccionado anteriormente, si nunca ha jugado, el por defecto será Sir Qovun
    public void detectCharacter() throws IOException{
        FileReader fr = new FileReader(charinfo);
        LineNumberReader lnr = new LineNumberReader(fr);
        String nombre=lnr.readLine();
        switch (nombre){
            case "Sir Qovun":
                rbSirQovun.setSelected(true);
                break;
            case "Darki":
                rbDarki.setSelected(true);
                break;
            case "Mark":
                rbMark.setSelected(true);
                break;
        }
    }

    //Escribe los datos del personaje en personaje.txt
    @javafx.fxml.FXML
    public void confirm(ActionEvent actionEvent) throws IOException {
        FileWriter charwritter=new FileWriter(charinfo);
        charinfo.delete();
        charinfo.createNewFile();
        if(rbSirQovun.isSelected()){
            charwritter.write("Sir Qovun");
            charwritter.write("\n");
            charwritter.write("3");
            charwritter.write("\n");
            charwritter.write("7");
            charwritter.write("\n");
            charwritter.write("black");
            charwritter.close();
        }else if(rbDarki.isSelected()){
            charwritter.write("Darki");
            charwritter.write("\n");
            charwritter.write("2");
            charwritter.write("\n");
            charwritter.write("7");
            charwritter.write("\n");
            charwritter.write("red");
            charwritter.close();
        }else if(rbMark.isSelected()){
            charwritter.write("Mark");
            charwritter.write("\n");
            charwritter.write("1");
            charwritter.write("\n");
            charwritter.write("7");
            charwritter.write("\n");
            charwritter.write("white");
            charwritter.close();
        }
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
