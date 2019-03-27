package com.example.biosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment{

    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b6df984eba8e46d43326f404be37161a&language=en-US&page=1";
    private ProgressDialog dialog;
    private ListView listView;
    private List<Movie> array = new ArrayList<Movie>();
    private MovieAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    Movie movie;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.overview_fragment, container, false);
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideDialog();
                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0; i< jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        movie = new Movie();
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


        listView = view.findViewById(R.id.list);
        adapter = new MovieAdapter(listView.getContext(), array);
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", array.get(position));
                startActivity(i);
            }
        });

        AppController.getmInstance().addToRequestQueue(request);
        return view;
    }



    public interface OnItemSelectedListener {
        void onItemSelected(Movie item);
    }

    private OnItemSelectedListener listener;

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (getActivity() instanceof OnItemSelectedListener) {
//            listener = (OnItemSelectedListener) getActivity();
//            AdapterView.OnItemClickListener mItemClickedHandler = new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView parent, View view, int position, long id) {
//                    listener.onItemSelected(movieArray[position]);
//                }
//            };
//            listView.setOnItemClickListener(mItemClickedHandler);
//        }
//
//
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) getActivity();
        }

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
