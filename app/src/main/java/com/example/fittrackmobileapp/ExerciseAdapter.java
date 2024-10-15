package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private Context context;
    private List<ExerciseItem> exerciseList;

    public ExerciseAdapter(Context context, List<ExerciseItem> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseItem item = exerciseList.get(position);
        holder.textView.setText(item.getName());
        holder.imageView.setImageResource(item.getImageResource());

        // Set click listener for the card
        holder.cardView.setOnClickListener(v -> {
            if (item.getName().equals("Yoga")) {
                // Start YogaActivity when Yoga card is clicked
                context.startActivity(new Intent(context, Yoga.class));
            }
        });

        Log.d("ExerciseAdapter", "Binding item: " + item.getName());
    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CardView cardView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exerciseName);
            imageView = itemView.findViewById(R.id.exerciseImage);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
