package com.example.biosapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnItemSelectedListener {
    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b6df984eba8e46d43326f404be37161a&language=en-US&page=1";
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

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d(TAG, response.toString());
//            hideDialog();
//            for(int i=0; i< response.length(); i++){
//                try{
//                    JSONObject obj = response.getJSONObject(i);
//                    Movie movie = new Movie();
//                    movie.setTitle(obj.getString("title"));
//                    movie.setDescription(obj.getString("overview"));
//                    movie.setPicture( Uri.parse(obj.getString("image")));
//                    movie.setRating( (float) obj.getDouble("vote_average")/2);
//                    Log.d("FILM", movie.getTitle());
//                    array.add(movie);
//                }catch(JSONException ex){
//                    ex.printStackTrace();
//                }
//            }
//            adapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideDialog();
//            }
//        });

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideDialog();
                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0; i< jsonArray.length(); i++){
                        Log.d("FILM", "Nurleyanti");
                        JSONObject obj = jsonArray.getJSONObject(i);

                        //String title = obj.getString("title");
                        Movie movie = new Movie();
                    movie.setTitle(obj.getString("title"));
                    movie.setDescription(obj.getString("overview"));
                    movie.setPicture( "http://image.tmdb.org/t/p/w185/" +obj.getString("poster_path"));

                    movie.setRating( (float) obj.getDouble("vote_average")/2);
                    array.add(movie);
                    Log.d("FILM", movie.getTitle());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        listView = findViewById(R.id.list_view);
        adapter = new MovieAdapter(listView.getContext(), array);
        listView.setAdapter(adapter);

        AppController.getmInstance().addToRequestQueue(request);

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

    public static class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


}
