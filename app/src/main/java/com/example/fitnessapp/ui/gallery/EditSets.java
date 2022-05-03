package com.example.fitnessapp.ui.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class EditSets extends AppCompatActivity implements RecyclerViewInterface {

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
        setContentView(R.layout.activity_edit_sets);

        name = findViewById(R.id.displayExercise);
        newSet = findViewById(R.id.newSet);
        saveExercise = findViewById(R.id.saveExercise);
        recyclerView = findViewById(R.id.completedSetList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date = getIntent().getExtras().getString("date");
        datePass = date.replaceAll("/", ".");
        exerciseName = getIntent().getExtras().getString("exerciseName");
        name.setText(exerciseName);

        setList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("users").document(userID)
                .collection("workouts").document(datePass)
                .collection(datePass).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    Log.d("TAG", "set list");
                    Log.d("TAG", String.valueOf(documentChange.getDocument().get("list")));
                    ArrayList<HashMap> arrayList = (ArrayList<HashMap>) documentChange.getDocument().get("list");
                    String name = (String) documentChange.getDocument().get("name");
                    if (name.equals(exerciseName)){
                        for (HashMap hashMap: arrayList){
                            long r1 = (long) hashMap.get("reps");
                            long w1 = (long) hashMap.get("weight");
                            int r = (int)r1;
                            int w = (int)w1;
                            setList.add(new Set(r,w));
                        }
                    }
                }
            }
        });



        addSetsAdapter = new AddSetsAdapter(this, this, setList);
        recyclerView.setAdapter(addSetsAdapter);

        addSetsAdapter.notifyDataSetChanged();

        newSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(EditSets.this);
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


                Intent intent = new Intent(EditSets.this, DisplayWorkout.class);
                intent.putExtra("today", datePass);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Dialog dialog = new Dialog(EditSets.this);
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