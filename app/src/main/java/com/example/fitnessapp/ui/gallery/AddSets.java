package com.example.fitnessapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSets extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<HashMap> hashMapArrayList;

    ArrayList<Set> setList;
    MaterialButton newSet;
    MaterialButton saveExercise;
    RecyclerView recyclerView;
    TextView name;

    String exerciseName;
    String date;
    String datePass;
    AddSetsAdapter addSetsAdapter;
    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sets);

        name = findViewById(R.id.displayExercise);
        newSet = findViewById(R.id.newSet);
        saveExercise = findViewById(R.id.saveExercise);
        recyclerView = findViewById(R.id.completedSetList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date = getIntent().getExtras().getString("date");
        datePass = date;
        exerciseName = getIntent().getExtras().getString("exerciseName");
        name.setText(exerciseName);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        setList = new ArrayList<Set>();
        //setList.add(new Set(12, 240));
        //setList.add(new Set(13, 240));
        //setList.add(new Set(14, 240));

        addSetsAdapter = new AddSetsAdapter(this, this, setList);
        recyclerView.setAdapter(addSetsAdapter);

        addSetsAdapter.notifyDataSetChanged();

        newSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(AddSets.this);
                dialog.setContentView(R.layout.dialog_add_set);

                TextView rep = dialog.findViewById(R.id.repInput);
                TextView weight = dialog.findViewById(R.id.weightInput);
                Button add = dialog.findViewById(R.id.addSetDialog);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int reps = Integer.parseInt(rep.getText().toString());
                        int we = Integer.parseInt(weight.getText().toString());
                        Set set = new Set(reps, we);
                        setList.add(set);
                        addSetsAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        saveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = date.replaceAll("/", ".");
                //DocumentReference documentReference = firestore.collection("users").document(userID).collection("workouts").document(date);

                CompExer compExer = new CompExer(exerciseName, setList);
                firestore.collection("users").document(userID)
                        .collection("workouts").document(date)
                        .collection(date).document(exerciseName).set(compExer);


                Intent intent = new Intent(AddSets.this, DisplayWorkout.class);
                intent.putExtra("today", datePass);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onItemClick(int position) {
        Dialog dialog = new Dialog(AddSets.this);
        dialog.setContentView(R.layout.dialog_edit_del_set);

        Button del = dialog.findViewById(R.id.delete);
        TextView rep = dialog.findViewById(R.id.repInput);
        TextView weight = dialog.findViewById(R.id.weightInput);
        Button edit = dialog.findViewById(R.id.addSetDialog);
        Button can = dialog.findViewById(R.id.cancel);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reps = Integer.parseInt(rep.getText().toString());
                int we = Integer.parseInt(weight.getText().toString());
                Set set = new Set(reps, we);
                setList.set(position,set);
                addSetsAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setList.remove(position);
                addSetsAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}