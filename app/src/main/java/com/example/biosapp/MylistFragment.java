package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
    private ArrayList<Bitmap> bitmapList;
    private ArrayList<String> urlList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.mylist_fragment, container, false);
//        Bundle bundle = getArguments();
//        List<Movie> movies = bundle.getParcelableArrayList("MOVIES");
        imageGrid = (GridView) view.findViewById(R.id.gridview);
        array = getArrayList("movies");

        this.urlList = new ArrayList<String>();
        try {
            for(int i = 0; i < array.size(); i++) {
                if(array.get(i).inMyList){
                    this.urlList.add(array.get(i).getPicture());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageGrid.setAdapter(new ImageAdapter(view.getContext(), this.urlList));

        return view;
    }

    private Bitmap urlImageToBitmap(String imageUrl) throws Exception {
        Bitmap result = null;
        URL url = new URL(imageUrl);
        if(url != null) {
            result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
        return result;
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
