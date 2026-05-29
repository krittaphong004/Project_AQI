package com.example.project_aqi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView txtAQI, txtStatus, txtDanger;
    MaterialButton btnRefresh, btnHistory;
    // เก็บประวัติ AQI
    public static ArrayList<AQIHistory> historyList =
            new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Notification Channel
        NotificationHelper.createChannel(this);

        // Android 13+
        requestNotificationPermission();

        // TEXTVIEW
        txtAQI = findViewById(R.id.txtAQI);
        txtStatus = findViewById(R.id.txtStatus);
        txtDanger = findViewById(R.id.txtDanger);

        // BUTTON
        btnRefresh = findViewById(R.id.btnRefresh);
        btnHistory = findViewById(R.id.btnHistory);

        // โหลด AQI ครั้งแรก
        updateAQI();

        // REFRESH BUTTON
        btnRefresh.setOnClickListener(v -> {

            updateAQI();

        });

        // HISTORY BUTTON
        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(MainActivity.this,
                            HistoryActivity.class);

            startActivity(intent);

        });
    }
    private void updateAQI(){

        Random random = new Random();

        int aqi = random.nextInt(301);

        String status;
        String danger;

        txtAQI.setText(String.valueOf(aqi));

        // GOOD
        if(aqi <= 50){
            status = "Good";
            danger = "อากาศดี ปลอดภัย";
            txtStatus.setText(status);
            txtStatus.setTextColor(
                    Color.parseColor("#00E676"));
            txtDanger.setText(danger);
        }
        // MODERATE
        else if(aqi <= 100){
            status = "Moderate";
            danger = "เริ่มมีผลต่อบางกลุ่ม";
            txtStatus.setText(status);
            txtStatus.setTextColor(
                    Color.parseColor("#FFD600"));
            txtDanger.setText(danger);
        }

        // UNHEALTHY
        else if(aqi <= 150){
            status = "Unhealthy";
            danger = "เริ่มมีผลต่อสุขภาพ";
            txtStatus.setText(status);
            txtStatus.setTextColor(
                    Color.parseColor("#FF5252"));
            txtDanger.setText(danger);
        }

        // DANGEROUS
        else{
            status = "Dangerous";
            danger = "อันตรายต่อสุขภาพ";
            txtStatus.setText(status);
            txtStatus.setTextColor(
                    Color.parseColor("#EA80FC"));
            txtDanger.setText(danger);
            // Notification
            NotificationHelper.showNotification(
                    this,
                    aqi
            );
        }

        // เวลาปัจจุบัน
        String currentTime = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        ).format(new Date());

        // บันทึกประวัติ
        historyList.add(
                new AQIHistory(
                        aqi,
                        status,
                        currentTime
                )
        );
    }

    // Android 13 Notification Permission
    private void requestNotificationPermission(){

        if(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.TIRAMISU){

            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS)

                    != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        100
                );
            }
        }
    }
}