package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;

public class WilksActivity extends AppCompatActivity {

    MaterialButton wilksConvert;
    EditText bodyWeight, weightLifted, wilks;
    RadioGroup radio_group;
    RadioButton radio_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilks);

        wilksConvert = (MaterialButton) findViewById(R.id.wilksConvert);
        bodyWeight = (EditText) findViewById(R.id.bodyWeight);
        weightLifted = (EditText) findViewById(R.id.weightLifted);
        wilks = (EditText) findViewById(R.id.wilks);
        radio_group = (RadioGroup)findViewById(R.id.radio_group);

        wilksConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radio_group.getCheckedRadioButtonId() == R.id.radio_male){
                    double bodyW = Integer.parseInt(bodyWeight.getText().toString());
                    double weightL = Integer.parseInt(weightLifted.getText().toString());
                    double a = Math.pow(bodyW, 2);
                    double s = Math.pow(bodyW, 3);
                    double c = Math.pow(bodyW, 4);
                    double d = Math.pow(bodyW, 5);
                    double dd = 7.01863 * Math.pow( 10, -6);
                    double ee = -1.291 * Math.pow(10, -8);
                    double total = (weightL * 500)/(-216.0475144 + (16.2606339 * bodyW) + (-0.002388645 * a) +  (-0.00113732 * s) + (c * dd) + (d * ee));
                    String result = Double.toString(total);
                    wilks.setText(result);
                }
                else {
                    double bodyW = Integer.parseInt(bodyWeight.getText().toString());
                    double weightL = Integer.parseInt(weightLifted.getText().toString());
                    double a = Math.pow(bodyW, 2);
                    double s = Math.pow(bodyW, 3);
                    double c = Math.pow(bodyW, 4);
                    double d = Math.pow(bodyW, 5);
                    double dd = 4.731582 * Math.pow( 10, -5);
                    double ee = -9.054 * Math.pow(10, -8);
                    double total = (weightL * 500)/(594.31747775582 + (-27.23842536447 * bodyW) + (0.82112226871 * a) +  (-0.00930733913 * s) + c * dd + d * ee);
                    String result = Double.toString(total);
                    wilks.setText(result);
                }



            }
        });

    }
}