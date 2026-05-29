package com.example.project_aqi;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                .inflate(R.layout.item_history,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        AQIHistory history = historyList.get(position);

        holder.txtAQI.setText(String.valueOf(history.getAqi()));
        holder.txtStatus.setText(history.getStatus());
        holder.txtDate.setText(history.getDatetime());

        switch (history.getStatus()) {

            case "Good":
                holder.txtStatus.setTextColor(
                        Color.parseColor("#00E676"));
                break;

            case "Moderate":
                holder.txtStatus.setTextColor(
                        Color.parseColor("#FFD600"));
                break;

            case "Unhealthy":
                holder.txtStatus.setTextColor(
                        Color.parseColor("#FF5252"));
                break;

            default:
                holder.txtStatus.setTextColor(
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

        TextView txtAQI, txtStatus, txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAQI = itemView.findViewById(R.id.txtAQI);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}