package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusStatusAdapter extends BaseAdapter  {
    Context context;
    ArrayList<BusStatusVO> originalArray;
    boolean image_check = false;
    public BusStatusAdapter(Context context, ArrayList<BusStatusVO> originalArray) {
        this.context = context;
        this.originalArray = originalArray;
    }

    @Override
    public int getCount() {
        return originalArray.size();
    }

    @Override
    public Object getItem(int position) {
        return originalArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_item3, null);

        ImageView bookmarkImageView = (ImageView)row.findViewById(R.id.repair_imageView);
        TextView stationNameTextView = (TextView)row.findViewById(R.id.bus_report_name);
        TextView stationNumberTextView = (TextView)row.findViewById(R.id.bus_report_number);

        bookmarkImageView.setImageResource(originalArray.get(position).getBookmark());
        stationNameTextView.setText(originalArray.get(position).getStation_name());
        stationNumberTextView.setText(originalArray.get(position).getStation_number());

        bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image_check == false){
                    bookmarkImageView.setImageResource(R.drawable.heart2);
                    image_check = true;
                } else if(image_check == true){
                    bookmarkImageView.setImageResource(R.drawable.heart1);
                    image_check = false;
                }

            }
        });

        return row;
    }
}
