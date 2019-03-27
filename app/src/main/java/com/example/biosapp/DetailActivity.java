package com.example.biosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private ImageView _image;
    private TextView _title;
    private TextView _desc;
    private TextView _overview;
    private RatingBar _rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras = this.getIntent().getExtras();
        //Movie movie = (Movie) extras.getpa("MOVIE!");
        Movie movie = (Movie) getIntent().getExtras().get("MOVIE!");

        _title = findViewById(R.id.detail_title);
        _desc = findViewById(R.id.detail_description);
        //_image = findViewById(R.id.detail_image);
        _rating = findViewById(R.id.detail_ratingBar);
        _overview = findViewById(R.id.detail_overview);

        _title.setText(movie.title);
        _rating.setRating(movie.rating);
        _overview.setText(movie.description);
        _desc.setText(movie.description);
        new MainActivity.DownloadImageFromInternet((ImageView) findViewById(R.id.detail_image))
                .execute(movie.picture);

    }

    public void setMovie(Movie item){
        _title.setText(item.title);
        _desc.setText(item.description);
        _rating.setRating(item.rating);
    }
}
