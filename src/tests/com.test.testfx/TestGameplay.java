package com.test.testfx;

import com.darkforest.darkforest.MainMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class TestGameplay {

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setTitle("Dark Forest");
        stage.setScene(scene);
        stage.show();
    }

    //Test del selector de personajes
    @Test
    void select_character(FxRobot robot) {
        robot.clickOn("#bttCharacter");
        FxAssert.verifyThat(robot.window("Select Character"), WindowMatchers.isShowing());
        robot.clickOn("#rbDarki");
        robot.clickOn("#btConfirm");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
    }

    //Test de las instrucciones del juego
    @Test
    void how_to_play(FxRobot robot) {
        robot.clickOn("#btHowTo");
        FxAssert.verifyThat(robot.window("How to play"), WindowMatchers.isShowing());
        robot.clickOn("#btBack");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
    }

    //Test de las instrucciones del juego
    @Test
    void go_back(FxRobot robot) {
        robot.clickOn("#bttStart");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
        robot.clickOn("#btBack");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
    }

    //Test de las instrucciones del juego
    @Test
    void gameplay(FxRobot robot) throws IOException {
        robot.clickOn("#bttStart");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
        int num=0;
        boolean salir=false;
        do{
            num= (int) (Math.random() * 4);
            switch (num) {
                case 0 -> robot.clickOn("#btUp");
                case 1 -> robot.clickOn("#btRight");
                case 2 -> robot.clickOn("#btLeft");
                case 3 -> robot.clickOn("#btDown");
            }
        }while(!salir);
    }
}
