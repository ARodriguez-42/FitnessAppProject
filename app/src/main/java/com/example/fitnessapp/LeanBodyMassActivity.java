package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class LeanBodyMassActivity extends AppCompatActivity {

    MaterialButton lbmConvert;
    EditText bodyWeight, bodyFat, lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leanbodymass);

        bodyWeight = (EditText)findViewById(R.id.bodyWeight);
        bodyFat = (EditText)findViewById(R.id.bodyFat);
        lbm = (EditText)findViewById(R.id.lbm);
        lbmConvert = (MaterialButton) findViewById(R.id.lbmConvert);

        lbmConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double bodyWeight1 = Integer.parseInt(bodyWeight.getText().toString());
                double bodyFat1 = Integer.parseInt(bodyFat.getText().toString());
                double leanBodyMass = bodyWeight1 - (bodyWeight1 * (bodyFat1/100));
                String result = Double.toString(leanBodyMass);
                lbm.setText(result);
            }
        });
    }
}