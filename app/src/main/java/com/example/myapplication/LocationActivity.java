package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<HashMap<String, String>> simpleDatas = new ArrayList<>();
    private GoogleMap map;
    private TextView station_name;
    private TextView station_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        station_name = findViewById(R.id.location_station_name);
        station_number = findViewById(R.id.location_station_number);

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

        if(map != null){
            for(int i = 0; i < 13192; i++){
                String str1 = simpleDatas.get(i).get("xpoint");
                String str2 = simpleDatas.get(i).get("ypoint");
                double x_point = Double.valueOf(str1);
                double y_point = Double.valueOf(str2);

                LatLng latLng = new LatLng(y_point, x_point);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
                markerOptions.title("정류소명 :" + simpleDatas.get(i).get("name"));
                markerOptions.snippet("정류소번호 :" + simpleDatas.get(i).get("number"));
                map.addMarker(markerOptions);

                CameraPosition position = new CameraPosition.Builder()
                        .target(latLng).zoom(16f).build();
                map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            }
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                station_name.setText(marker.getTitle().toString());
                station_number.setText(marker.getSnippet().toString());

                return false;
            }
        });
    }
}