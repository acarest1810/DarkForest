package com.darkforest.darkforest.Controladores;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GruntShowThread extends Thread{

    Image img;
    ImageView imgView;

    public synchronized void run(){
        int i=0;
        imgView.setImage(img);
        do{
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    imgView.setImage(null);
                }
            });
            i++;
        }while(i==0);
    }

    public GruntShowThread(Image img, ImageView imgView){
        this.img=img;
        this.imgView=imgView;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }
}
