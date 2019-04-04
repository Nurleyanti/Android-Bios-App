package com.example.biosapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ExpandableHeightGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    Button btnSelectImage;
    ImageView image;
    TextView name;
    TextView desc;

    static final int SELECT_IMAGE = 1000;
    Bitmap bitmap;
    private ExpandableHeightGridView gridView;
    List<Movie> movies;
    ArrayList<Movie>seenMovies;
    GridViewAdapter gridAdapter;
    View view;

    @Override
    public void onResume() {
        super.onResume();
        movies = loadMovies();
        seenMovies = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getSeen()){
                seenMovies.add(movies.get(i));
            }

        }
        if(seenMovies.size() <4){
            desc.setText("Movie Noob");
        }else if(seenMovies.size() > 3 && seenMovies.size() < 10){
            desc.setText("Movie Novice");
        }else if(seenMovies.size() > 10){
            desc.setText("Movie Master");
        }
        gridAdapter = new GridViewAdapter(view.getContext(), seenMovies);
        gridAdapter.notifyDataSetChanged();
        gridView.setAdapter(gridAdapter);
        gridView.setExpanded(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        btnSelectImage = view.findViewById(R.id.profile_button);
        image = view.findViewById(R.id.profile_image);
        gridView = view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
        desc = view.findViewById(R.id.profile_description);
        movies = loadMovies();

        seenMovies = new ArrayList<Movie>();

        for(int i = 0; i < movies.size(); i++) {
            if(movies.get(i).getSeen()){
                seenMovies.add(movies.get(i));
            }

        }
        gridAdapter = new GridViewAdapter(getContext(), seenMovies);
        gridView.setAdapter(gridAdapter);
        if(loadData() != null){
            byte[] imageAsBytes = Base64.decode(loadData().getBytes(), Base64.DEFAULT);
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            //image.setImageBitmap(bitmap);
        }else{
            image.setImageResource(R.drawable.ic_launcher_foreground);
        }
        btnSelectImage.setOnClickListener(this);

        handlePermission();
        name = view.findViewById(R.id.profile_name);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Create intent
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", seenMovies.get(position));
                startActivity(i);

            }
        });
        if(seenMovies.size() <4){
            desc.setText("Movie Noob");
        }else if(seenMovies.size() > 3 && seenMovies.size() < 10){
            desc.setText("Movie Novice");
        }else if(seenMovies.size() > 10){
            desc.setText("Movie Master");
        }
        return view;
    }

    public ArrayList<Movie> loadMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString("movies", null);
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }


    void handlePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Dont need for versions less than M
            createPermissions();
            return;
        }
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_IMAGE);
        }
    }

    public void createPermissions(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        SELECT_IMAGE);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("NURLEYANTI", "hoi");
        switch(requestCode){

            case SELECT_IMAGE:
                Log.d("NURLEYANTI", "hoi2");
                for(int i=0; i< permissions.length; i++){
                    Log.d("NURLEYANTI", "hoi3");
                    String permission = permissions[i];
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        Log.d("NURLEYANTI", "hoi4");
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
                        if(showRationale){
                            Log.d("NURLEYANTI", "hoi5");
                            //show your own message
                        }else{
                            Log.d("NURLEYANTI", "hoi6");
                            //User tapped never ask again
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs access to the storage");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DON'T ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                openAppSettings(getActivity());
            }
        });
        alertDialog.show();
    }

    private void openAppSettings(final Activity context) {
        if(context == null){
            return;
        }
//        Intent i = new Intent();
//        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        i.addCategory(Intent.CATEGORY_DEFAULT);
//        i.setData(Uri.parse("package" + getContext().getPackageName()));
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        //startActivity(i);
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
    }

    void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(resultCode == RESULT_OK){
                    if(requestCode == SELECT_IMAGE){
                        final Uri selectedImageUri = data.getData();
                        if(null != selectedImageUri){
                            image.post(new Runnable() {
                                @Override
                                public void run() {
                                    //image.setImageURI(selectedImageUri);

                                    Bitmap bitmapLocal = null;
                                    try {
                                        bitmapLocal = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    saveData(bitmapLocal);
                                    bitmap = bitmapLocal;
                                    image.setImageBitmap(bitmapLocal);
                                }
                            });
                        }
                    }
                }
            }
        }).start();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveData(Bitmap bm){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
        editor.putString("image", encoded);
        //editor.putString("image", uri.toString());
        editor.commit();     // This line is IMPORTANT !!
    }

    public String loadData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String imageUri = prefs.getString("image", null);
        return imageUri;
    }


    @Override
    public void onClick(View v) {
        try {
            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_IMAGE);
            } else {
                openImageChooser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };



}

