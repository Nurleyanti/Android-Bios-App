package com.example.biosapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MylistFragment extends Fragment{

    private static final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=b6df984eba8e46d43326f404be37161a&language=en-US&page=1";
    private ProgressDialog dialog;
    private ListView listView;
    private List<Movie> array = new ArrayList<Movie>();
    private MovieAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.mylist_fragment, container, false);
        //listView = view.findViewById(R.id.list);

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
