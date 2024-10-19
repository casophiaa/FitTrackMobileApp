package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    ArrayList<com.example.fittrackmobileapp.ChallengeData> challengeDataList;
    Context context;

    public ChallengeAdapter(ArrayList<com.example.fittrackmobileapp.ChallengeData> challengeDataList, Context context) {
        this.challengeDataList = challengeDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.challenge_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.fittrackmobileapp.ChallengeData challengeData = challengeDataList.get(position);
        holder.textDayNum.setText(challengeData.getDayNum());
        holder.textDayDesc.setText(challengeData.getDayDesc());
        holder.exerciseMoveImage.setImageResource(challengeData.getExerciseMove());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, challengeData.getDayNum(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, ChallengeActivity.class);
            i.putExtra("image", challengeData.getExerciseMove());
            i.putExtra("dayNum", challengeData.getDayNum());
            i.putExtra("dayDesc", challengeData.getDayDesc());
            i.putExtra("exerciseDets", challengeData.getExerciseDetails());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return challengeDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView exerciseMoveImage;
        TextView textDayNum;
        TextView textDayDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseMoveImage = itemView.findViewById(R.id.imageview);
            textDayNum = itemView.findViewById(R.id.dayNum);
            textDayDesc = itemView.findViewById(R.id.dayDesc);
        }
    }
}
