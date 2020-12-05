package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StationStatusActivity extends AppCompatActivity {
    private TextView station_name;
    private TextView station_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_status);
        station_name = findViewById(R.id.station_name);
        station_number = findViewById(R.id.station_number);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        station_name.setText(name);
        station_number.setText(number);
    }
}