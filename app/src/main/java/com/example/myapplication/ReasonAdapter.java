package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReasonAdapter extends BaseAdapter {
    Context context;
    ArrayList<ReasonVO> originalArray;
    boolean image_check = false;
    public ReasonAdapter(Context context, ArrayList<ReasonVO> originalArray) {
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
        View row = inflater.inflate(R.layout.custom_item4, null);

        ImageView repairImageView = (ImageView)row.findViewById(R.id.repair_imageView);
        TextView reportNameTextView= (TextView)row.findViewById(R.id.bus_report_name);
        TextView reportNumberTextView = (TextView)row.findViewById(R.id.bus_report_number);
        TextView reasonTextView = row.findViewById(R.id.reason_textView);

        repairImageView.setImageResource(originalArray.get(position).repair);
        reportNameTextView.setText(originalArray.get(position).stationName);
        reportNumberTextView.setText(originalArray.get(position).stationNumber);
        reasonTextView.setText(originalArray.get(position).reason);

        return row;
    }
}
