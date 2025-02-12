package com.emr.javacalculator.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimInfoAdapter extends RecyclerView.Adapter<SimInfoAdapter.SimInfoViewHolder> {

    private List<String> simInfoList;

    public SimInfoAdapter(List<String> simInfoList) {
        this.simInfoList = simInfoList;
    }

    @NonNull
    @Override
    public SimInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SimInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimInfoViewHolder holder, int position) {
        holder.textView.setText(simInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return simInfoList.size();
    }

    public static class SimInfoViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SimInfoViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}