package com.example.biosapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private Context context;
    private List<Movie> movies;
    String message;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();

//    public MovieAdapter(Activity activity, List<Movie> movies){
//        this.activity = activity;
//        this.movies = movies;
//    }

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
        Button button = convertView.findViewById(R.id.bt);

        Movie movie = movies.get(position);
        new MainActivity.DownloadImageFromInternet((ImageView) convertView.findViewById(R.id.listview_image))
                .execute(movie.getPicture());
        //imageView.setImageBitmap(movie.getPicture());
        title.setText(movie.getTitle());
        desc.setText(movie.getDescription().substring(0, 90)+"...");
        rating.setRating(movie.getRating());


        if(!movie.getInMylist()){
            button.setBackgroundResource(R.drawable.ic_add_black_24dp);
            message =  "Toegevoegd aan Mijn Lijst";
        }else{
            button.setBackgroundResource(R.drawable.ic_remove_black_24dp);
            message =  "Verwijderd uit Mijn Lijst";
        }

        button.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                toastMsg(message);
            }
        });

        //}
        return convertView;





        /////
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listview_activity, parent, false);
//        }
//        Movie movie = movies.get(position);
//
//        ImageView imageview = convertView.findViewById(R.id.listview_image);
//
//        TextView title = convertView.findViewById(R.id.listview_item_title);
//
//        TextView desc = convertView.findViewById(R.id.listview_item_short_description);
//
//        if(movie!= null){
//            imageview.setImageURI(movie.picture);
//            title.setText(movie.title);
//            desc.setText(movie.description);
//
//        }else{
//            title.setText("Movie not found");
//        }
//        return convertView;
    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();

    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listview_activity, parent, false);
//        }
//        Movie movie = (Movie) getItem(position);
//        ImageView imageview = convertView.findViewById(R.id.listview_image);
//        imageview.setImageResource(movie.picture);
//
//        TextView textTitle = convertView.findViewById(R.id.listview_item_title);
//        textTitle.setText(movie.name);
//
//        TextView textDesc = convertView.findViewById(R.id.listview_item_short_description);
//        textDesc.setText(movie.description);
//
//        return convertView;
//    }





}
