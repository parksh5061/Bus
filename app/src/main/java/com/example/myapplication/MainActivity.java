package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private ListView listView, listView2;
    private String station_name, station_number, station_to, bus_name, bus_to;
    private ImageView location_imageView, bookmark1;
    private LinearLayout custom_li;
    private DriveAdapter adapter;
    private BusAdapter busAdapter;
    private EditText editText;
    private TabHost tabHost;
    private TextView location_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<DriveVO> customDatas = new ArrayList<>();
        ArrayList<BusVO> busDatas = new ArrayList<>();
        location_imageView = findViewById(R.id.location_imageView);
        listView = findViewById(R.id.bus_status_listview);
        listView2 = findViewById(R.id.listView2);
        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(this);
        custom_li = findViewById(R.id.custom_li);
        bookmark1= findViewById(R.id.bus_status_bookmark);
        tabHost = findViewById(R.id.tabHost1);
        location_textView = findViewById(R.id.location_textView);

        TabHost.TabSpec ts = tabHost.newTabSpec("Tab Spec");
        tabHost.setup();

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
                        String content = sheet.getCell(1, row).getContents();
                        String content1 = sheet.getCell(2, row).getContents();
                        String content2 = sheet.getCell(3, row).getContents();
                        // String content3 = sheet.getCell(4, row).getContents();

                        DriveVO vo = new DriveVO(R.drawable.heart1, content1, content2);
                        vo.bookmark = R.drawable.heart1;
                        vo.stationName = content1;
                        vo.stationNumber = content;
                        // vo.stationTo = content3;

                        customDatas.add(vo);
                    }
                    adapter = new DriveAdapter(this, customDatas);
                    listView.setAdapter(adapter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data2.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    String start_station = "";
                    String end_station = "";
                    ArrayList<String> bus_number= new ArrayList<String>();
                    ArrayList<String> num= new ArrayList<String>();
                    ArrayList<String> name= new ArrayList<String>();

                    bus_number.add("0");
                    num.add("1");
                    name.add("0");
                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        bus_number.add(sheet.getCell(0, row).getContents());
                        num.add(sheet.getCell(1, row).getContents());
                        name.add(sheet.getCell(3, row).getContents());

                        if(num.get(row).equals("1") && !num.get(row-1).equals("1")){
                            end_station = start_station + " <-> " + name.get(row-1);
                            BusVO vo = new BusVO(R.drawable.heart1, bus_number.get(row-1), end_station);
                            vo.bookmark = R.drawable.heart1;
                            vo.busName = bus_number.get(row-1) + "번";
                            vo.busTo = end_station;
                            busDatas.add(vo);
                        }

                        if (num.get(row).equals("1")) {
                            start_station = name.get(row);
                        }
                    }
                }

                busAdapter = new BusAdapter(this, busDatas);
                listView2.setAdapter(busAdapter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        TabHost.TabSpec ts1 = tabHost.newTabSpec("버스");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("버스");
        tabHost.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("정류장");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("정류장");
        tabHost.addTab(ts2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                station_name = ((DriveVO) adapter.getItem(position)).stationName;
                station_number = ((DriveVO) adapter.getItem(position)).stationNumber;

                Intent intent = new Intent(MainActivity.this, StationStatusActivity.class);
                intent.putExtra("name", station_name);
                intent.putExtra("number", station_number);
                startActivity(intent);

            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bus_name = ((BusVO) busAdapter.getItem(position)).busName;
                bus_to = ((BusVO) busAdapter.getItem(position)).busTo;

                Intent intent = new Intent(MainActivity.this, BusStatusActivity.class);
                intent.putExtra("name", bus_name);
                intent.putExtra("dest", bus_to);

                startActivity(intent);
            }
        });

        location_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

        location_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.adapter.getFilter().filter(s);
        this.busAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}