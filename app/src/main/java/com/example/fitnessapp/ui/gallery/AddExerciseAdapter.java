package com.example.fitnessapp.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.MyViewHolder> implements Filterable {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Exercise> list;
    ArrayList<Exercise> listFilter;

    public AddExerciseAdapter(RecyclerViewInterface recyclerViewInterface, Context context, ArrayList<Exercise> list) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.list = list;
        this.listFilter = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_add_exercise, parent, false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseAdapter.MyViewHolder holder, int position) {

        Exercise exercise = listFilter.get(position);
        holder.exerciseName.setText(exercise.getExerciseName());

    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String d = charSequence.toString();
                if (d.isEmpty()){
                    listFilter = list;
                }
                else {
                    ArrayList<Exercise> temp = new ArrayList<>();
                    for(Exercise e : listFilter){
                        if(e.getExerciseName().toLowerCase().contains(d.toLowerCase())){
                            temp.add(e);
                        }
                    }
                    listFilter = temp;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFilter = (ArrayList<Exercise>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
