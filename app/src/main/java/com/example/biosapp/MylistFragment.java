package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MylistFragment extends Fragment{

    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b6df984eba8e46d43326f404be37161a&language=en-US&page=1";
    private ProgressDialog dialog;
    private List<Movie> array = new ArrayList<Movie>();
    private GridView imageGrid;
    private ArrayList<Movie> urlList;
    View view;
    ImageAdapter imageAdapter;

    @Override
    public void onResume() {
        super.onResume();
        array = getArrayList("movies");
        urlList = new ArrayList<Movie>();
        for(int i = 0; i < array.size(); i++) {
            if (array.get(i).getInMylist() == true) {
                urlList.add(array.get(i));
            }
        }
        imageAdapter = new ImageAdapter(view.getContext(), urlList);
        imageAdapter.notifyDataSetChanged();
        imageGrid.setAdapter(imageAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        view = inflater.inflate(R.layout.mylist_fragment, container, false);

        imageGrid = view.findViewById(R.id.gridView);
        array = getArrayList("movies");

        this.urlList = new ArrayList<Movie>();

        for(int i = 0; i < array.size(); i++) {
            if(array.get(i).getInMylist() == true){
                this.urlList.add(array.get(i));
            }

        }
        imageAdapter = new ImageAdapter(view.getContext(), urlList);
        imageGrid.setAdapter(imageAdapter);
        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Create intent
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", urlList.get(position));
                startActivity(i);

            }
        });


        return view;
    }


    public ArrayList<Movie> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
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
