package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StationStatusActivity extends AppCompatActivity {
    private TextView stationName;
    private TextView stationNumber;
    private Button button;
    private String name, number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_status);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");

        stationName = findViewById(R.id.station_name);
        stationNumber = findViewById(R.id.station_number);
        button = findViewById(R.id.button);

        stationName.setText(name);
        stationNumber.setText(number);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationStatusActivity.this, StationReportActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent(StationStatusActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_report:
                Intent intent2 = new Intent(StationStatusActivity.this, ReportResultActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}