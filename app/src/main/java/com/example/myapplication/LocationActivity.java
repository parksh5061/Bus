package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {
    private ArrayList<HashMap<String, String>> simpleDatas = new ArrayList<>();
    private ArrayList<HashMap<String, String>> listDatas = new ArrayList<>();
    private GoogleMap map;
    private double y_point, x_point;
    private Location currentLocation;
    private MarkerOptions markerOptions, locMarkerOptions;
    private LatLng latLng;
    private ListView listView;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // station_name = findViewById(R.id.location_station_name);
        // station_number = findViewById(R.id.location_station_number);
        listView = findViewById(R.id.bus_status_listview);
        listView.setOnItemClickListener(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    // Log.d("test2", "print : "  + rowTotal);
                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        HashMap<String, String> map = new HashMap<>();
                        String content = sheet.getCell(1, row).getContents();
                        String content1 = sheet.getCell(2, row).getContents();
                        String content2 = sheet.getCell(3, row).getContents();
                        String content3 = sheet.getCell(4, row).getContents();

                        map.put("number", content);
                        map.put("name", content1);
                        map.put("xpoint", content2);
                        map.put("ypoint", content3);

                        simpleDatas.add(map);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        // SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        // mapFragment.getMapAsync(this);
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(LocationActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double distance = 0;
        LatLng loc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        Location user = new Location("user");
        user.setLatitude(loc.latitude); // y
        user.setLongitude(loc.longitude); // x

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        circleOptions.radius(1000);
        circleOptions.strokeWidth(1);
        circleOptions.fillColor(Color.parseColor("#500084d3"));
        map.addCircle(circleOptions);

        if (map != null) {
            for (int i = 0; i < 13931; i++) {
                if (!simpleDatas.get(i).get("xpoint").isEmpty()) {
                    String str1 = simpleDatas.get(i).get("xpoint"); // y
                    String str2 = simpleDatas.get(i).get("ypoint"); // x
                    x_point = Double.valueOf(str1);
                    y_point = Double.valueOf(str2);
                    latLng = new LatLng(y_point, x_point);
                    Location station = new Location("station");
                    station.setLongitude(latLng.longitude);
                    station.setLatitude(latLng.latitude);
                    distance = user.distanceTo(station);

                    if (distance < circleOptions.getRadius()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        String name =  simpleDatas.get(i).get("name");
                        String number = simpleDatas.get(i).get("number");

                        hashMap.put("name", name);
                        hashMap.put("number", number);
                        listDatas.add(hashMap);

                        markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
                        markerOptions.title("정류소명 :" + name);
                        markerOptions.snippet("정류소번호 :" + number);
                        map.addMarker(markerOptions);
                    }
                }
            }
            locMarkerOptions = new MarkerOptions().position(loc).title("");
            map.animateCamera(CameraUpdateFactory.newLatLng(loc));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
            map.addMarker(locMarkerOptions);

            SimpleAdapter adapter = new SimpleAdapter(this,
                    listDatas, android.R.layout.simple_list_item_2,
                    new String[]{"name","number"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            listView.setAdapter(adapter);
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // station_name.setText(marker.getTitle());
                // station_number.setText(marker.getSnippet());

                return false;
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = listDatas.get(position).get("name");
        String number = listDatas.get(position).get("number");

        Intent intent = new Intent(LocationActivity.this, StationStatusActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("number", number);
        startActivity(intent);
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
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_report:
                Intent intent2 = new Intent(LocationActivity.this, ReportResultActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_report:
                Intent intent2 = new Intent(LocationActivity.this, ReportResultActivity.class);
                startActivity(intent2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}