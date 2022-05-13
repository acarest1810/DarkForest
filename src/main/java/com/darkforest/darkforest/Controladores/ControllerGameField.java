package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.Class.Enemy;
import com.darkforest.darkforest.Class.Item;
import com.darkforest.darkforest.Class.Player;
import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;

public class ControllerGameField {
    @javafx.fxml.FXML
    private ImageView imgvCharacter;
    @javafx.fxml.FXML
    private Pane pane;
    @javafx.fxml.FXML
    private Button btRight;
    @javafx.fxml.FXML
    private Button btUp;
    @javafx.fxml.FXML
    private ImageView imgvHeart;
    @javafx.fxml.FXML
    private Button btLeft;
    @javafx.fxml.FXML
    private Button btDown;
    @javafx.fxml.FXML
    private Label labelTxt;
    @javafx.fxml.FXML
    private ImageView statusImgView;
    @javafx.fxml.FXML
    private Label statusLabel;

    //Variables para controlar el juego
    String name="",keytype="";
    int hp=0,slots=0,hptotal=0,sizeX=10,sizeY=10,totalX=0,countKey=0,countFalseKey=0,slotsfilled=0;
    int[] inventory;
    double posXActual, posYActual,posXAnterior,posYAnterior;
    //Diferentes estados de las llaves y detección del final del juego
    boolean fullKey=false,trueKey1=false,trueKey2=false,
            trueKey3=false,falseKey1=false,falseKey2=false,falseKey3=false,
            isFinished=false,dyedKey1=false,dyedKey2=false,dyedKey3=false;
    ImageView[][] arrayImageView;
    ImageView[] itemsArrayImageView;
    Enemy grunt,grunt2;
    Player pj;
    Item pieceFalse1,pieceFalse2,pieceFalse3,piece1,piece2,piece3,dyedPiece1,dyedPiece2,dyedPiece3;

    //0 = sin explorar; 1 = donde está el jugador; 2 = explorado y no habia nada;
    //3 = explorado y habia trozo de llave; 4 = hay trozo de llave y sin explorar (se verá en rojo)
    //5 = está el jugador y hay trozo de llave; 6 = hay un trozo de llave inservible y sin explorar
    //7 = trozo de llave inservible y explorado; 8 = trozo de llave inservible y el jugador está ahí
    //9 = El jugador está en la puerta; 10 = Donde se ha generado la puerta; 11 = El jugador sabe donde está la puerta
    //12 = El jugador está en el molde; 13 = Donde se ha generado el molde; 14 = El jugador sabe donde está el molde
    //15 = El jugador está en el cubo de pintura; 16 = Donde se ha generado el cubo de pintura; 17 = El jugador sabe donde está el cubo de pintura
    //18 = El jugador está en la torre; 19 = Donde se ha generado la torre; 20 = El jugador sabe donde está la torre
    int[][] statusTable;

    public void initialize() throws IOException {
        File charinfo=new File("src/main/characterinfo/personaje.txt");
        characterChoosed(charinfo);
        inventory = new int[pj.getSlots()];
        setHeart();
        controlTable();
        hptotal=pj.getHp();
        labelTxt.setText(pj.getHp()+"/"+hptotal);
    }

    //Genera los distintos objetos y zonas del juego
    public void controlTable(){
        arrayImageView = new ImageView[sizeX][sizeY];
        itemsArrayImageView=new ImageView[pj.getSlots()];
        statusTable = new int[sizeX][sizeY];
        setTable(arrayImageView,statusTable);
        posXActual = Math.random()*sizeX;
        posYActual = Math.random()*sizeY;
        grunt = new Enemy((int) (Math.random()*sizeX), (int) (Math.random()*sizeY));
        grunt2 = new Enemy((int) (Math.random()*sizeX), (int) (Math.random()*sizeY));
        updateTableActual(posXActual,posYActual);
        int i=0;
        do{
            double posObjetoX=Math.random()*sizeX;
            double posObjetoY=Math.random()*sizeY;
            if(statusTable[(int) posObjetoX][(int) posObjetoY]==0){
                statusTable[(int) posObjetoX][(int) posObjetoY]=4;
                i++;
            }
        }while(i<3);
        i=0;
        do{
            double posObjetoX=Math.random()*sizeX;
            double posObjetoY=Math.random()*sizeY;
            if(statusTable[(int) posObjetoX][(int) posObjetoY]==0){
                statusTable[(int) posObjetoX][(int) posObjetoY]=6;
                i++;
            }
        }while(i<3);
        i=0;
        do{
            double posPuertaX=Math.random()*sizeX;
            double posPuertaY=Math.random()*sizeY;
            if(statusTable[(int) posPuertaX][(int) posPuertaY]==0){
                statusTable[(int) posPuertaX][(int) posPuertaY]=10;
                i++;
            }
        }while(i<1);
        i=0;
        do{
            double posMoldeX=Math.random()*sizeX;
            double posMoldeY=Math.random()*sizeY;
            if(statusTable[(int) posMoldeX][(int) posMoldeY]==0){
                statusTable[(int) posMoldeX][(int) posMoldeY]=13;
                i++;
            }
        }while(i<1);
        i=0;
        do{
            double posPaintX=Math.random()*sizeX;
            double posPaintY=Math.random()*sizeY;
            if(statusTable[(int) posPaintX][(int) posPaintY]==0){
                statusTable[(int) posPaintX][(int) posPaintY]=16;
                i++;
            }
        }while(i<1);
        i=0;
        do{
            double posTowerX=Math.random()*sizeX;
            double posTowerY=Math.random()*sizeY;
            if(statusTable[(int) posTowerX][(int) posTowerY]==0){
                statusTable[(int) posTowerX][(int) posTowerY]=19;
                i++;
            }
        }while(i<1);
    }

    //Actualiza la zona anterior y actual del jugador
    public void updateTableActual(double x, double y){
        Image image = getPngImage("yellow");
        arrayImageView[(int) y][(int) x].setImage(image);
        if((statusTable[(int) y][(int) x]==0) || (statusTable[(int) y][(int) x]==2)){
            statusImgView.setImage(null);
            statusTable[(int) y][(int) x]=1;
        }else if((statusTable[(int) y][(int) x]==4) || (statusTable[(int) y][(int) x]==3)){
            statusImgView.setImage(null);
            if(statusTable[(int) y][(int) x]==4){
                statusTable[(int) y][(int) x]=5;
                if(pj.getKeytype().equals("black")) {
                    switch (countKey) {
                        case 0 -> setItems("1-black-key");
                        case 1 -> setItems("2-black-key");
                        case 2 -> setItems("3-black-key");
                    }
                }
                if(pj.getKeytype().equals("red")) {
                    switch (countKey) {
                        case 0 -> setItems("1-red-key");
                        case 1 -> setItems("2-red-key");
                        case 2 -> setItems("3-red-key");
                    }
                }
                countKey++;
            }else{
                statusTable[(int) y][(int) x]=5;
            }
        }else if((statusTable[(int) y][(int) x]==6) || (statusTable[(int) y][(int) x]==7)){
            statusImgView.setImage(null);
            if(statusTable[(int) y][(int) x]==6){
                statusTable[(int) y][(int) x]=8;
                if(pj.getKeytype().equals("red")) {
                    switch (countFalseKey) {
                        case 0 -> setItems("1-black-key");
                        case 1 -> setItems("2-black-key");
                        case 2 -> setItems("3-black-key");
                    }
                }
                if(pj.getKeytype().equals("black")) {
                    switch (countFalseKey) {
                        case 0 -> setItems("1-red-key");
                        case 1 -> setItems("2-red-key");
                        case 2 -> setItems("3-red-key");
                    }
                }
                countFalseKey++;
            }else{
                statusTable[(int) y][(int) x]=8;
            }
        }else if((statusTable[(int) y][(int) x]==10) || (statusTable[(int) y][(int) x]==11)){
            Image img=getPngImage("door");
            statusImgView.setImage(img);
            statusTable[(int) y][(int) x]=9;
            if(fullKey){
                isFinished=true;
            }
        }else if((statusTable[(int) y][(int) x]==13) || (statusTable[(int) y][(int) x]==14)){
            Image img=getPngImage("mold");
            statusImgView.setImage(img);
            statusTable[(int) y][(int) x]=12;
            if((trueKey1)&&(trueKey2)&&(trueKey3)){
                if(pj.getKeytype().equals("black")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-black-key");
                    itemsArrayImageView[piece1.getPiece()].setImage(imageKey);
                    itemsArrayImageView[piece2.getPiece()].setImage(null);
                    itemsArrayImageView[piece3.getPiece()].setImage(null);
                }else if(pj.getKeytype().equals("red")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-red-key");
                    itemsArrayImageView[piece1.getPiece()].setImage(imageKey);
                    itemsArrayImageView[piece2.getPiece()].setImage(null);
                    itemsArrayImageView[piece3.getPiece()].setImage(null);
                }
            }
            if((dyedKey1)&&(dyedKey2)&&(dyedKey3)){
                if(pj.getKeytype().equals("black")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-black-key");
                    itemsArrayImageView[dyedPiece1.getPiece()].setImage(imageKey);
                    itemsArrayImageView[dyedPiece2.getPiece()].setImage(null);
                    itemsArrayImageView[dyedPiece3.getPiece()].setImage(null);
                }else if(pj.getKeytype().equals("red")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-red-key");
                    itemsArrayImageView[dyedPiece1.getPiece()].setImage(imageKey);
                    itemsArrayImageView[dyedPiece2.getPiece()].setImage(null);
                    itemsArrayImageView[dyedPiece3.getPiece()].setImage(null);
                }
            }
        }else if((statusTable[(int) y][(int) x]==16) || (statusTable[(int) y][(int) x]==17)){
            Image img=getPngImage("paint");
            statusImgView.setImage(img);
            statusTable[(int) y][(int) x]=15;
            if(falseKey1){
                if(pj.getKeytype().equals("black")){
                    Image imageKey= getPngImage("1-black-key");
                    itemsArrayImageView[pieceFalse1.getPiece()].setImage(imageKey);
                    dyedPiece1=new Item(pieceFalse1.getPiece());
                    dyedKey1=true;
                }else if(pj.getKeytype().equals("red")){
                    Image imageKey= getPngImage("1-red-key");
                    itemsArrayImageView[pieceFalse1.getPiece()].setImage(imageKey);
                    dyedPiece1=new Item(pieceFalse1.getPiece());
                    dyedKey1=true;
                }
            }
            if(falseKey2) {
                if(pj.getKeytype().equals("black")){
                    Image imageKey= getPngImage("2-black-key");
                    itemsArrayImageView[pieceFalse2.getPiece()].setImage(imageKey);
                    dyedPiece2=new Item(pieceFalse2.getPiece());
                    dyedKey2=true;
                }else if(pj.getKeytype().equals("red")){
                    Image imageKey= getPngImage("2-red-key");
                    itemsArrayImageView[pieceFalse2.getPiece()].setImage(imageKey);
                    dyedPiece2=new Item(pieceFalse2.getPiece());
                    dyedKey2=true;
                }
            }
            if(falseKey3) {
                if(pj.getKeytype().equals("black")){
                    Image imageKey= getPngImage("3-black-key");
                    itemsArrayImageView[pieceFalse3.getPiece()].setImage(imageKey);
                    dyedPiece3=new Item(pieceFalse3.getPiece());
                    dyedKey3=true;
                }else if(pj.getKeytype().equals("red")){
                    Image imageKey= getPngImage("3-red-key");
                    itemsArrayImageView[pieceFalse3.getPiece()].setImage(imageKey);
                    dyedPiece3=new Item(pieceFalse3.getPiece());
                    dyedKey3=true;
                }
            }
        }else if((statusTable[(int) y][(int) x]==19) || (statusTable[(int) y][(int) x]==20)){
            Image img=getPngImage("tower");
            statusImgView.setImage(img);
            statusTable[(int) y][(int) x]=18;
            statusLabel.setText("Enemy 1:\nX = "+grunt.getPosX()+"\nY = "+grunt.getPosY()+"\nEnemy 2:\nX = "+grunt2.getPosX()+"\nY = "+grunt2.getPosY());
        }
    }

    public void updateTableAnterior(double x, double y){
        if(statusTable[(int) y][(int) x]==1){
            Image image = getPngImage("green");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=2;
        }else if(statusTable[(int) y][(int) x]==5){
            Image image = getPngImage("blue");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=3;
        }else if(statusTable[(int) y][(int) x]==8){
            Image image = getPngImage("blue");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=7;
        }else if(statusTable[(int) y][(int) x]==9){
            Image image = getPngImage("door");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=11;
        }else if(statusTable[(int) y][(int) x]==12){
            Image image = getPngImage("mold");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=14;
        }else if(statusTable[(int) y][(int) x]==15){
            Image image = getPngImage("paint");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=17;
        }else if(statusTable[(int) y][(int) x]==18){
            Image image = getPngImage("tower");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=20;
            statusLabel.setText("");
        }
    }

    //Actualiza el inventario del jugador
    public void setItems(String path){
        if(pj.getSlots()>slotsfilled){
            Image image = getPngImage(path);
            itemsArrayImageView[slotsfilled]=new ImageView(image);
            pane.getChildren().add(itemsArrayImageView[slotsfilled]);
            itemsArrayImageView[slotsfilled].setFitHeight(75);
            itemsArrayImageView[slotsfilled].setFitWidth(75);
            if(slotsfilled<3){
                itemsArrayImageView[slotsfilled].setLayoutX(totalX+30);
            }else{
                itemsArrayImageView[slotsfilled].setLayoutX(totalX+80);
            }
            boolean isEmpty=true;
            int y=2;
            for(int i=0;i<=inventory.length-1;i++){
                if((inventory[i]==0) && (isEmpty)){
                    itemsArrayImageView[slotsfilled].setLayoutY(y);
                    inventory[i]=1;
                    slotsfilled++;
                    if(statusTable[(int) posYActual][(int) posXActual]==5){
                        switch (countKey){
                            case 0:
                                piece1 =new Item(i);
                                trueKey1=true;
                                break;
                            case 1:
                                piece2 =new Item(i);
                                trueKey2=true;
                                break;
                            case 2:
                                piece3 =new Item(i);
                                trueKey3=true;
                                break;
                        }
                    }
                    if(statusTable[(int) posYActual][(int) posXActual]==8){
                        switch (countFalseKey){
                            case 0:
                                pieceFalse1 =new Item(i);
                                falseKey1=true;
                                break;
                            case 1:
                                pieceFalse2 =new Item(i);
                                falseKey2=true;
                                break;
                            case 2:
                                pieceFalse3 =new Item(i);
                                falseKey3=true;
                                break;
                        }
                    }
                    isEmpty=false;
                }else{
                   if(slotsfilled>=3){
                       y=2+(slotsfilled-3)*55;
                   }else{
                       y+=55;
                   }
                }
            }
        }
    }

    //Genera la zona de juego
    public void setTable(ImageView[][] arrayImageView,int[][] statusTable){
        int y=5,x=100;
        totalX=x;
        boolean stopTotalX=false;
        for(int i=0;i<=arrayImageView.length-1;i++){
            for(int j=0;j<=arrayImageView.length-1;j++){
                Image image = getPngImage("red");
                arrayImageView[i][j]=new ImageView(image);
                pane.getChildren().add(arrayImageView[i][j]);
                arrayImageView[i][j].setFitHeight(20);
                arrayImageView[i][j].setFitWidth(20);
                arrayImageView[i][j].setLayoutX(x);
                arrayImageView[i][j].setLayoutY(y);
                x+=30;
                if(!stopTotalX){
                    totalX+=30;
                }
            }
            if(!stopTotalX){
                stopTotalX=true;
            }
            y+=30;
            x=100;
        }
        for(int i=0;i<=statusTable.length-1;i++){
            for(int j=0;j<=statusTable.length-1;j++){
                statusTable[i][j]=0;
            }
        }
        for(int i=0;i<=inventory.length-1;i++){
            inventory[i]=0;
        }
    }

    //Muestra el personaje seleccionado.
    public void characterChoosed(File charinfo) throws IOException {
        FileReader fr= new FileReader(charinfo);
        LineNumberReader lnr= new LineNumberReader(fr);
        name = lnr.readLine();
        hp = Integer.parseInt(lnr.readLine());
        slots = Integer.parseInt(lnr.readLine());
        keytype = lnr.readLine();
        pj=new Player(name,hp,slots,keytype);
        if(pj.getName().equals("Sir Qovun")){
            setImgvCharacter("src/main/resources/Characters/sir_qovun.png");
        }else if(pj.getName().equals("Darki")){
            setImgvCharacter("src/main/resources/Characters/Darki.png");
        }
    }

    @javafx.fxml.FXML
    public void movement(ActionEvent actionEvent) throws IOException {
        boolean salir=false;
        do{
            enemyMove(grunt);
            enemyMove(grunt2);
            if((grunt.getPosX()!=grunt2.getPosX()) || (grunt.getPosY()!=grunt2.getPosY())){
                salir=true;
            }
        }while(!salir);
        if(actionEvent.getSource()==btUp){
            if((int) posYActual!=0){
                posYAnterior=posYActual;
                posXAnterior=posXActual;
                posYActual--;
                updateTableActual(posXActual,posYActual);
                updateTableAnterior(posXAnterior,posYAnterior);
            }
        }else if(actionEvent.getSource()==btDown) {
            if ((int) posYActual != sizeX-1) {
                posYAnterior = posYActual;
                posXAnterior = posXActual;
                posYActual++;
                updateTableActual(posXActual, posYActual);
                updateTableAnterior(posXAnterior, posYAnterior);
            }
        }else if(actionEvent.getSource()==btRight) {
            if ((int) posXActual != sizeY-1) {
                posYAnterior = posYActual;
                posXAnterior = posXActual;
                posXActual++;
                updateTableActual(posXActual, posYActual);
                updateTableAnterior(posXAnterior, posYAnterior);
            }
        }else if(actionEvent.getSource()==btLeft) {
            if ((int) posXActual != 0) {
                posYAnterior = posYActual;
                posXAnterior = posXActual;
                posXActual--;
                updateTableActual(posXActual, posYActual);
                updateTableAnterior(posXAnterior, posYAnterior);
            }
        }
        System.out.println("Jugador: "+(int) posXActual+" "+(int) posYActual);
        System.out.println("Grunt: "+grunt.getPosX()+" "+grunt.getPosY());
        System.out.println("Grunt2: "+grunt2.getPosX()+" "+grunt2.getPosY());
        isHit(grunt);
        isHit(grunt2);
        if(isFinished){
            finishGame(actionEvent);
        }else if(pj.getHp()==0){
            finishDeathGame(actionEvent);
        }
    }

    public void isHit(Enemy enemy){
        if(((int) posXActual==enemy.getPosX()) && ((int) posYActual==enemy.getPosY())){
            enemy.setPosX((int) (Math.random() * 10));
            enemy.setPosY((int) (Math.random() * 10));
            pj.setHp(pj.getHp()-1);
            labelTxt.setText(pj.getHp()+"/"+hptotal);
            Image img=getPngImage("grunt");
            statusImgView.setImage(img);
        }
    }

    //Un enemigo se mueve
    public void enemyMove(Enemy enemy){
        int randomX= (int) (Math.random() * 10);
        int randomY= (int) (Math.random() * 10);
        int randomDirection = (int) (Math.random() * 10);
        boolean salir=false;
        int tries=0;
        do{
            if(randomDirection>=5){
                if(randomX>=5){
                    enemy.setPosX(enemy.getPosX()+1);
                    if(enemy.getPosX()>=sizeX){
                        enemy.setPosX(9);
                    }
                }else{
                    enemy.setPosX(enemy.getPosX()-1);
                    if(enemy.getPosX()<=0){
                        enemy.setPosX(0);
                    }
                }
            }else{
                if(randomY>=5){
                    enemy.setPosY(enemy.getPosY()+1);
                    if(enemy.getPosY()>=sizeY){
                        enemy.setPosY(9);
                    }
                }else{
                    enemy.setPosY(enemy.getPosY()-1);
                    if(enemy.getPosY()<=0){
                        enemy.setPosY(0);
                    }
                }
            }
            if(tries >= 5){
                enemy.setPosX((int) (Math.random() * 10));
                enemy.setPosY((int) (Math.random() * 10));
            }
            if(statusTable[enemy.getPosY()][enemy.getPosX()]<=8){
                salir=true;
            }else{
                tries++;
            }
        }while(!salir);
    }

    //Set del imagen view del personaje
    public void setImgvCharacter(String path){
        File file = new File(path);
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = new Image(localUrl, false);
        imgvCharacter.setImage(image);
    }

    //Set del corazon de la vida
    public void setHeart(){
        Image imageHeart = getPngImage("heart");
        imgvHeart.setImage(imageHeart);
        imgvHeart.setFitHeight(50);
        imgvHeart.setFitWidth(50);
    }

    //Devuelve png en una image
    public Image getPngImage(String name){
        File file = new File("src/main/resources/img/"+name+".png");
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = new Image(localUrl, false);
        return image;
    }

    public void finishGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("win-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,400);
        Stage stage = new Stage();
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void finishDeathGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("death-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),600,400);
        Stage stage = new Stage();
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}