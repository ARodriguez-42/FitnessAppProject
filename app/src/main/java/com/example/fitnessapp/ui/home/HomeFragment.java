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
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    //private FragmentHomeBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore, firebaseFirestore;
    CompExerDashAdapter compExerAdapter;
    String userID;
    String temp;
    ArrayList<CompExerDash> list;
    int maxF, maxC, maxP;
    ProgressBar protein, fat, carb;
    TextView date;
    RecyclerView workoutView;
    TextView totalC, totalF, totalP;
    int finalF, finalC, finalP;
    int mc, mp , mf;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        date = view.findViewById(R.id.todayDate);
        protein = view.findViewById(R.id.proteinBar);
        fat = view.findViewById(R.id.fatsBar);
        carb = view.findViewById(R.id.carbsBar);
        totalC = view.findViewById(R.id.totalC);
        totalP = view.findViewById(R.id.totalP);
        totalF = view.findViewById(R.id.totalF);
        workoutView = view.findViewById(R.id.workoutList);
        maxF = 100;
        maxC = 100;
        maxP = 100;

        mc= 0;
        mf=0;
        mp=0;


        String d = new SimpleDateFormat("M-dd-yyyy", Locale.getDefault()).format(new Date());
        String d1 = new SimpleDateFormat("M-d-yyyy", Locale.getDefault()).format(new Date());
        temp = d1.replaceAll("-", ".");
        date.setText(d);

        workoutView.setHasFixedSize(true);
        workoutView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        compExerAdapter = new CompExerDashAdapter(this.getContext(), list , this);
        workoutView.setAdapter(compExerAdapter);

        DocumentReference dR = firestore.collection("users").document(userID)
                .collection("goals").document("macro");
        dR.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){

                        long c = (long) documentSnapshot.get("carb");
                        long p = (long) documentSnapshot.get("protein");
                        long f = (long) documentSnapshot.get("fat");
                        int car = (int)c;
                        int protei = (int)p;
                        int fa = (int)f;
                        maxC = car;
                        maxF = fa;
                        maxP = protei;
                        mc = car;
                        mp = protei;
                        mf = fa;
                        carb.setMax(car);
                        fat.setMax(fa);
                        protein.setMax(protei);
                    }
                }
            }
        });


        DocumentReference documentReference = firestore.collection("users").document(userID).collection("macros").document(temp);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d("TAG", "macro file read");
                        long c = (long) document.get("carb");
                        long p = (long) document.get("protein");
                        long f = (long) document.get("fat");
                        int car = (int)c;
                        int protei = (int)p;
                        int fa = (int)f;
                        finalF = fa;
                        finalC = car;
                        finalP = protei;
                        carb.setProgress(finalC);
                        fat.setProgress(finalF);
                        protein.setProgress(finalP);
                        String cText = finalC + "/" + mc;
                        String pText = finalP + "/" + mp;
                        String fText = finalF + "/" + mf;
                        totalP.setText(pText);
                        totalF.setText(fText);
                        totalC.setText(cText);
                    }
                }
            }
        });



        firebaseFirestore.collection("users").document(userID)
                .collection("workouts").document(temp)
                .collection(temp).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d("TAG", "display called");
                        ArrayList<HashMap> arrayList = (ArrayList<HashMap>) doc
                                .get("list");
                        ArrayList<Set> setArrayList = new ArrayList<Set>();
                        for (HashMap hashMap : arrayList){
                            long r1 = (long) hashMap.get("reps");
                            long w1 = (long) hashMap.get("weight");
                            int r = (int)r1;
                            int w = (int)w1;
                            setArrayList.add(new Set(r, w));
                        }

                        String name = (String) doc.get("name");
                        CompExerDash compExer = new CompExerDash(name, setArrayList);
                        list.add(compExer);
                        compExerAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        //displayWorkouts();

        return view;
    }

    private void displayWorkouts() {
        Log.d("TAG", "display called");
        Log.d("TAG", String.valueOf(temp));
        String s = "5.02,2022";
        firebaseFirestore.collection("users").document(userID)
                .collection("workouts").document(s)
                .collection(s).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("Firestore error", error.getMessage());
                    return;
                }
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    Log.d("TAG", "display working");
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