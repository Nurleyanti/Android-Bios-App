package com.example.biosapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import com.example.biosapp.ExpandableHeightGridView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Button btnSelectImage;
    private ImageView image;
    private TextView desc;
    private static final int SELECT_IMAGE = 1000;
    private static final int CAMERA = 2000;
    private ExpandableHeightGridView gridView;
    private List<Movie> movies;
    private ArrayList<Movie> seenMovies;
    private GridViewAdapter gridAdapter;
    private View view;
    private CharSequence options[];
    public ContentValues values;
    public Bitmap bitmap;

    //call when user presses back to update view
    @Override
    public void onResume() {
        super.onResume();
        movies = loadMovies();
        seenMovies = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getSeen()) {
                seenMovies.add(movies.get(i));
            }
        }
        if (seenMovies.size() < 4) {
            desc.setText("Movie Noob");
        } else if (seenMovies.size() > 3 && seenMovies.size() < 10) {
            desc.setText("Movie Novice");
        } else if (seenMovies.size() > 10) {
            desc.setText("Movie Master");
        }
        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
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
        gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
        desc = view.findViewById(R.id.profile_description);
        values = new ContentValues();
        movies = loadMovies();
        seenMovies = new ArrayList<Movie>();

        //add seen movies to seenMovies array
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getSeen()) {
                seenMovies.add(movies.get(i));
            }
        }

        gridAdapter = new GridViewAdapter(getContext(), seenMovies);
        gridView.setAdapter(gridAdapter);
        if (loadData() != null) {
            byte[] imageAsBytes = Base64.decode(loadData().getBytes(), Base64.DEFAULT);
            image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } else {
            image.setImageResource(R.drawable.ic_launcher_foreground);
        }
        btnSelectImage.setOnClickListener(this);
        //pass movie object with intent to next activity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Create intent
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("MOVIE!", seenMovies.get(position));
                startActivity(i);

            }
        });
        if (seenMovies.size() < 4) {
            desc.setText("Movie Noob");
        } else if (seenMovies.size() > 3 && seenMovies.size() < 10) {
            desc.setText("Movie Novice");
        } else if (seenMovies.size() > 10) {
            desc.setText("Movie Master");
        }
        return view;
    }

    //load movies from array
    public ArrayList<Movie> loadMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = prefs.getString("movies", null);
        Type type = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SELECT_IMAGE || requestCode == CAMERA) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
                    if (showRationale) {
                        //show your own message
                    } else {
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
        //user will be redirected to settings on phone
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
        if (context == null) {
            return;
        }
        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), SELECT_IMAGE);
    }


    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_IMAGE) {
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            image.post(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bitmapLocal = null;
                                    try {
                                        bitmapLocal = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
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
                    if (requestCode == CAMERA) {
                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                                image.setImageBitmap(bitmap1);
                                saveData(bitmap1);
                                bitmap = bitmap1;
                            }

                        });
                    }
                }
            }
        }).start();
        super.onActivityResult(requestCode, resultCode, data);
    }

    //save bitmap to SharedPreferences
    public void saveData(Bitmap bm) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
        editor.putString("image", encoded);
        editor.commit();     // This line is IMPORTANT !!
    }

    //get bitmap from Sharedpreferences
    public String loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String imageUri = prefs.getString("image", null);
        return imageUri;
    }


    @Override
    public void onClick(View v) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, SELECT_IMAGE);
        } else {
            showDialog();
        }

    }

    ;

    public void showDialog() {
        options = new CharSequence[]{"Gallery", "Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle("Select your option:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on options[which]
                if (options[which] == "Gallery") {
                    openImageChooser();
                }
                if (options[which] == "Camera") {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA);
                    } else {
                        openCamera();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //the user clicked on Cancel
                dialog.cancel();
            }
        });
        builder.show();
    }


}

