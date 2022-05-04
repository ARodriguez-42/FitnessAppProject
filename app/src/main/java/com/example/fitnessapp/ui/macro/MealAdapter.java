package com.example.fitnessapp.ui.macro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.example.fitnessapp.ui.gallery.CompExer;
import com.example.fitnessapp.ui.gallery.CompExerAdapter;

import java.util.ArrayList;


public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Meal> list;

    public MealAdapter(Context context, ArrayList<Meal> list, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.meal_item, parent, false);
        return new MealAdapter.MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.MyViewHolder holder, int position) {
        Meal meal = list.get(position);
        String name = meal.name;
        String c = Integer.toString(meal.c);
        String p = Integer.toString(meal.c);
        String f = Integer.toString(meal.c);
        holder.name.setText(name);
        holder.cIn.setText(c);
        holder.fIn.setText(f);
        holder.pIn.setText(p);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cIn, pIn, fIn, name;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            cIn = itemView.findViewById(R.id.cIn);
            pIn = itemView.findViewById(R.id.pIn);
            fIn = itemView.findViewById(R.id.fIn);
            name = itemView.findViewById(R.id.name);


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
