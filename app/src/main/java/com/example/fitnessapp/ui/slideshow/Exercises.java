package com.example.fitnessapp.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.ExerciseAdapter;
import com.example.fitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ExerciseAdapter exerciseAdapter;
    ArrayList<Exercise> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        recyclerView = (RecyclerView) findViewById(R.id.exerciseList);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Exercise>();
        exerciseAdapter = new ExerciseAdapter(this, list);
        recyclerView.setAdapter(exerciseAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    list.add(exercise);

                }

                exerciseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}