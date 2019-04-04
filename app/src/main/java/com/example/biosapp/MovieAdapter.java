package com.example.biosapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private Context context;
    private List<Movie> movies;
    String message;
    Button button;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();

    public MovieAdapter(@NonNull Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }



    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_activity, null);
        }
        //if(imageLoader == null){
            imageLoader=AppController.getmInstance().getmImageLoader();
            ImageView imageView = (ImageView) convertView.findViewById(R.id.listview_image);

            TextView title = (TextView) convertView.findViewById((R.id.listview_item_title));
            TextView desc = (TextView) convertView.findViewById(R.id.listview_item_short_description);
            RatingBar rating = (RatingBar) convertView.findViewById(R.id.detail_ratingBar);
        button = convertView.findViewById(R.id.bt);

        final Movie movie = movies.get(position);
        Picasso.get().load(movie.getPicture()).into(imageView);

        title.setText(movie.getTitle());
        String description = movie.getDescription().substring(0, movie.getDescription().length()/4);
        desc.setText(description + "...");
        rating.setRating(movie.getRating());


        if(!movie.getInMylist()){
            button.setBackgroundResource(R.drawable.ic_add_black_24dp);

        }else{
            button.setBackgroundResource(R.drawable.ic_remove_black_24dp);

        }

        button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(movie.getInMylist()){
                    message =  "Removed from my list";
                    v.setBackgroundResource(R.drawable.ic_add_black_24dp);

                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setInMylist(false);
                        }
                    }
                    saveData((ArrayList)movies);
                    movies = loadData();


                }else{
                    message =  "Added to my list";
                    v.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setInMylist(true);
                        }
                    }
                    saveData((ArrayList)movies);
                    movies = loadData();


                }
//                    saveData((ArrayList)movies);
                    //movies = loadData();
                //!movie.getInMylist() = movie.getInMylist();
                toastMsg(message);
            }
        });

        //}
        return convertView;

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void saveData(ArrayList<Movie> movies){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        editor.putString("movies", json);
        editor.apply();     // This line is IMPORTANT !!
    }

    public ArrayList<Movie> loadData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("movies", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }






}
