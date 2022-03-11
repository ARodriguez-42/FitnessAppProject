package com.example.fitnessapp.ui.slideshow;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.KGtoLBSActivity;
import com.example.fitnessapp.R;
import com.google.android.material.button.MaterialButton;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel mViewModel;

    public static SlideshowFragment newInstance() {
        return new SlideshowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        MaterialButton listButton = view.findViewById(R.id.ListButton);
        MaterialButton backListButton = view.findViewById(R.id.BackListButton);
        MaterialButton absListButton = view.findViewById(R.id.AbsListButton);
        MaterialButton bicepListButton = view.findViewById(R.id.BicepsListButton);
        MaterialButton chestListButton = view.findViewById(R.id.ChestListButton);
        MaterialButton legsListButton = view.findViewById(R.id.LegsListButton);
        MaterialButton shoulderListButton = view.findViewById(R.id.ShoulderListButton);
        MaterialButton tricepListButton = view.findViewById(R.id.TricepListButton);


        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), Exercises.class));

            }
        });

        backListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), BackExercises.class));

            }
        });

        absListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AbsExercises.class));

            }
        });

        bicepListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), BicepExercises.class));

            }
        });

        chestListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ChestExercises.class));

            }
        });

        legsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), LegsExercises.class));

            }
        });

        shoulderListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ShoulderExercises.class));

            }
        });

        tricepListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), TricepExercises.class));

            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        // TODO: Use the ViewModel
    }

}