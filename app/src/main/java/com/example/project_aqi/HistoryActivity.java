package com.example.project_aqi;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AQIHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        adapter = new AQIHistoryAdapter(
                MainActivity.historyList);

        recyclerView.setAdapter(adapter);
    }
}