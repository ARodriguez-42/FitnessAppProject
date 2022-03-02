package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class KGtoLBSActivity extends AppCompatActivity {

    Button KGConvert, LBSConvert;
    EditText LBSValue, KGValue;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kg_to_lbs);



        LBSValue = (EditText) findViewById(R.id.LBSValue);
        KGValue = (EditText) findViewById(R.id.KGValue);
        KGConvert = (MaterialButton)findViewById(R.id.KGConvert);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group_KG_LBS);

        KGConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.radio_KG){
                    double kg = Integer.parseInt(KGValue.getText().toString());
                    kg = kg * 2.20462262;
                    String result = Double.toString(kg);
                    LBSValue.setText(result);
                }
                else{
                    double kg = Integer.parseInt(LBSValue.getText().toString());
                    kg = kg / 2.20462262;
                    String result = Double.toString(kg);
                    KGValue.setText(result);
                }

            }
        });


    }
}