package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment{

    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b6df984eba8e46d43326f404be37161a&language=en-EN&page=1";
    private static final String detailurl = "https://api.themoviedb.org/3/movie/";
    private static final String api = "?api_key=b6df984eba8e46d43326f404be37161a";
    private ProgressDialog dialog;
    private ListView listView;
    private ArrayList<Movie> array = new ArrayList<Movie>();
    private MovieAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    Movie movie;
    static boolean shouldExecuteOnResume;

    @Override
    public void onResume() {
        if(shouldExecuteOnResume){
            array = loadData("movies");
            adapter = new MovieAdapter(listView.getContext(), array);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            super.onResume();
        } else{
            shouldExecuteOnResume = true;
        }
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldExecuteOnResume = false;
        View view = inflater.inflate(R.layout.overview_fragment, container, false);

        if(loadData("movies") == null){
            doJsonRequest();
        }else{
            array = loadData("movies");
        }
       // doJsonRequest();


        listView = view.findViewById(R.id.list);
        adapter = new MovieAdapter(listView.getContext(), array);
        listView.setAdapter(adapter);


        //pass movie data to detail activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", array.get(position));
                startActivity(i);
            }
        });


        return view;
    }

    public void doJsonRequest(){
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0; i< jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        movie = new Movie();
                        movie.setId(obj.getInt("id"));
                        movie.setTitle(obj.getString("title"));
                        movie.setDescription(obj.getString("overview"));
                        movie.setPicture( "http://image.tmdb.org/t/p/w185/" +obj.getString("poster_path"));
                        movie.setRating( (float) obj.getDouble("vote_average")/2);
                        array.add(movie);

                        Log.d("FILM", movie.getTitle());
                    }
                    hideDialog();
                    saveData(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getmInstance().addToRequestQueue(request);
    }


    public void saveData(ArrayList<Movie> movies){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        editor.putString("movies", json);
        editor.apply();     // This line is IMPORTANT !!
    }

    public ArrayList<Movie> loadData(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public interface OnItemSelectedListener {
        void onItemSelected(Movie item);
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

}
