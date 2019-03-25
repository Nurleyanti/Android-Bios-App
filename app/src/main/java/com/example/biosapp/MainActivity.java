package com.example.biosapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        final MovieAdapter adapter = new MovieAdapter(this, R.layout.listview_activity);
//        adapter.add(new Movie("Corgi", R.drawable.corgi, 4, "Corgi Rex is de meest geliefde hond van het Britse koningshuis en ondanks dat hij zich niet altijd aan de regels houdt is hij lievelingetje van de Koningin."));
//        adapter.add(new Movie("Captain Marvel", R.drawable.marvel, 4, "Marvel Studios’ Captain Marve is een compleet nieuw avontuur dat zich afspeelt in 1990, een tot nu toe verborgen periode uit de geschiedenis van het Marvel Cinematic Universe."));
//        adapter.add(new Movie("Us", R.drawable.us, 3.5, "De innovatieve filmmaker en Oscarwinnaar Jordan Peele (Get Out) komt opnieuw met een originele en controversiële thriller: Us."));
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(Movie item)
    {
        DetailFragment fragment = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.detailFragment);

        fragment.setMovie(item);
    }
}
