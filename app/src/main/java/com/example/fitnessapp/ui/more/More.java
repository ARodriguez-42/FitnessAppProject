package com.example.fitnessapp.ui.more;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnessapp.KGtoLBSActivity;
import com.example.fitnessapp.LeanBodyMassActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.WilksActivity;

public class More extends Fragment {

    private MoreViewModel mViewModel;

    public static More newInstance() {
        return new More();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        Button BMIbutton = view.findViewById(R.id.BMIbutton);
        Button WILKSbutton = view.findViewById(R.id.WILKSbutton);
        Button KGbutton = view.findViewById(R.id.KGbutton);

        KGbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), KGtoLBSActivity.class));

            }
        });

        BMIbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), LeanBodyMassActivity.class));

            }
        });

        WILKSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), WilksActivity.class));

            }
        });



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        // TODO: Use the ViewModel
    }

}