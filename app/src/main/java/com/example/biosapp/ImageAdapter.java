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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    //private ArrayList<Bitmap> bitmapList;
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
//        ImageView imageView;
//        if (convertView == null) {
//
//                imageView = new ImageView(this.context);
//                imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//
//
//        } else {
//            imageView = (ImageView) convertView;
//        }
//        for(int i = 0;i < urlList.size(); i++) {
//            new MainActivity.DownloadImageFromInternet(imageView)
//                    .execute(urlList.get(i));
//        }
//        ListView.LayoutParams params = new ListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        ListView.LayoutParams lp = new ListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
        //lp.gravity = Gravity.RIGHT;


        TextView textView;
        Button button;
        if (convertView == null){
            textView = new TextView(this.context);
            //textView.setLayoutParams(params);
            textView.setTextSize(15);

            button = new Button(this.context);
            button.setWidth(30);
            button.setHeight(30);
            //button.setla("right");


        }else{
            textView = (TextView) convertView;
            button = (Button) convertView;
        }
        //imageView.setImageBitmap(this.bitmapList.get(position));
            if(urlList.get(position).getInMylist() == true){
                textView.setText(urlList.get(position).title);
                button.setBackgroundResource(R.drawable.ic_remove_black_24dp);
            }

        return textView;

    }
}
