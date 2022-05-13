package com.darkforest.darkforest.Class;

public class Player {
   private int hp,slots;
   private String name,keytype;

   //Constructor

    public Player(String name, int hp, int slots, String keytype) {
        this.name = name;
        this.hp = hp;
        this.slots = slots;
        this.keytype = keytype;
    }

    //Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }
}
