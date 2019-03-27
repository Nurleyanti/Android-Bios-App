package com.example.biosapp;

import android.graphics.Bitmap;
import android.net.Uri;

public class Movie {
    public String title;
    public String picture;
    public float rating;
    public String description;

    public Movie() {
    }

    public Movie(String title, String picture, float rating, String description){
     this.title = title;
     this.picture = picture;
     this.rating = rating;
     this.description = description;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPicture(){
        return picture;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public float getRating(){
        return rating;
    }

    public void setRating(float rating){
        this.rating = rating;
    }



}
