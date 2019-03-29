package com.example.biosapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private static final String url = "https://api.themoviedb.org/3/movie/";
    private static final String api = "?api_key=b6df984eba8e46d43326f404be37161a";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView _image;
    private TextView _title;
    private TextView _desc;
    private TextView _duration;
    private TextView _overview;
    private RatingBar _rating;
    private int movieId;
    private ProgressDialog dialog;
    Movie movie;
    Movie extraMovie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Bundle extras = this.getIntent().getExtras();

        movie = (Movie) getIntent().getExtras().get("MOVIE!");
        String newUrl;
        newUrl = url +movie.id + api;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, newUrl, null,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for(int i = 0;i <response.length();i++){
                    try{

                        jsonObject = response.getJSONObject(i);
                        movie.setDuration(jsonObject.getInt("duration"));

                        // Genre is json array
                        JSONArray genreArry = jsonObject.getJSONArray("genres");
                        ArrayList<String> genre = new ArrayList<String>();
                        for (int j = 0; j < genreArry.length(); j++) {
                            JSONObject obj = genreArry.getJSONObject(j);
                            genre.add((String) obj.getString("name"));
                        }
                        movie.setGenres(genre);
                    } catch(JSONException e){
                        e.printStackTrace();

                    }//adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getmInstance().addToRequestQueue(jsonArrayRequest);

        _title = findViewById(R.id.detail_title);
        _desc = findViewById(R.id.detail_description);
        //_image = findViewById(R.id.detail_image);
        _rating = findViewById(R.id.detail_ratingBar);
        _overview = findViewById(R.id.detail_overview);
        _duration = findViewById(R.id.detail_duration);

        _title.setText(movie.title);
        _rating.setRating(movie.rating);
        _overview.setText(movie.description);
        _desc.setText(movie.description.substring(0, 90)+"...");
        _duration.setText("Duration: " + movie.duration  +
                " mins");
        new MainActivity.DownloadImageFromInternet((ImageView) findViewById(R.id.detail_image))
                .execute(movie.picture);

    }



    public void hideDialog(){
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

}
