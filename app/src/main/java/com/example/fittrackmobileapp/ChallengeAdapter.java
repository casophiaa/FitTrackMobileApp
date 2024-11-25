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
import java.util.List;

import com.bumptech.glide.Glide;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    private List<ChallengeData> challengeDataList;
    private Context context;

    public ChallengeAdapter(List<ChallengeData> challengeDataList, Context context) {
        this.challengeDataList = challengeDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChallengeData challengeData = challengeDataList.get(position);
        holder.dayNum.setText(challengeData.getDayNum());
        holder.dayDesc.setText(challengeData.getDayDesc());

        if (!challengeData.getExerciseMove().isEmpty()) {
            Glide.with(context)
                    .load(challengeData.getExerciseMove()) // URL from Firebase
                    .placeholder(R.drawable.yogaone) // Optional placeholder image
                    .into(holder.exerciseMove);
        } else {
            holder.exerciseMove.setImageResource(R.drawable.yogaone); // Default placeholder
        }

        // Set click listener on the item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChallengeActivity.class);
            intent.putExtra("DayNum", challengeData.getDayNum());
            intent.putExtra("DayDesc", challengeData.getDayDesc());
            intent.putExtra("exerciseMove", challengeData.getExerciseMove());

            // Pass the exercise details
            ArrayList<ChallengeMoveDetails> exerciseDetails = new ArrayList<>(challengeData.getExerciseDetails());
            intent.putParcelableArrayListExtra("exerciseDetails", exerciseDetails);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return challengeDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayNum, dayDesc;
        ImageView exerciseMove;

        public ViewHolder(View itemView) {
            super(itemView);
            dayNum = itemView.findViewById(R.id.DayNum);
            dayDesc = itemView.findViewById(R.id.DayDesc);
            exerciseMove = itemView.findViewById(R.id.imageview);

        }
    }
}
