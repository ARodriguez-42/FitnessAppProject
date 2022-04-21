package com.example.fitnessapp.ui.macro;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;

public class MacroFragment extends Fragment {

    private MacroViewModel mViewModel;

    public static MacroFragment newInstance() {
        return new MacroFragment();
    }

    CalendarView calendarView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.macro_fragment, container, false);

        calendarView = view.findViewById(R.id.CalendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                int mo = month + 1;
                String date = mo + "/" + day + "/" + year;
                Context context = getContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, date, duration);
                toast.show();
                Intent intent = new Intent(getContext(), DisplayMacroActivity.class);
                intent.putExtra("today", date);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MacroViewModel.class);
        // TODO: Use the ViewModel
    }

}