package com.darkforest.darkforest.Class;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    //Pruebas sobe el funcionamiento sobre los set y gets
    @Test
    void getName() {
        Player pj = new Player("Sir qovun",3,7,"black");
        String name=pj.getName();
        assertEquals(name,pj.getName());
    }

    @Test
    void setHp(){
        Player pj = new Player("Sir qovun",3,7,"black");
        int hp=pj.getHp();
        pj.setHp(2);
        assertNotEquals(pj.getHp(),hp);
    }
}