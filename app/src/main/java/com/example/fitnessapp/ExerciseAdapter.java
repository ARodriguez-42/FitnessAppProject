package com.example.fitnessapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    Context context;
    ArrayList<Exercise> list;

    public ExerciseAdapter(Context context, ArrayList<Exercise> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Exercise exercise = list.get(position);
        holder.exerciseName.setText(exercise.getExerciseName());
        holder.muscleGroupName.setText(exercise.getMuscleGroupName());
        holder.equipmentName.setText(exercise.getEquipmentName());
        Glide.with(holder.imageView).load(exercise.getImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        TextView exerciseName, muscleGroupName, equipmentName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseNameInput);
            muscleGroupName = itemView.findViewById(R.id.muscleGroupNameInput);
            equipmentName = itemView.findViewById(R.id.equipmentNameInput);
            imageView = itemView.findViewById(R.id.imageInput);

        }
    }

}
