package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<BusVO> originalArray, tempArray;
    CustomFilter cs;
    boolean image_check;

    public BusAdapter(Context context, ArrayList<BusVO> originalArray) {
        this.context = context;
        this.originalArray = originalArray;
        this.tempArray = originalArray;
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
        View row = inflater.inflate(R.layout.custom_item2, null);

        ImageView bookmarkImageView = (ImageView)row.findViewById(R.id.bus_status_bookmark);
        TextView busNameTextView = (TextView)row.findViewById(R.id.bus_status_name);
        TextView busToTextView = (TextView)row.findViewById(R.id.bus_status_number);

        bookmarkImageView.setImageResource(originalArray.get(position).getBookmark());
        busNameTextView.setText(originalArray.get(position).getBusName());
        busToTextView.setText(originalArray.get(position).getBusTo());

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

    @Override
    public Filter getFilter() {
        if(cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<BusVO> filters = new ArrayList<>();

                for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).getBusName().contains(constraint)) {
                        BusVO busVO = new BusVO(tempArray.get(i).getBookmark(), tempArray.get(i).getBusName(), tempArray.get(i).getBusTo());
                        filters.add(busVO);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            }
            else{
                results.count = tempArray.size();
                results.values = tempArray;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            originalArray = (ArrayList<BusVO>) results.values;
            notifyDataSetChanged();
        }
    }
}
