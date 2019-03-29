package com.example.biosapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bitmap> bitmapList;
    private ArrayList<String> urlList;

    public ImageAdapter(Context context, ArrayList<Bitmap> bitmapList, ArrayList<String> urlList) {
        this.context = context;
        this.bitmapList = bitmapList;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return bitmapList.size();
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
        if (convertView == null) {
            for(int i = 0;i < urlList.size(); i++){
                new MainActivity.DownloadImageFromInternet(new ImageView(this.context))
                        .execute(urlList.get(i));
                imageView = new ImageView(this.context);
                imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageBitmap(this.bitmapList.get(position));
        return imageView;

    }
}
