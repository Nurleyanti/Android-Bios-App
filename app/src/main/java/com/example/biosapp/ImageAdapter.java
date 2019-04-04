package com.example.biosapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> urlList;

    public ImageAdapter(Context context, ArrayList<Movie> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(context);
        }else{
            imageView = (ImageView) convertView;
        }

        Picasso.get().load(urlList.get(position).getPicture())
                .noFade().resize(200,300)
                .centerCrop()
                .into(imageView);

        return imageView;

    }
}
