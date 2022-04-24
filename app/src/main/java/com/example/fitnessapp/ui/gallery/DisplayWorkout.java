package com.example.fitnessapp.ui.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        String temp = d.replaceAll("/", ".");

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

        //DocumentReference documentReference = firestore.collection("users").document(userID).collection("workouts").document(temp);


        /*
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        ArrayList<HashMap> arrayList = (ArrayList<HashMap>) documentSnapshot.get("list");
                        Log.d("TAG", String.valueOf(arrayList));
                        ArrayList<Set> setArrayList = new ArrayList<Set>();
                        for (HashMap hashMap : arrayList){
                            long r1 = (long) hashMap.get("reps");
                            long w1 = (long) hashMap.get("weight");
                            int r = (int)r1;
                            int w = (int)w1;
                            setArrayList.add(new Set(r, w));
                        }

                        String name = (String) documentSnapshot.get("name");
                        CompExer compExer = new CompExer(name, setArrayList);
                        list.add(compExer);
                        compExerAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                }
                else {
                    Log.d("TAG", "get failed with ", task.getException());
                }

            }
        });
        */


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayWorkout.this, MainMenu.class);
                startActivity(intent);
            }
        });

        /*
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null && documentSnapshot.exists()){
                    CompExer compExer = (CompExer) documentSnapshot.get("workoutSet");
                    list.add(compExer);
                    compExerAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(DisplayWorkout.this, "Nothing was read from Firestore for this date" + temp, Toast.LENGTH_SHORT).show();

                }
            }
        });
        */


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

    }
}