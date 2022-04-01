package com.example.fitnessapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.ExerciseAdapter;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;

import java.util.ArrayList;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Exercise> list;

    public AddExerciseAdapter(RecyclerViewInterface recyclerViewInterface, Context context, ArrayList<Exercise> list) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_add_exercise, parent, false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseAdapter.MyViewHolder holder, int position) {

        Exercise exercise = list.get(position);
        holder.exerciseName.setText(exercise.getExerciseName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView exerciseName;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseNameInput);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getBindingAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }

}
