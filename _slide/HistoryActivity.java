package com.example.project_aqi;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AQIHistoryAdapter adapter;
    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);

        lineChart = findViewById(R.id.lineChart);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new AQIHistoryAdapter(
                MainActivity.historyList);

        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        setupGraph();
    }
    private void setupGraph(){
        ArrayList<Entry> entries =
                new ArrayList<>();

        for(int i = 0;
            i < MainActivity.historyList.size();
            i++){
            AQIHistory history =
                    MainActivity.historyList.get(i);
            entries.add(
                    new Entry(
                            i,
                            history.getAqi()
                    )
            );
        }
        LineDataSet dataSet =
                new LineDataSet(entries,
                        "AQI Level");
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f); // เพิ่มขนาดตัวเลขบนจุดกราฟ
        dataSet.setLineWidth(3f);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawCircleHole(false);

        LineData lineData =
                new LineData(dataSet);
        lineChart.setData(lineData);

        // ปรับแต่งแกน X
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextSize(12f);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);

        // ปรับแต่งแกน Y ฝั่งซ้าย
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextSize(12f);
        lineChart.getAxisLeft().setGridColor(Color.GRAY);

        // ปรับแต่งแกน Y ฝั่งขวา (ปิดไปเพื่อให้ดูง่ายขึ้น)
        lineChart.getAxisRight().setEnabled(false);

        // ปรับแต่งคำอธิบายกราฟ (Legend)
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextSize(14f);
        lineChart.getLegend().setForm(com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE);

        // ปรับแต่งคำอธิบายรายละเอียด
        lineChart.getDescription().setEnabled(false); // ปิด Description เล็กๆ มุมขวาเพื่อลดความรก

        // เพิ่ม Animation และการแสดงผล
        lineChart.setExtraOffsets(10, 10, 10, 10);
        lineChart.animateX(1000);
        lineChart.invalidate();
    }
}