package com.example.fitnessapp.ui.macro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.fitnessapp.MainMenu;
import com.example.fitnessapp.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macro);

        ImageButton back = findViewById(R.id.backButton);

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
                        cBar.setProgress(carb);
                        fBar.setProgress(fat);
                        pBar.setProgress(protein);
                        String cText = carb + "/100";
                        String pText = protein + "/100";
                        String fText = fat + "/100";
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
                String s = p + "/100";
                pProgress.setText(s);
                int f =  Integer.parseInt(fInput.getText().toString());
                fBar.setProgress(f);
                String t = f + "/100";
                fProgress.setText(t);
                int c =  Integer.parseInt(cInput.getText().toString());
                cBar.setProgress(c);
                String v = c + "/100";
                cProgress.setText(v);
                HashMap hashMap = new HashMap();
                hashMap.put("carb", c);
                hashMap.put("protein", p);
                hashMap.put("fat", f);
                firestore.collection("users").document(userID)
                        .collection("macros").document(temp).set(hashMap);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMacroActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });


    }
}