package com.darkforest.darkforest.Controladores;

import com.darkforest.darkforest.Class.Enemy;
import com.darkforest.darkforest.Class.Item;
import com.darkforest.darkforest.Class.Player;
import com.darkforest.darkforest.MainMenu;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

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
    private ImageView imgViewShield1;
    @javafx.fxml.FXML
    private Button btBack;
    @javafx.fxml.FXML
    private ImageView imgViewGrunt;

    //Variables para controlar el juego
    String name="",keytype="";
    int hp=0,slots=0,hptotal=0,sizeX=10,sizeY=10,totalX=0,countKey=0,countFalseKey=0,
            slotsfilled=0,shieldcount=0,pastXgrunt=0,pastYgrunt=0,pastXgrunt2=0,pastYgrunt2=0;
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
    //21 = El jugador está en un escudo; 22 = Donde se ha generado un escudo; 23 = El jugador sabe donde estaba un escudo
    int[][] statusTable;


    //Proyecto de fin de curso creador por Antonio Cara Esteban
    public void initialize() throws IOException {
        File charinfo=new File(System.getProperty("user.dir"), "personaje.txt");
        characterChoosed(charinfo);
        inventory = new int[pj.getSlots()];
        setHeart();
        controlTable();
        hptotal=pj.getHp();
        labelTxt.setText(pj.getHp()+"/"+hptotal);
        //El jugador se puede mover con WASD
        pane.setOnKeyPressed(keyListener);
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
        //Genera trozo de llave
        do{
            double posObjetoX=Math.random()*sizeX;
            double posObjetoY=Math.random()*sizeY;
            if(statusTable[(int) posObjetoX][(int) posObjetoY]==0){
                statusTable[(int) posObjetoX][(int) posObjetoY]=4;
                i++;
            }
        }while(i<3);
        i=0;
        //Genera trozo de llave inservible
        do{
            double posObjetoX=Math.random()*sizeX;
            double posObjetoY=Math.random()*sizeY;
            if(statusTable[(int) posObjetoX][(int) posObjetoY]==0){
                statusTable[(int) posObjetoX][(int) posObjetoY]=6;
                i++;
            }
        }while(i<3);
        i=0;
        //Genera puerta
        do{
            double posPuertaX=Math.random()*sizeX;
            double posPuertaY=Math.random()*sizeY;
            if(statusTable[(int) posPuertaX][(int) posPuertaY]==0){
                statusTable[(int) posPuertaX][(int) posPuertaY]=10;
                i++;
            }
        }while(i<1);
        i=0;
        //Genera molde
        do{
            double posMoldeX=Math.random()*sizeX;
            double posMoldeY=Math.random()*sizeY;
            if(statusTable[(int) posMoldeX][(int) posMoldeY]==0){
                statusTable[(int) posMoldeX][(int) posMoldeY]=13;
                i++;
            }
        }while(i<1);
        i=0;
        //Genera cubo de pintura
        do{
            double posPaintX=Math.random()*sizeX;
            double posPaintY=Math.random()*sizeY;
            if(statusTable[(int) posPaintX][(int) posPaintY]==0){
                statusTable[(int) posPaintX][(int) posPaintY]=16;
                i++;
            }
        }while(i<1);
        i=0;
        //Genera torre
        do{
            double posTowerX=Math.random()*sizeX;
            double posTowerY=Math.random()*sizeY;
            if(statusTable[(int) posTowerX][(int) posTowerY]==0){
                statusTable[(int) posTowerX][(int) posTowerY]=19;
                i++;
            }
        }while(i<1);
        i=0;
        //Genera escudo
        do{
            double posTowerX=Math.random()*sizeX;
            double posTowerY=Math.random()*sizeY;
            if(statusTable[(int) posTowerX][(int) posTowerY]==0){
                statusTable[(int) posTowerX][(int) posTowerY]=22;
                i++;
            }
        }while(i<1);
    }

    //Actualiza la zona actual del jugador
    public void updateTableActual(double x, double y){
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
                if(pj.getKeytype().equals("white")) {
                    switch (countKey) {
                        case 0 -> setItems("1-white-key");
                        case 1 -> setItems("2-white-key");
                        case 2 -> setItems("3-white-key");
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
                if(pj.getKeytype().equals("white")) {
                    switch (countFalseKey) {
                        case 0 -> setItems("1-black-key");
                        case 1 -> setItems("2-black-key");
                        case 2 -> setItems("3-black-key");
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
                }else if(pj.getKeytype().equals("white")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-white-key");
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
                }else if(pj.getKeytype().equals("white")){
                    fullKey=true;
                    Image imageKey=getPngImage("full-white-key");
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
                }else if(pj.getKeytype().equals("white")){
                    Image imageKey= getPngImage("1-white-key");
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
                }else if(pj.getKeytype().equals("white")){
                    Image imageKey= getPngImage("2-white-key");
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
                }else if(pj.getKeytype().equals("white")){
                    Image imageKey= getPngImage("3-white-key");
                    itemsArrayImageView[pieceFalse3.getPiece()].setImage(imageKey);
                    dyedPiece3=new Item(pieceFalse3.getPiece());
                    dyedKey3=true;
                }
            }
        }else if((statusTable[(int) y][(int) x]==19) || (statusTable[(int) y][(int) x]==20)){
            Image img=getPngImage("tower");
            statusImgView.setImage(img);
            statusTable[(int) y][(int) x]=18;
            showEnemy(grunt);
            pastXgrunt=grunt.getPosX();
            pastYgrunt=grunt.getPosY();
            pastXgrunt2=grunt2.getPosX();
            pastYgrunt2=grunt2.getPosY();
            showEnemy(grunt2);
        }else if((statusTable[(int) y][(int) x]==22)){
            statusImgView.setImage(null);
            statusTable[(int) y][(int) x]=21;
            shieldcount++;
            if(shieldcount==1){
                Image imageShield=getPngImage("shield");
                imgViewShield1.setImage(imageShield);
            }
        }else if((statusTable[(int) y][(int) x]==23)){
            statusImgView.setImage(null);
            statusTable[(int) y][(int) x]=21;
        }
        Image image = getPngImage("yellow");
        arrayImageView[(int) y][(int) x].setImage(image);
    }

    //Actualiza la zona anterior por que el que el jugador ha pasado
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
            backToNormal(pastXgrunt,pastYgrunt);
            backToNormal(pastXgrunt2,pastYgrunt2);
        }else if(statusTable[(int) y][(int) x]==21){
            Image image = getPngImage("blue");
            arrayImageView[(int) y][(int) x].setImage(image);
            statusTable[(int) y][(int) x]=23;
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

    //Muestra el personaje seleccionado
    public void characterChoosed(File charinfo) throws IOException {
        FileReader fr= new FileReader(charinfo);
        LineNumberReader lnr= new LineNumberReader(fr);
        name = lnr.readLine();
        hp = Integer.parseInt(lnr.readLine());
        slots = Integer.parseInt(lnr.readLine());
        keytype = lnr.readLine();
        pj=new Player(name,hp,slots,keytype);
        try{
            if(pj.getName().equals("Sir Qovun")){
                Image image = new Image(this.getClass().getResource("/Characters/sir_qovun.png").toString());
                imgvCharacter.setImage(image);
            }else if(pj.getName().equals("Darki")){
                Image image = new Image(this.getClass().getResource("/Characters/Darki.png").toString());
                imgvCharacter.setImage(image);
            }else if(pj.getName().equals("Mark")){
                Image image = new Image(this.getClass().getResource("/Characters/Mark.png").toString());
                imgvCharacter.setImage(image);
            }
        }catch (Exception e){
            imgvCharacter.setImage(null);
        }
    }

    //Se mueve con los botones
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
            moveUp();
        }else if(actionEvent.getSource()==btDown) {
            moveDown();
        }else if(actionEvent.getSource()==btRight) {
            moveRight();
        }else if(actionEvent.getSource()==btLeft) {
            moveLeft();
        }
        isHit(grunt);
        isHit(grunt2);
        if(isFinished){
            finishGame(actionEvent);
        }else if(pj.getHp()==0){
            finishDeathGame(actionEvent);
        }
    }

    //Se mueve con las teclas WASD
    private EventHandler<KeyEvent> keyListener = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            if((event.getCode() == KeyCode.W) || (event.getCode() == KeyCode.S) ||
                    (event.getCode() == KeyCode.A) || (event.getCode() == KeyCode.D)){
                boolean salir = false;
                do {
                    enemyMove(grunt);
                    enemyMove(grunt2);
                    if ((grunt.getPosX() != grunt2.getPosX()) || (grunt.getPosY() != grunt2.getPosY())) {
                        salir = true;
                    }
                } while (!salir);
                if (event.getCode() == KeyCode.W) {
                    moveUp();
                } else if (event.getCode() == KeyCode.S) {
                    moveDown();
                } else if (event.getCode() == KeyCode.D) {
                    moveRight();
                } else if (event.getCode() == KeyCode.A) {
                    moveLeft();
                }
                isHit(grunt);
                isHit(grunt2);
                if (isFinished) {
                    try {
                        finishGame(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (pj.getHp() == 0) {
                    try {
                        finishDeathGame(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            }
        }
    };

    public void moveUp(){
        if((int) posYActual!=0){
            posYAnterior=posYActual;
            posXAnterior=posXActual;
            posYActual--;
            updateTableActual(posXActual,posYActual);
            updateTableAnterior(posXAnterior,posYAnterior);
        }
    }

    public void moveDown(){
        if ((int) posYActual != sizeX-1) {
            posYAnterior = posYActual;
            posXAnterior = posXActual;
            posYActual++;
            updateTableActual(posXActual, posYActual);
            updateTableAnterior(posXAnterior, posYAnterior);
        }
    }

    public void moveRight(){
        if ((int) posXActual != sizeY-1) {
            posYAnterior = posYActual;
            posXAnterior = posXActual;
            posXActual++;
            updateTableActual(posXActual, posYActual);
            updateTableAnterior(posXAnterior, posYAnterior);
        }
    }

    public void moveLeft(){
        if ((int) posXActual != 0) {
            posYAnterior = posYActual;
            posXAnterior = posXActual;
            posXActual--;
            updateTableActual(posXActual, posYActual);
            updateTableAnterior(posXAnterior, posYAnterior);
        }
    }

    public void isHit(Enemy enemy){
        if(((int) posXActual==enemy.getPosX()) && ((int) posYActual==enemy.getPosY())){
            enemy.setPosX((int) (Math.random() * 10));
            enemy.setPosY((int) (Math.random() * 10));
            switch (shieldcount){
                case 0:
                    pj.setHp(pj.getHp()-1);
                    labelTxt.setText(pj.getHp()+"/"+hptotal);
                    Image img=getPngImage("grunt");
                    GruntShowThread gr=new GruntShowThread(img,imgViewGrunt);
                    new Thread(gr).start();
                    break;
                case 1:
                    imgViewShield1.setImage(null);
                    shieldcount--;
                    Image img2=getPngImage("grunt");
                    GruntShowThread gr2=new GruntShowThread(img2,imgViewGrunt);
                    new Thread(gr2).start();
                    break;
            }
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
            //No puedenacceder a zonas importantes
            if(statusTable[enemy.getPosY()][enemy.getPosX()]<=8){
                salir=true;
            }else{
                tries++;
            }
        }while(!salir);
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
        Image image = new Image(this.getClass().getResource("/img/"+name+".png").toString());
        return image;
    }

    //Muestra los enemigos en el mapa
    public void showEnemy(Enemy enemy){
        Image image = getPngImage("grunt");
        arrayImageView[enemy.getPosY()][enemy.getPosX()].setImage(image);
    }

    //Regresa la imagen de donde estaban los enemigos a la normalidad
    public void backToNormal(int x,int y){
        if((statusTable[(int) y][(int) x]==0) || (statusTable[(int) y][(int) x]==4) || (statusTable[(int) y][(int) x]==6)){
            Image image = getPngImage("red");
            arrayImageView[y][x].setImage(image);
        }else if(statusTable[(int) y][(int) x]==2){
            Image image = getPngImage("green");
            arrayImageView[y][x].setImage(image);
        }else if((statusTable[(int) y][(int) x]==3) || (statusTable[(int) y][(int) x]==7)) {
            Image image = getPngImage("blue");
            arrayImageView[y][x].setImage(image);
        }
    }

    //Pantalla de victoria
    public void finishGame(Event actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("win-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),650,450);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setTitle("Victory");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    //Pantalla de muerte
    public void finishDeathGame(Event actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("death-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),650,450);
        Stage stage = new Stage();
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setTitle("Defeat");
        stage.setScene(scene);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @javafx.fxml.FXML
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
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