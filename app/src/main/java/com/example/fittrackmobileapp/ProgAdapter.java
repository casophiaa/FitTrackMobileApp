package com.example.fittrackmobileapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgAdapter extends RecyclerView.Adapter< ProgressViewHolder > {

    private Context context;
    private List< ProgFeatItem > progressList;

    ProgAdapter(Context context, List< ProgFeatItem > progressList){
        this.context = context;
        this.progressList = progressList;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProgressViewHolder holder, int position){
        holder.mImage.setImageResource(progressList.get(position).getImageResId());
        holder.title.setText(progressList.get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return progressList.size();
    }
}

class ProgressViewHolder extends RecyclerView.ViewHolder{

    ImageView mImage;
    TextView title;

    ProgressViewHolder(View itemView){
        super(itemView);

        mImage = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.tvTitle);
    }
}

