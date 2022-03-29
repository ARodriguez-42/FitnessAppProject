package com.example.fitnessapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.KGtoLBSActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RegisterActivity;
import com.example.fitnessapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    CalendarView calendarView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        calendarView = view.findViewById(R.id.CalendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = month + "/" + day + "/" + year;
                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, date, duration);
                toast.show();
                Intent intent = new Intent(getContext(), WorkoutAdd.class);
                intent.putExtra("today", date);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}