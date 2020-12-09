package com.example.myapplication;

public class ReasonVO {
    public int repair;
    public String stationName;
    public String stationNumber;
    public String reason;

    public ReasonVO(int repair, String stationName, String stationNumber, String reason) {
        this.repair = repair;
        this.stationName = stationName;
        this.stationNumber = stationNumber;
        this.reason = reason;
    }

    public int getRepair() {
        return repair;
    }

    public void setRepair(int repair) {
        this.repair = repair;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
