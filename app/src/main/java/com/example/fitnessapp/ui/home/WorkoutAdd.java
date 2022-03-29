package com.example.fitnessapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fitnessapp.R;

public class WorkoutAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_add);

        TextView textView = findViewById(R.id.textView);
        String date = getIntent().getExtras().getString("today");
        textView.setText(date);
    }
}