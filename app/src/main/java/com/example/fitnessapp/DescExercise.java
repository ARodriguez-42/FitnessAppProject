package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class DescExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_exercise);

        String exerciseName = getIntent().getStringExtra("exerciseName");
        String muscleGroupName = getIntent().getStringExtra("muscleGroupName");
        String equipmentName = getIntent().getStringExtra("equipmentName");
        String video = getIntent().getStringExtra("video");

        TextView exerciseNameIn = findViewById(R.id.exerciseNameInput);
        TextView muscleGroupNameIn = findViewById(R.id.muscleGroupNameInput);
        TextView equipmentNameIn = findViewById(R.id.equipmentNameInput);
        exerciseNameIn.setText(exerciseName);
        muscleGroupNameIn.setText(muscleGroupName);
        equipmentNameIn.setText(equipmentName);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.video);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(video, 0);
            }
        });


    }
}