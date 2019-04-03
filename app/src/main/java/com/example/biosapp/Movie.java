package com.example.biosapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Movie implements Parcelable {
    public int id;
    public String title;
    public String picture;
    public float rating;
    public String description;
    public int duration;
    public ArrayList<String> genres;
    public boolean inMyList = false;

    public Movie() {
    }

    public Movie(int id, String title, String picture, float rating, String description, int duration, ArrayList<String> genres, boolean inMyList){
        this.id = id;
     this.title = title;
     this.picture = picture;
     this.rating = rating;
     this.description = description;
     this.duration = duration;
     this.genres = genres;
     this.inMyList = inMyList;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        picture = in.readString();
        rating = in.readFloat();
        description = in.readString();
        duration = in.readInt();
        genres = in.createStringArrayList();
        inMyList = in.readInt() == 1;
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

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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

    public int getDuration(){
        return duration;
    }

    public void setDuration(int id){
        this.duration = duration;
    }

    public ArrayList<String> getGenres(){
        return genres;
    }

    public void setGenres(ArrayList<String> genres){
        this.genres = genres;
    }

    public boolean getInMylist(){
        return inMyList;
    }

    public void setInMylist(boolean inMyList){
        this.inMyList = inMyList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(picture);
        dest.writeFloat(rating);
        dest.writeString(description);
        dest.writeInt(duration);
        dest.writeList(genres);
        dest.writeInt(inMyList ? 1 : 0);
    }
}
