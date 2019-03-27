package com.example.biosapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class OverviewFragment extends Fragment{

    private ListView listView;
    private Movie[] movieArray;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Movie corgi = new Movie("Corgi", R.drawable.corgi, 4, "Corgi Rex is de meest geliefde hond van het Britse koningshuis en ondanks dat hij zich niet altijd aan de regels houdt is hij lievelingetje van de Koningin.");
//        Movie marvel = new Movie("Captain Marvel", R.drawable.marvel, 4, "Marvel Studios’ Captain Marve is een compleet nieuw avontuur dat zich afspeelt in 1990, een tot nu toe verborgen periode uit de geschiedenis van het Marvel Cinematic Universe.");
//        Movie us = new Movie("Us", R.drawable.us, 3.5f, "De innovatieve filmmaker en Oscarwinnaar Jordan Peele (Get Out) komt opnieuw met een originele en controversiële thriller: Us.");
//
//        movieArray = new Movie[]{corgi, marvel, us};

        View view = inflater.inflate(R.layout.overview_fragment, container, false);
        listView = view.findViewById(R.id.list);
        //api = new ApiConnector(getContext(), listView);
        //final MovieAdapter adapter = new MovieAdapter(listView.getContext(), android.R.layout.simple_list_item_1, movieArray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movie clickedItem = (Movie) listView.getItemAtPosition(position);
                listener.onItemSelected(clickedItem);
            }
        });
        //listView.setAdapter(adapter);
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

}
