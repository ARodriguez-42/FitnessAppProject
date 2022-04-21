package com.example.fitnessapp.ui.macro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessapp.R;
import com.google.android.material.button.MaterialButton;

public class DisplayMacroActivity extends AppCompatActivity {

    ProgressBar fBar, cBar, pBar;
    TextView pInput, cInput, fInput, fProgress, cProgress, pProgress, date;
    MaterialButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_macro);

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

        cBar.setProgress(0);
        fBar.setProgress(0);
        pBar.setProgress(0);

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

            }
        });



    }
}