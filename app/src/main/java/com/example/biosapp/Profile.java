package com.example.biosapp;

import java.util.ArrayList;

public class Profile {

    public String name;
    public String title;
    public ArrayList<Movie> newMovies;
    public ArrayList<Movie> myMovies;
    public ArrayList<Movie> seenMovies;
    public Profile(){

    }
    public Profile(String name, String title, ArrayList<Movie> newMovies, ArrayList<Movie> myMovies, ArrayList<Movie> seenMovies){
        this.name = name;
        this.title = title;
        this.newMovies = newMovies;
        this.myMovies = myMovies;
        this.seenMovies = seenMovies;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.title = name;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public ArrayList<Movie> getNewMovies(){
        return newMovies;
    }

    public void setNewMovies(ArrayList<Movie> newMovies){
        this.newMovies = newMovies;
    }

    public ArrayList<Movie> getMyMovies(){
        return myMovies;
    }

    public void setMyMovies(ArrayList<Movie> myMovies){
        this.myMovies = myMovies;
    }

    public ArrayList<Movie> getSeenMovies(){
        return seenMovies;
    }

    public void setSeenMovies(ArrayList<Movie> seenMovies){
        this.seenMovies = seenMovies;
    }

}
