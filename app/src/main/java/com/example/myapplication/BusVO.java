package com.example.myapplication;

import android.view.View;

public class BusVO {
    public int bookmark;
    public String busName;
    public String busTo;

    public BusVO(int bookmark, String busName, String busTo) {
        this.bookmark = bookmark;
        this.busName = busName;
        this.busTo = busTo;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusTo() {
        return busTo;
    }

    public void setBusTo(String busTo) {
        this.busTo = busTo;
    }
}
