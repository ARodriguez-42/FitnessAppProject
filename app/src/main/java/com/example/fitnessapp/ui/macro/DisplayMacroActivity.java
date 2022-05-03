package com.example.fitnessapp.ui.macro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.fitnessapp.ui.gallery.AddSets;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayMacroActivity extends AppCompatActivity {

    ProgressBar fBar, cBar, pBar;
    TextView pInput, cInput, fInput, fProgress, cProgress, pProgress, date;
    MaterialButton edit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userID;
    ImageButton back, goal;
    int maxF, maxC, maxP;
    int finalF, finalC, finalP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macro);

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
        pInput = findViewById(R.id.proteinInput);
        cInput = findViewById(R.id.carbInput);
        fInput = findViewById(R.id.fatInput);
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

        cBar.setProgress(0);
        fBar.setProgress(0);
        pBar.setProgress(0);

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
                int p =  Integer.parseInt(pInput.getText().toString());
                pBar.setProgress(p);
                String s = p + "/" + maxP;
                pProgress.setText(s);
                int f =  Integer.parseInt(fInput.getText().toString());
                fBar.setProgress(f);
                String t = f + "/" + maxF;
                fProgress.setText(t);
                int c =  Integer.parseInt(cInput.getText().toString());
                cBar.setProgress(c);
                String v = c + "/" + maxC;
                cProgress.setText(v);
                HashMap hashMap = new HashMap();
                hashMap.put("carb", c);
                hashMap.put("protein", p);
                hashMap.put("fat", f);

                hashMap.put("carbMax", maxC);
                hashMap.put("proteinMax", maxP);
                hashMap.put("fatMax", maxF);

                firestore.collection("users").document(userID)
                        .collection("macros").document(temp).set(hashMap);
                HashMap test = new HashMap();
                test.put("carb", maxC);
                test.put("protein", maxP);
                test.put("fat", maxF);
                test.put("tcarb", c);
                test.put("tprotein", p);
                test.put("tfat", f);

                firestore.collection("users").document(userID)
                        .collection("goals").document("macro").set(test);
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
}