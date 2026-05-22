package com.example.project_aqi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_aqi.AQIHistory;
import com.example.project_aqi.R;
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

        // TEXTVIEW
        txtAQI = findViewById(R.id.txtAQI);
        txtStatus = findViewById(R.id.txtStatus);
        txtDanger = findViewById(R.id.txtDanger);

        // BUTTON
        btnRefresh = findViewById(R.id.btnRefresh);
        btnHistory = findViewById(R.id.btnHistory);

        // โหลดค่า AQI ครั้งแรก
        updateAQI();

        // REFRESH BUTTON
        btnRefresh.setOnClickListener(v -> {

            updateAQI();

        });

        // HISTORY BUTTON
        btnHistory.setOnClickListener(v -> {

            Intent intent =
                    new Intent(MainActivity.this,
                            com.example.project_aqi.HistoryActivity.class);

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
}