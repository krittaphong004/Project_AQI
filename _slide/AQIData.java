package com.example.project_aqi;

public class AQIData {

    private int aqi;
    private String status;
    private String danger;
    public AQIData(int aqi, String status, String danger) {
        this.aqi = aqi;
        this.status = status;
        this.danger = danger;
    }
    public int getAqi() {
        return aqi;
    }
    public String getStatus() {
        return status;
    }
    public String getDanger() {
        return danger;
    }
}