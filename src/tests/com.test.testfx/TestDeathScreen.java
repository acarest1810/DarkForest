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
public class TestDeathScreen {
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("death-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setMinWidth(650);
        stage.setMinHeight(450);
        stage.setMaxWidth(750);
        stage.setMaxHeight(550);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/icon.png").toString()));
        stage.setTitle("Defeat");
        stage.setScene(scene);
        stage.show();
    }

    //Test del selector de personajes
    @Test
    void return_main_menu(FxRobot robot) {
        FxAssert.verifyThat(robot.window("Defeat"), WindowMatchers.isShowing());
        robot.clickOn("#btReturn");
        FxAssert.verifyThat(robot.window("Dark Forest"), WindowMatchers.isShowing());
    }
}
