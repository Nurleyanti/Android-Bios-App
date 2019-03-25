package com.example.biosapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(@NonNull Context context, int resource, Movie[] objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_activity, parent, false);
        }
        Movie movie = getItem(position);
        ImageView imageview = convertView.findViewById(R.id.listview_image);

        TextView title = convertView.findViewById(R.id.listview_item_title);

        TextView desc = convertView.findViewById(R.id.listview_item_short_description);

        if(movie!= null){
            imageview.setImageResource(movie.picture);
            title.setText(movie.name);
            desc.setText(movie.description);

        }else{
            title.setText("Pokemon not found");
        }
        return convertView;
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
