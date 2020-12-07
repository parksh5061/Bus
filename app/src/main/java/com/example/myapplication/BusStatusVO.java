package com.example.myapplication;

public class BusStatusVO {
    public String station_name;
    public String station_number;
    public int bookmark;

    public BusStatusVO(String station_name, String station_number, int bookmark) {
        this.station_name = station_name;
        this.station_number = station_number;
        this.bookmark = bookmark;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_number() {
        return station_number;
    }

    public void setStation_number(String station_number) {
        this.station_number = station_number;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }
}
