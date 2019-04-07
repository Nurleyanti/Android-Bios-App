package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.resources.TextAppearance;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String url = "https://api.themoviedb.org/3/movie/";
    private static final String api = "?api_key=b6df984eba8e46d43326f404be37161a";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView _image;
    private TextView _title;
    private TextView _desc;
    private TextView _duration;
    private TextView _overview;
    private TextView _genres;
    private TextView _date;
    private RatingBar _rating;
    private Button _seen;
    private Button _inMyList;
    Movie movie;
    List<Movie> movies;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        _title = findViewById(R.id.detail_title);
        _desc = findViewById(R.id.detail_description);
        _image = findViewById(R.id.detail_image);
        _rating = findViewById(R.id.detail_ratingBar);
        _overview = findViewById(R.id.detail_overview);
        _duration = findViewById(R.id.detail_duration);
        _genres = findViewById(R.id.detail_genres);
        _date = findViewById(R.id.detail_date);
        _seen = findViewById(R.id.seen_button);
        _inMyList = findViewById(R.id.profile_button);

        Movie intentMovie = (Movie) getIntent().getExtras().get("MOVIE!");
        movies = loadData("movies");
        for(int i = 0; i< movies.size(); i++){
            if(intentMovie.id == movies.get(i).id){
                movie = movies.get(i);
            }
        }
        String newUrl;
        newUrl = url +movie.id + api;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    int runtime = response.getInt("runtime");
                    movie.setDuration(runtime);
                    String releaseDate = response.getString("release_date");

                    _date.setText("Release date: "+ releaseDate);

                    JSONArray genreArry = response.getJSONArray("genres");
                    ArrayList<String> genre = new ArrayList<String>();
                    for (int j = 0; j < genreArry.length(); j++) {
                        JSONObject obj = genreArry.getJSONObject(j);
                        genre.add((String) obj.getString("name"));
                    }
                    movie.setGenres(genre);
                    StringBuilder builder = new StringBuilder();
                    for (String details : movie.getGenres()) {
                        builder.append(details + " ");
                    }

                    _genres.setText("Genres: " + builder.toString());

                    _title.setText(movie.title);
                    _rating.setRating(movie.rating);
                    _overview.setText(movie.description);
                    String description = movie.description.substring(0, movie.getDescription().length()/2);
                    _desc.setText(description + "...");
                    _duration.setText("Duration: " + movie.getDuration()  +
                            " minutes");

//                    new MainActivity.DownloadImageFromInternet((ImageView) findViewById(R.id.detail_image))
//                            .execute(movie.picture);
                    Picasso.get().load(movie.getPicture()).into(_image);
                    if(!movie.getSeen()){
                        _seen.setText("Watched");
                    }else{
                        _seen.setText("Watched already");
                        _seen.setTextSize(10);
                        _seen.setBackgroundColor(Color.parseColor("#82E0AA"));
                    }

                    if(!movie.getInMylist()){
                        _inMyList.setText("Add to my list");
                    }else{
                        _inMyList.setText("Remove from my list");
                        _inMyList.setTextSize(10);
                        _inMyList.setBackgroundColor(Color.parseColor("#82E0AA"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        AppController.getmInstance().addToRequestQueue(request);

        _seen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!movie.getSeen()){
                    toastMsg("Movie added to profile");
                    _seen.setText("Watched already");
                    _seen.setTextSize(10);
                    v.setBackgroundColor(Color.parseColor("#82E0AA"));
                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setSeen(true);
                        }
                    }

                    saveData((ArrayList)movies);
                }else{
                    toastMsg("Movie removed from profile");
                    _seen.setText("Watched");
                    v.setBackgroundResource(android.R.drawable.btn_default);
                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setSeen(false);
                        }
                    }
                    saveData((ArrayList)movies);
                }
            }
        });

        _inMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!movie.getInMylist()){
                    toastMsg("Added to my list");
                    _inMyList.setText("Remove from list");
                    _inMyList.setTextSize(10);
                    v.setBackgroundColor(Color.parseColor("#82E0AA"));
                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setInMylist(true);
                        }
                    }
                    saveData((ArrayList)movies);
                }else{
                    toastMsg("Removed from my list");
                    _inMyList.setText("Add to list");
                    v.setBackgroundResource(android.R.drawable.btn_default);
                    for(int i = 0; i < movies.size(); i++){
                        if (movies.get(i).id == movie.id){
                            movies.get(i).setInMylist(false);
                        }
                    }
                    saveData((ArrayList)movies);
                }

            }
        });

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void saveData(ArrayList<Movie> movies){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        editor.putString("movies", json);
        editor.apply();     // This line is IMPORTANT !!
    }

    public ArrayList<Movie> loadData(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }




}
