package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MylistFragment extends Fragment {
    private ProgressDialog dialog;
    private List<Movie> array = new ArrayList<Movie>();
    private GridView imageGrid;
    private ArrayList<Movie> mymovies;
    private View view;
    private ImageAdapter imageAdapter;

    //call when user presses back to update view
    @Override
    public void onResume() {
        super.onResume();
        array = getArrayList("movies");
        mymovies = new ArrayList<Movie>();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getInMylist() == true) {
                mymovies.add(array.get(i));
            }
        }
        imageAdapter = new ImageAdapter(view.getContext(), mymovies);
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

        mymovies = new ArrayList<Movie>();
        //get all movies that were added to mylist
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getInMylist() == true) {
                this.mymovies.add(array.get(i));
            }

        }
        imageAdapter = new ImageAdapter(view.getContext(), mymovies);
        imageGrid.setAdapter(imageAdapter);
        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Create intent
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", mymovies.get(position));
                startActivity(i);

            }
        });

        return view;
    }

    //load movies from sharedpreferences
    public ArrayList<Movie> getArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}
