package com.darkforest.darkforest.Class;

public class Item {

    //Proyecto de fin de curso creador por Antonio Cara Esteban

    int piece;

    //Sirve para saber en parte de la array del inventario están para reorgalizarlos mejor
    public Item(int piece) {
        this.piece = piece;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }
}
