package com.example.biosapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    //private ArrayList<Bitmap> bitmapList;
    private ArrayList<String> urlList;

    public ImageAdapter(Context context, ArrayList<String> urlList) {
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
        if (convertView == null) {

                imageView = new ImageView(this.context);
                imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);



        } else {
            imageView = (ImageView) convertView;
        }
        for(int i = 0;i < urlList.size(); i++) {
            new MainActivity.DownloadImageFromInternet(imageView)
                    .execute(urlList.get(i));
        }
        //imageView.setImageBitmap(this.bitmapList.get(position));
        return imageView;

    }
}
