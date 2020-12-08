package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class tap extends AppCompatActivity {

    private Button btn_loginpage, btn_complaint, btn_info, btn_settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        btn_loginpage = findViewById(R.id.btn_loginpage);
        btn_complaint = findViewById(R.id.btn_complaint);
        btn_info = findViewById(R.id.btn_info);
        btn_settings = findViewById(R.id.btn_settings);


        btn_loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapplication.tap.this, com.example.myapplication.LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapplication.tap.this, com.example.myapplication.ComplaintActivity.class);
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapplication.tap.this, com.example.myapplication.SettingActivity.class);
                startActivity(intent);
            }
        });


    }
}