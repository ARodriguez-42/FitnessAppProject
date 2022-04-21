package com.example.fitnessapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;

import java.util.ArrayList;

public class CompExerDashAdapter extends RecyclerView.Adapter<CompExerDashAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<CompExerDash> list;

    public CompExerDashAdapter(Context context, ArrayList<CompExerDash> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CompExerDashAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.completed_exercise_item, parent, false);
        return new CompExerDashAdapter.MyViewHolder(v, recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull CompExerDashAdapter.MyViewHolder holder, int position) {

        CompExerDash compExer = list.get(position);
        String name = compExer.getName();
        holder.exerciseName.setText(name);
        Log.d("Display of the method: ", String.valueOf(compExer.displayExercises(compExer.getList())));
        holder.setsAndReps.setText(compExer.displayExercises(compExer.getList()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView exerciseName, setsAndReps;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exercise_name);
            setsAndReps = itemView.findViewById(R.id.completed_description);


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
