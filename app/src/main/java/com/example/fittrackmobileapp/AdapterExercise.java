package com.example.fittrackmobileapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ViewHolder> {

    private Context context;
    private List<ItemsExercise> exerciseList;

    public AdapterExercise(Context context, List<ItemsExercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemsExercise item = exerciseList.get(position);
        holder.nameTextView.setText(item.getName());
        Glide.with(context)
                .load(item.getImageResource())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExerciseDetails.class);
            intent.putExtra("EXERCISE_NAME", item.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseName);
            imageView = itemView.findViewById(R.id.exerciseImage);
        }
    }
}
