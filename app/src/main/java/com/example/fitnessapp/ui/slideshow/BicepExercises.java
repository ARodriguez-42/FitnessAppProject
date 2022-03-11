package com.example.fitnessapp.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fitnessapp.DescExercise;
import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.ExerciseAdapter;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BicepExercises extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ExerciseAdapter exerciseAdapter;
    ArrayList<Exercise> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicep_exercises);

        recyclerView = findViewById(R.id.exerciseList);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Exercise>();
        exerciseAdapter = new ExerciseAdapter(this, list, this);
        recyclerView.setAdapter(exerciseAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    if(exercise.getMuscleGroupName().equals("Biceps")){
                        list.add(exercise);
                    }

                }

                exerciseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(BicepExercises.this, DescExercise.class);
        intent.putExtra("exerciseName", list.get(position).getExerciseName());
        intent.putExtra("muscleGroupName", list.get(position).getMuscleGroupName());
        intent.putExtra("equipmentName", list.get(position).getEquipmentName());
        intent.putExtra("image", list.get(position).getImage());
        intent.putExtra("video", list.get(position).getVideo());

        startActivity(intent);

    }
}