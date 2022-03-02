package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView tvBacktoLogin;
    Button btnReset;
    TextInputEditText resetEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnReset = findViewById(R.id.btnReset);
        resetEmail = findViewById(R.id.resetEmail);
        mAuth = FirebaseAuth.getInstance();
        tvBacktoLogin = findViewById(R.id.tvBacktoLogin);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmail.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPassword.this, "Reset Link was sent, check you email.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPassword.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        tvBacktoLogin.setOnClickListener(view ->{
            startActivity(new Intent(ResetPassword.this, MainActivity.class));
        });


    }
}