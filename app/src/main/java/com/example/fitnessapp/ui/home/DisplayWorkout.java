package com.example.fitnessapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitnessapp.LeanBodyMassActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayWorkout extends AppCompatActivity implements RecyclerViewInterface {

    TextView date;
    MaterialButton newWorkout;
    ArrayList<CompExer> list;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_work);


        list = new ArrayList<CompExer>();

        newWorkout = findViewById(R.id.new_workout);
        date = findViewById(R.id.displayDate);
        recyclerView = findViewById(R.id.completedList);
        String d = getIntent().getExtras().getString("today");
        date.setText(d);

        newWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DisplayWorkout.this, AddExercise.class);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onItemClick(int position) {

    }
}