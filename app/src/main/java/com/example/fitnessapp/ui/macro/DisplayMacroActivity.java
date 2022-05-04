package com.example.fitnessapp.ui.macro;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fitnessapp.MainMenu;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.example.fitnessapp.ui.gallery.AddSets;
import com.example.fitnessapp.ui.gallery.CompExer;
import com.example.fitnessapp.ui.gallery.CompExerAdapter;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;
import com.example.fitnessapp.ui.gallery.Set;
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

public class DisplayMacroActivity extends AppCompatActivity implements RecyclerViewInterface {

    ProgressBar fBar, cBar, pBar;
    TextView fProgress, cProgress, pProgress, date;
    MaterialButton edit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userID;
    ImageButton back, goal;
    int maxF, maxC, maxP;
    int finalF, finalC, finalP;
    RecyclerView recyclerView;
    MealAdapter mealAdapter;
    ArrayList<Meal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macro);

        list = new ArrayList<>();

        recyclerView = findViewById(R.id.foodList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealAdapter = new MealAdapter(this, list , this);
        recyclerView.setAdapter(mealAdapter);

        maxC = 100;
        maxF = 100;
        maxP = 100;
        finalF = 0;
        finalC = 0;
        finalP = 0;

        back = findViewById(R.id.backButton);
        goal = findViewById(R.id.goalButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        fBar = (ProgressBar) findViewById(R.id.fBar);
        cBar = (ProgressBar) findViewById(R.id.cBar);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        fProgress = findViewById(R.id.fProgress);
        cProgress = findViewById(R.id.cProgress);
        pProgress = findViewById(R.id.pProgress);
        edit = findViewById(R.id.edit_macros);
        date = findViewById(R.id.displayDate);
        String d = getIntent().getExtras().getString("today");
        date.setText(d);

        String temp = d.replaceAll("/", ".");

        DocumentReference dR = firestore.collection("users").document(userID)
                .collection("goals").document("macro");
        dR.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){

                        long c = (long) documentSnapshot.get("carb");
                        long p = (long) documentSnapshot.get("protein");
                        long f = (long) documentSnapshot.get("fat");
                        int carb = (int)c;
                        int protein = (int)p;
                        int fat = (int)f;
                        cBar.setMax(carb);
                        fBar.setMax(fat);
                        pBar.setMax(protein);
                        maxC = carb;
                        maxF = fat;
                        maxP = protein;
                        cBar.setProgress(finalC);
                        fBar.setProgress(finalF);
                        pBar.setProgress(finalP);
                        String cText = finalC + "/" + maxC;
                        String pText = finalP + "/" + maxP;
                        String fText = finalF + "/" + maxF;
                        pProgress.setText(pText);
                        fProgress.setText(fText);
                        cProgress.setText(cText);
                    }
                }
            }
        });



        firestore.collection("users").document(userID)
                .collection("macros").document(temp)
                .collection(temp).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){

                    long t = (long) documentChange.getDocument().get("c");
                    int c = (int) t;
                    long t1 = (long) documentChange.getDocument().get("p");
                    int p = (int) t1;
                    long t2 = (long) documentChange.getDocument().get("f");
                    int f = (int) t2;
                    String name = (String) documentChange.getDocument().get("name");
                    Meal meal = new Meal(name, c, p, f);
                    list.add(meal);
                    mealAdapter.notifyDataSetChanged();
                }
            }
        });



        DocumentReference documentReference = firestore.collection("users").document(userID).collection("macros").document(temp);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d("TAG", String.valueOf(documentSnapshot.get("carb")));

                        long c = (long) documentSnapshot.get("carb");
                        long p = (long) documentSnapshot.get("protein");
                        long f = (long) documentSnapshot.get("fat");
                        int carb = (int)c;
                        int protein = (int)p;
                        int fat = (int)f;
                        finalF = fat;
                        finalC = carb;
                        finalP = protein;
                        cBar.setProgress(carb);
                        fBar.setProgress(fat);
                        pBar.setProgress(protein);
                        String cText = carb + "/" + maxC;
                        String pText = protein + "/" + maxP;
                        String fText = fat + "/" + maxF;
                        pProgress.setText(pText);
                        fProgress.setText(fText);
                        cProgress.setText(cText);
                    }
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(DisplayMacroActivity.this);
                dialog.setContentView(R.layout.dialog_add_meal);
                Button editM = dialog.findViewById(R.id.addMeal);
                EditText n = dialog.findViewById(R.id.name);
                EditText p = dialog.findViewById(R.id.pInput);
                EditText c = dialog.findViewById(R.id.cInput);
                EditText f = dialog.findViewById(R.id.fInput);

                editM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int c1 = Integer.parseInt(c.getText().toString());
                        int p1 = Integer.parseInt(p.getText().toString());
                        int f1 = Integer.parseInt(f.getText().toString());
                        finalC += c1;
                        finalP += p1;
                        finalF += f1;
                        cBar.setProgress(finalC);
                        fBar.setProgress(finalF);
                        pBar.setProgress(finalP);
                        String cText = finalC + "/" + maxC;
                        String pText = finalP + "/" + maxP;
                        String fText = finalF + "/" + maxF;
                        pProgress.setText(pText);
                        fProgress.setText(fText);
                        cProgress.setText(cText);

                        HashMap hashMap = new HashMap();
                        hashMap.put("carb", finalC);
                        hashMap.put("protein", finalP);
                        hashMap.put("fat", finalF);
                        firestore.collection("users").document(userID)
                                .collection("macros").document(temp).set(hashMap);

                        String name = n.getText().toString();

                        Meal meal = new Meal(name, c1, p1, f1);
                        firestore.collection("users").document(userID)
                                .collection("macros").document(temp)
                        .collection(temp).document(name).set(meal);

                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMacroActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DisplayMacroActivity.this);
                dialog.setContentView(R.layout.dialog_macro_goal);
                Button editM = dialog.findViewById(R.id.editMacro);
                EditText p = dialog.findViewById(R.id.pInput);
                EditText c = dialog.findViewById(R.id.cInput);
                EditText f = dialog.findViewById(R.id.fInput);

                editM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int protein = Integer.parseInt(p.getText().toString());
                        int carb = Integer.parseInt(c.getText().toString());
                        int fat = Integer.parseInt(f.getText().toString());
                        HashMap hashMap = new HashMap();
                        hashMap.put("carb", carb);
                        hashMap.put("protein", protein);
                        hashMap.put("fat", fat);
                        maxC = carb;
                        maxF = fat;
                        maxP = protein;
                        firestore.collection("users").document(userID)
                                .collection("goals").document("macro").set(hashMap);
                        String cText = finalC + "/" + maxC;
                        String pText = finalP + "/" + maxP;
                        String fText = finalF + "/" + maxF;
                        pProgress.setText(pText);
                        fProgress.setText(fText);
                        cProgress.setText(cText);
                        cBar.setMax(maxC);
                        fBar.setMax(maxF);
                        pBar.setMax(maxP);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }
}