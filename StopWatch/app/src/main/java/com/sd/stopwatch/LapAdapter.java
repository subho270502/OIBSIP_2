package com.sd.stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LapAdapter extends RecyclerView.Adapter<LapAdapter.LapViewHolder>{
    private List<String> laptimes;
    public LapAdapter(List<String> laptimes) {
        this.laptimes = laptimes;}
    static class LapViewHolder extends RecyclerView.ViewHolder {
        TextView lapTextView;

        LapViewHolder(View itemView){
            super(itemView);
            lapTextView = itemView.findViewById(R.id.laps_item);
        }
    }

    @NonNull
    @Override
    public LapViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laps_item,parent,false);
        return new LapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LapViewHolder holder,int position){
        holder.lapTextView.setText(laptimes.get(position));
    }

    @Override
    public int getItemCount(){
        return laptimes.size();
    }
}