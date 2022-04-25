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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fitnessapp.MainMenu;
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

public class DisplayWorkout extends AppCompatActivity implements RecyclerViewInterface {

    TextView date;
    MaterialButton newWorkout, save;
    ArrayList<CompExer> list;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CompExerAdapter compExerAdapter;
    String userID;
    Toolbar toolbar;
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_work);

        toolbar = findViewById(R.id.toolbar);
        ImageButton back = findViewById(R.id.backButton);

        list = new ArrayList<CompExer>();

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        newWorkout = findViewById(R.id.new_workout);
        date = findViewById(R.id.displayDate);
        recyclerView = findViewById(R.id.completedSetList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        compExerAdapter = new CompExerAdapter(this, list , this);
        recyclerView.setAdapter(compExerAdapter);
        String d = getIntent().getExtras().getString("today");
        date.setText(d);

        temp = d.replaceAll("/", ".");

        firestore.collection("users").document(userID)
                .collection("workouts").document(temp)
                .collection(temp).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    Log.d("TAG", String.valueOf(documentChange.getDocument().get("list")));
                    ArrayList<HashMap> arrayList = (ArrayList<HashMap>) documentChange.getDocument().get("list");
                    ArrayList<Set> setArrayList = new ArrayList<>();
                    for (HashMap hashMap: arrayList){
                        long r1 = (long) hashMap.get("reps");
                        long w1 = (long) hashMap.get("weight");
                        int r = (int)r1;
                        int w = (int)w1;
                        setArrayList.add(new Set(r,w));
                    }
                    String name = (String) documentChange.getDocument().get("name");
                    CompExer compExer = new CompExer(name, setArrayList);
                    list.add(compExer);
                    compExerAdapter.notifyDataSetChanged();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayWorkout.this, MainMenu.class);
                startActivity(intent);
            }
        });



        newWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayWorkout.this, AddExercise.class);
                intent.putExtra("date", d);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onItemClick(int position) {

        Log.d("TAG", String.valueOf(position));

        Dialog dialog = new Dialog(DisplayWorkout.this);
        dialog.setContentView(R.layout.dialog_edit_workout);

        Button del = dialog.findViewById(R.id.delete);
        Button editW = dialog.findViewById(R.id.editWorkout);
        Button can = dialog.findViewById(R.id.cancel);

        editW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayWorkout.this, AddSets.class);
                intent.putExtra("exerciseName", list.get(position).getName());
                intent.putExtra("date", temp);
                startActivity(intent);
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = list.get(position).getName();

                DocumentReference documentReference = firestore.collection("users").document(userID)
                        .collection("workouts").document(temp)
                        .collection(temp).document(name);
                documentReference.delete();
                compExerAdapter.remove(list.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}