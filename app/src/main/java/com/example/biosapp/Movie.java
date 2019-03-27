package com.example.biosapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
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

    protected Movie(Parcel in) {
        title = in.readString();
        picture = in.readString();
        rating = in.readFloat();
        description = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(picture);
        dest.writeFloat(rating);
        dest.writeString(description);
    }
}
