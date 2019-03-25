package com.example.biosapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private ImageView _image;
    private TextView _title;
    private TextView _desc;
    private RatingBar _rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        _image = view.findViewById(R.id.listview_image);
        _title = view.findViewById(R.id.listview_item_title);
        _desc = view.findViewById(R.id.listview_item_short_description);
        _rating = view.findViewById(R.id.ratingBar);
        return view;
    }

    public void setMovie(Movie item){
        _image.setImageResource(item.picture);
        _title.setText(item.name);
        _desc.setText(item.description);
        _rating.setNumStars(item.rating);
    }
}
