package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ReportResultActivity extends AppCompatActivity {
    private ReasonAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_result);

        Intent intent = getIntent();
        listView = findViewById(R.id.report_listView);

        ArrayList<ReasonVO> reportDatas = new ArrayList<>();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String reason = intent.getStringExtra("reason");
        ReasonVO vo = new ReasonVO(R.drawable.repair, name, number, reason);
        reportDatas.add(vo);
        adapter = new ReasonAdapter(this, reportDatas);
        listView.setAdapter(adapter);

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
                Intent intent = new Intent(ReportResultActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_report:
                Intent intent2 = new Intent(ReportResultActivity.this, ReportResultActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}