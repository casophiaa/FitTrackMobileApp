package com.example.fittrackmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChallengeMoveAdapter extends RecyclerView.Adapter<ChallengeMoveAdapter.ExerciseViewHolder> {

    private List<ChallengeMoveDetails> exerciseList;
    private Context context;

    public ChallengeMoveAdapter(List<ChallengeMoveDetails> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_move, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ChallengeMoveDetails exercise = exerciseList.get(position);
        holder.textExerciseName.setText(exercise.getMoveName());
        holder.textDuration.setText("Duration: " + exercise.getDuration());

        if (!exercise.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(exercise.getImageUrl()) // URL from Firebase
                    .placeholder(R.drawable.workoutone) // Optional placeholder image
                    .into(holder.imageUrl);
        } else {
            holder.imageUrl.setImageResource(R.drawable.workoutone); // Default placeholder
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView textExerciseName, textDuration;
        ImageView imageUrl;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textExerciseName = itemView.findViewById(R.id.textExerciseName);
            textDuration = itemView.findViewById(R.id.textDuration);
            imageUrl = itemView.findViewById(R.id.imageview);
        }
    }
}

