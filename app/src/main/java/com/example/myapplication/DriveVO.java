package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

public class DriveVO {
    public int bookmark;
    public String stationName;
    public String stationNumber;
    public String stationTo;

    public DriveVO(int bookmark, String stationName, String stationNumber, String stationTo) {
        this.bookmark = bookmark;
        this.stationName = stationName;
        this.stationNumber = stationNumber;
        this.stationTo = stationTo;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }
}
