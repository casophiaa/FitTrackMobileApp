package com.example.fittrackmobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private List<PlanItem> planItemList;

    public PlanAdapter(List<PlanItem> planItemList){
        this.planItemList = planItemList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_plan_item, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position){
        PlanItem planData = planItemList.get(position);
        holder.dayNum.setText(planData.obtainName());
        holder.dayDesc.setText(planData.obtainDesc());
        holder.imageview.setImageResource(planData.obtainImage());

    }

    @Override
    public int getItemCount() { return planItemList.size(); }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView dayNum, dayDesc;

        public PlanViewHolder(@NonNull View itemView){
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            dayNum = itemView.findViewById(R.id.dayNum);
            dayDesc = itemView.findViewById(R.id.dayDesc);
        }
    }
}