package com.arcis.vgil.model;

/**
 * Created by JaiMishra on 05/04/16.
 */
public class Beanclass {
    private int image;
    private String title;


    public Beanclass(int image, String title){

        this.image = image;
        this.title = title;




    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
