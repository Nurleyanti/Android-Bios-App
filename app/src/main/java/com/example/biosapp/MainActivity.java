package com.example.biosapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnItemSelectedListener {
    private static final String url = "https://api.androidhive.info/json/movies.json";
    private ProgressDialog dialog;
    private List<Movie> array = new ArrayList<Movie>();
    private ListView listView;
    private MovieAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
            hideDialog();
            for(int i=0; i< response.length(); i++){
                try{
                    JSONObject obj = response.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setTitle(obj.getString("title"));
                    movie.setDescription(obj.getString("title"));
                    movie.setPicture( Uri.parse(obj.getString("image")));
                    movie.setRating( (float) obj.getDouble("rating")/2);

                    array.add(movie);
                }catch(JSONException ex){
                    ex.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideDialog();
            }
        });
        listView = findViewById(R.id.list_view);
        adapter = new MovieAdapter(listView.getContext(), array);
        listView.setAdapter(adapter);

        AppController.getmInstance().addToRequestQueue(jsonArrayRequest);

//        final MovieAdapter adapter = new MovieAdapter(this, R.layout.listview_activity);
//        adapter.add(new Movie("Corgi", R.drawable.corgi, 4, "Corgi Rex is de meest geliefde hond van het Britse koningshuis en ondanks dat hij zich niet altijd aan de regels houdt is hij lievelingetje van de Koningin."));
//        adapter.add(new Movie("Captain Marvel", R.drawable.marvel, 4, "Marvel Studios’ Captain Marve is een compleet nieuw avontuur dat zich afspeelt in 1990, een tot nu toe verborgen periode uit de geschiedenis van het Marvel Cinematic Universe."));
//        adapter.add(new Movie("Us", R.drawable.us, 3.5, "De innovatieve filmmaker en Oscarwinnaar Jordan Peele (Get Out) komt opnieuw met een originele en controversiële thriller: Us."));
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
    }

    public void hideDialog(){
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onItemSelected(Movie item)
    {
//        DetailFragment fragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);
//
//        fragment.setMovie(item);
    }


}
