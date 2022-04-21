package com.example.fitnessapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;
import com.example.fitnessapp.ui.gallery.Set;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    //private FragmentHomeBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CompExerDashAdapter compExerAdapter;
    String userID;
    String temp;
    ArrayList<CompExerDash> list;

    ProgressBar protein, fat, carb;
    EditText date;
    RecyclerView workoutView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        protein = view.findViewById(R.id.proteinBar);
        fat = view.findViewById(R.id.fatsBar);
        carb = view.findViewById(R.id.carbsBar);
        date = view.findViewById(R.id.todayDate);
        workoutView = view.findViewById(R.id.workoutList);

        String d = new SimpleDateFormat("M-dd-yyyy", Locale.getDefault()).format(new Date());

        temp = d.replaceAll("-", ".");

        Log.d("TAG", String.valueOf(temp));

        date.setText(d);

        workoutView.setHasFixedSize(true);
        workoutView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        compExerAdapter = new CompExerDashAdapter(this.getContext(), list , this);
        workoutView.setAdapter(compExerAdapter);

        displayWorkouts();

        return view;
    }

    private void displayWorkouts() {

        firestore.collection("users").document(userID)
                .collection("workouts").document(temp)
                .collection(temp).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange documentChange : value.getDocumentChanges()){

                    if (documentChange.getType() == DocumentChange.Type.ADDED){
                        ArrayList<HashMap> arrayList = (ArrayList<HashMap>) documentChange
                                .getDocument().get("list");
                        ArrayList<Set> setArrayList = new ArrayList<Set>();
                        for (HashMap hashMap : arrayList){
                            long r1 = (long) hashMap.get("reps");
                            long w1 = (long) hashMap.get("weight");
                            int r = (int)r1;
                            int w = (int)w1;
                            setArrayList.add(new Set(r, w));
                        }

                        String name = (String) documentChange.getDocument().get("name");
                        CompExerDash compExer = new CompExerDash(name, setArrayList);
                        Log.d("TAG", "MAthod is here");
                        Log.d("TAG", String.valueOf(arrayList));
                        list.add(compExer);
                        compExerAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    @Override
    public void onItemClick(int position) {

    }
}