package com.example.project_aqi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView txtAQI, txtStatus, txtDanger, txtRecommendation;
    MaterialButton btnHistory;
    DatabaseReference database;

    // เก็บประวัติ AQI
    public static ArrayList<AQIHistory> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Setup - ระบุ URL ของฐานข้อมูลเพื่อให้เชื่อมต่อกับโซนเอเชียได้ถูกต้อง
        database = FirebaseDatabase.getInstance("https://checkaqi-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("current_aqi");

        // Notification Channel
        NotificationHelper.createChannel(this);

        // Android 13+ Notification Permission
        requestNotificationPermission();

        // TEXTVIEW
        txtAQI = findViewById(R.id.txtAQI);
        txtStatus = findViewById(R.id.txtStatus);
        txtDanger = findViewById(R.id.txtDanger);
        txtRecommendation = findViewById(R.id.txtRecommendation);

        // BUTTON
        btnHistory = findViewById(R.id.btnHistory);

        // ฟังข้อมูลจาก Firebase แบบ Realtime
        listenToFirebase();

        // HISTORY BUTTON
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void listenToFirebase() {
        Log.d("FirebaseDB", "Starting to listen to current_aqi...");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        Integer aqiValue = snapshot.getValue(Integer.class);
                        if (aqiValue != null) {
                            Log.d("FirebaseDB", "Data received: " + aqiValue);
                            updateAQIWithData(aqiValue);
                        }
                    } catch (Exception e) {
                        String rawValue = String.valueOf(snapshot.getValue());
                        Log.w("FirebaseDB", "Data format issue, raw value: " + rawValue);
                        try {
                            int aqiValue = Integer.parseInt(rawValue);
                            updateAQIWithData(aqiValue);
                        } catch (NumberFormatException nfe) {
                            Log.e("FirebaseDB", "Cannot parse value to int", nfe);
                        }
                    }
                } else {
                    Log.d("FirebaseDB", "No data exists at current_aqi path");
                    // สำหรับทดสอบ: ถ้าไม่มีข้อมูล ให้สร้างค่าเริ่มต้นเป็น 0
                    database.setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FirebaseDB", "Database error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAQIWithData(int aqi) {
        String status = AQIUtils.getStatus(aqi);
        String danger = AQIUtils.getDanger(aqi);
        String recommendation = AQIUtils.getRecommendation(aqi);

        txtAQI.setText(String.valueOf(aqi));
        txtStatus.setText(status);
        txtDanger.setText(danger);
        txtRecommendation.setText(recommendation);

        // เปลี่ยนสีตาม AQI
        if (aqi <= 50) {
            txtStatus.setTextColor(Color.parseColor("#00E676")); // Good
        } else if (aqi <= 100) {
            txtStatus.setTextColor(Color.parseColor("#FFD600")); // Moderate
        } else if (aqi <= 150) {
            txtStatus.setTextColor(Color.parseColor("#FF5252")); // Unhealthy
        } else {
            txtStatus.setTextColor(Color.parseColor("#EA80FC")); // Dangerous
            NotificationHelper.showNotification(this, aqi);
        }

        // บันทึกเวลาปัจจุบัน
        String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        
        // บันทึกประวัติ
        historyList.add(new AQIHistory(aqi, status, currentTime));
    }

    // Android 13 Notification Permission
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }
    }
}
