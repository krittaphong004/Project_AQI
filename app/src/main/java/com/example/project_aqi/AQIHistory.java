package com.example.project_aqi;

public class AQIHistory {

    private int aqi;
    private String status;
    private String datetime;

    // Constructor
    public AQIHistory(int aqi,
                      String status,
                      String datetime) {

        this.aqi = aqi;
        this.status = status;
        this.datetime = datetime;
    }

    // GET AQI
    public int getAqi() {
        return aqi;
    }

    // GET STATUS
    public String getStatus() {
        return status;
    }

    // GET DATETIME
    public String getDatetime() {
        return datetime;
    }
}