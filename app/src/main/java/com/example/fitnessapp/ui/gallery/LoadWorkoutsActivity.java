package com.example.fitnessapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadWorkoutsActivity extends AppCompatActivity implements RecyclerViewInterface {

    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    WorkoutAdapter workoutAdapter;
    RecyclerView recyclerView;
    ArrayList<Workout> list;
    String d;
    ArrayList<CompExer> PPL1, PPL2, PPL3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_workouts);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        d = getIntent().getExtras().getString("date");
        d = d.replaceAll("/", ".");

        list = new ArrayList<>();

        recyclerView = findViewById(R.id.addWorkoutList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutAdapter = new WorkoutAdapter(this, this, list);
        recyclerView.setAdapter(workoutAdapter);

        ArrayList<Set> set = new ArrayList<>();
        for (int i = 0 ; i < 3; i++){
            set.add(new Set(10, 1));
        }
        ArrayList<Set> set1 = new ArrayList<>();
        for (int i = 0 ; i < 3; i++){
            set1.add(new Set(12, 1));
        }
        ArrayList<Set> set2 = new ArrayList<>();
        for (int i = 0 ; i < 3; i++){
            set2.add(new Set(8, 1));
        }
        PPL1 = new ArrayList<>();
        PPL1.add(new CompExer("Barbell Bench Press", set2));
        PPL1.add(new CompExer("Dumbbell Shoulder Press", set));
        PPL1.add(new CompExer("Dips", set2));
        PPL1.add(new CompExer("Cable Flys", set1));
        PPL1.add(new CompExer("Dumbbell Lateral Raises", set));
        PPL1.add(new CompExer("Cable Tricep Extensions", set1));

        PPL2 = new ArrayList<>();
        PPL2.add(new CompExer("Barbell Squat", set2));
        PPL2.add(new CompExer("Lying Hamstring Curl", set));
        PPL2.add(new CompExer("Hack Squats", set2));
        PPL2.add(new CompExer("Leg Extension", set1));
        PPL2.add(new CompExer("Seated Calf Raises", set1));

        PPL3 = new ArrayList<>();
        PPL3.add(new CompExer("Conventional Deadlift", set2));
        PPL3.add(new CompExer("T-Bar Row", set));
        PPL3.add(new CompExer("Wide Grip Lat Pulldown", set2));
        PPL3.add(new CompExer("EZ Bar Curl", set1));
        PPL3.add(new CompExer("Hammer Curls", set));
        PPL3.add(new CompExer("Dumbbell Shrugs", set1));

        list.add(new Workout("Push Pull Legs: Push Day", "Chest, Shoulders, Triceps"));
        list.add(new Workout("Push Pull Legs: Leg Day", "Quads, Hamstrings, Glutes, Calves"));
        list.add(new Workout("Push Pull Legs: Pull Day", "Back, Biceps, Traps"));

        workoutAdapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(int position) {
        String name = list.get(position).getName();
        if (name == "Push Pull Legs: Push Day"){
            for(CompExer compExer: PPL1){
                firestore.collection("users").document(userID)
                        .collection("workouts").document(d)
                        .collection(d).document(compExer.name).set(compExer);
            }
            Intent intent = new Intent(LoadWorkoutsActivity.this, DisplayWorkout.class);
            intent.putExtra("today", d);
            startActivity(intent);
        }
        else if (name == "Push Pull Legs: Leg Day"){
            for(CompExer compExer: PPL2){
                firestore.collection("users").document(userID)
                        .collection("workouts").document(d)
                        .collection(d).document(compExer.name).set(compExer);
            }
            Intent intent = new Intent(LoadWorkoutsActivity.this, DisplayWorkout.class);
            intent.putExtra("today", d);
            startActivity(intent);
        }
        else{
            for(CompExer compExer: PPL3){
                firestore.collection("users").document(userID)
                        .collection("workouts").document(d)
                        .collection(d).document(compExer.name).set(compExer);
            }
            Intent intent = new Intent(LoadWorkoutsActivity.this, DisplayWorkout.class);
            intent.putExtra("today", d);
            startActivity(intent);
        }
    }
}