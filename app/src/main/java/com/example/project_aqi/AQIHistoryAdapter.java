package com.example.project_aqi;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_aqi.AQIHistory;

import java.util.List;

public class AQIHistoryAdapter
        extends RecyclerView.Adapter<AQIHistoryAdapter.ViewHolder> {

    List<AQIHistory> historyList;

    public AQIHistoryAdapter(List<AQIHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        AQIHistory history = historyList.get(position);

        holder.text1.setText(
                "AQI: " + history.getAqi()
                        + " - "
                        + history.getStatus());

        holder.text2.setText(
                history.getDatetime());

        switch (history.getStatus()) {

            case "Good":
                holder.text1.setTextColor(
                        Color.parseColor("#00E676"));
                break;

            case "Moderate":
                holder.text1.setTextColor(
                        Color.parseColor("#FFD600"));
                break;

            case "Unhealthy":
                holder.text1.setTextColor(
                        Color.parseColor("#FF5252"));
                break;

            default:
                holder.text1.setTextColor(
                        Color.parseColor("#EA80FC"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView text1, text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(
                    android.R.id.text1);

            text2 = itemView.findViewById(
                    android.R.id.text2);
        }
    }
}