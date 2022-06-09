package com.darkforest.darkforest.Controladores;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerGameFieldTest {

    //Comprobacion de que el metodo carga correctamente
    @Test
    void moveUp() {
        ControllerGameField cgf=new ControllerGameField();
        cgf.moveUp();
    }
}