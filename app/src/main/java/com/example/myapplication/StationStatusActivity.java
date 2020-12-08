package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class StationStatusActivity extends AppCompatActivity {
    private TextView stationName;
    private TextView stationNumber;
    private Button button;
    private String name, number;
    private List<Address> matches;
    private Address bestMatch;
    private String stationAddress, addressResult;
    private String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=Ho%2BO79EuOBas%2BL0GqQ26bphsXGYl%2BizgO3z1Iv9jgKyvAc%2B7MYhEh3ydiBzNL0YZiqliG35KYbdXPu8%2F9DWKSQ%3D%3D&numOfRows=10&pageNo=1&base_date=20201208&base_time=0600&";

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

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("data2.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    String s_name = "";
                    String x_point = "";
                    String y_point = "";

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        s_name = sheet.getCell(3, row).getContents();
                        // Log.d("test : ", s_name + " " + name);
                        if (s_name.equals(name)) {

                            x_point = sheet.getCell(4, row).getContents();
                            y_point = sheet.getCell(5, row).getContents();

                            Geocoder geoCoder = new Geocoder(StationStatusActivity.this);
                            matches = geoCoder.getFromLocation(Double.parseDouble(y_point), Double.parseDouble(x_point), 1);
                            bestMatch = (matches.isEmpty() ? null : matches.get(0));
                            stationAddress = bestMatch.getThoroughfare();

                            addressResult = stationAddress.substring(0,3);
                            Log.d("result : ", bestMatch + " " + bestMatch.getLocality());
                            // Toast.makeText(getApplicationContext(), "address : " + bestMatch.getThoroughfare(), Toast.LENGTH_LONG);

                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("weather.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;
                    String s_name = "";
                    String x_point2 = "";
                    String y_point2 = "";

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        s_name = sheet.getCell(0, row).getContents();
                        // Log.d("test : ", s_name + " " + name);
                        if (s_name.length() > 0 && s_name != null) {
                            Log.d("test", s_name + " " + addressResult);
                            if (s_name.contains(addressResult)) {
                                x_point2 = sheet.getCell(1, row).getContents();
                                y_point2 = sheet.getCell(2, row).getContents();
                                url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=Ho%2BO79EuOBas%2BL0GqQ26bphsXGYl%2BizgO3z1Iv9jgKyvAc%2B7MYhEh3ydiBzNL0YZiqliG35KYbdXPu8%2F9DWKSQ%3D%3D&numOfRows=10&pageNo=1&base_date=20201208&base_time=0600&nx=55&ny=127";
                                // url += "nx=" + x_point2 + "&ny=" + y_point2;

                                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                                Document doc = dBuilder.parse(url);
                                doc.getDocumentElement().normalize();
                                NodeList nList = doc.getElementsByTagName("item");

                                for (int temp = 0; temp < nList.getLength(); temp++) {
                                    Node nNode = nList.item(temp);
                                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element eElement = (Element) nNode;
                                        String result = getTagValue("category", eElement);
                                        if (result.equals("RN1")) {
                                            String rainValue = getTagValue("obsrValue", eElement);
                                            System.out.println("강수량  : " + rainValue);
                                            Log.d("result", rainValue);
                                            break;
                                        }
                                    }    // for end
                                }    // if end
                                Toast.makeText(getApplicationContext(), "address : " + addressResult, Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

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

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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