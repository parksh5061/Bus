package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class BusStatusActivity extends AppCompatActivity {
    private TextView bus_name, destination_name;
    private ListView listView;
    private BusStatusAdapter adapter;
    private String station_name, station_number;
    private int bookmark_res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_status);
        ArrayList<BusStatusVO> busStatusDatas = new ArrayList<>();
        listView = findViewById(R.id.bus_status_listview);
        bus_name = findViewById(R.id.station_name);
        destination_name = findViewById(R.id.station_number);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("dest");

        bus_name.setText(name);
        destination_name.setText(number);

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data3.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        String content = sheet.getCell(0, row).getContents();
                        content += "번";
                        if(content.equals(name)){
                            String content1 = sheet.getCell(1, row).getContents();
                            String content2 = sheet.getCell(2, row).getContents();

                            BusStatusVO vo = new BusStatusVO(content2, content1, R.drawable.heart1);

                            vo.station_name = content2;
                            vo.station_number = content1;
                            vo.bookmark = R.drawable.heart1;

                            busStatusDatas.add(vo);
                        }
                    }
                    adapter = new BusStatusAdapter(this, busStatusDatas);
                    listView.setAdapter(adapter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                station_name = ((BusStatusVO) adapter.getItem(position)).station_name;
                station_number = ((BusStatusVO) adapter.getItem(position)).station_number;

                Toast.makeText(getApplicationContext(), "station_name : " + station_name + " station_number : " + station_number, Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(BusStatusActivity.this, StationStatusActivity.class);
                // intent.putExtra("name", station_name);
                // intent.putExtra("number", station_number);
                // startActivity(intent);

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
                Intent intent = new Intent(BusStatusActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_report:
                Intent intent2 = new Intent(BusStatusActivity.this, ReportResultActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}