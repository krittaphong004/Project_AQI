package com.example.project_aqi;

public class AQIUtils {

    public static String getStatus(int aqi){

        if(aqi <= 50){
            return "Good";
        }
        else if(aqi <= 100){
            return "Moderate";
        }
        else if(aqi <= 150){
            return "Unhealthy";
        }
        else{
            return "Dangerous";
        }
    }

    public static String getDanger(int aqi){

        if(aqi <= 50){
            return "อากาศดี";
        }
        else if(aqi <= 100){
            return "เริ่มมีผลต่อบางกลุ่ม";
        }
        else if(aqi <= 150){
            return "เริ่มมีผลต่อสุขภาพ";
        }
        else{
            return "อันตรายต่อสุขภาพ";
        }
    }
}