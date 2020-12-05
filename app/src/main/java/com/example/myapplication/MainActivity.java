package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SearchView searchView;
    private TextView station_TextView, text1, text2;
    private Button submit_Button;
    private String station_name, station_number;
    private Filter filter;
    private ImageView location_imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<HashMap<String, String>> simpleDatas = new ArrayList<>();

        location_imageView = findViewById(R.id.location_imageView);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        station_TextView = findViewById(R.id.station_name_textView);
        submit_Button = findViewById(R.id.button);
        searchView.setQueryHint("정류장을 입력해주세요.");

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        HashMap<String, String> map = new HashMap<>();
                        String content = sheet.getCell(1, row).getContents();
                        String content1 = sheet.getCell(2, row).getContents();
                        String content2 = sheet.getCell(3,row).getContents();
                        String content3 = sheet.getCell(4,row).getContents();

                        map.put("number", content);
                        map.put("name", content1);
                        map.put("xpoint",content2);
                        map.put("ypoint",content3);

                        simpleDatas.add(map);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(this, simpleDatas,
                            android.R.layout.simple_list_item_2,
                            new String[]{"number", "name"},
                            new int[]{android.R.id.text1, android.R.id.text2});
                    listView.setAdapter(adapter);
                    filter = adapter.getFilter();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    filter.filter(null);
                }
                else{
                    filter.filter(newText);
                }
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text1 = view.findViewById(android.R.id.text1);
                text2 = view.findViewById(android.R.id.text2);

                station_number = text1.getText().toString();
                station_name = text2.getText().toString();
                station_TextView.setText(station_name + " (" + station_number +")");
                searchView.clearFocus();
            }
        });

        submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (station_TextView.getText().toString() != null) {
                    Intent intent = new Intent(MainActivity.this, StationStatusActivity.class);
                    intent.putExtra("number", station_number);
                    intent.putExtra("name", station_name);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "정류장을 선택하지 않았습니다.", Toast.LENGTH_SHORT);
                }
            }
        });

        location_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }
}