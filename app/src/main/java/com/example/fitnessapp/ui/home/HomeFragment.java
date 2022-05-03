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
    int maxF, maxC, maxP;
    ProgressBar protein, fat, carb;
    EditText date;
    RecyclerView workoutView;
    TextView totalC, totalF, totalP;
    int finalF, finalC, finalP;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
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

        DocumentReference dR = firestore.collection("users").document(userID)
                .collection("goals").document("macro");
        dR.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d("TAG", "Read goals");
                        long c = (long) documentSnapshot.get("carb");
                        long p = (long) documentSnapshot.get("protein");
                        long f = (long) documentSnapshot.get("fat");
                        int car = (int)c;
                        int protei = (int)p;
                        int fa = (int)f;
                        maxC = car;
                        maxF = fa;
                        maxP = protei;
                    }
                }
            }
        });



        String d = new SimpleDateFormat("M-dd-yyyy", Locale.getDefault()).format(new Date());

        temp = d.replaceAll("-", ".");

        DocumentReference documentReference = firestore.collection("users").document(userID).collection("macros").document(temp);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d("TAG", "Read macros");
                        long c = (long) documentSnapshot.get("carb");
                        long p = (long) documentSnapshot.get("protein");
                        long f = (long) documentSnapshot.get("fat");
                        int car = (int)c;
                        int protei = (int)p;
                        int fa = (int)f;
                        finalF = fa;
                        finalC = car;
                        finalP = protei;
                        carb.setProgress(finalC);
                        fat.setProgress(finalF);
                        protein.setProgress(finalP);
                        String cText = finalC + "/" + maxC;
                        String pText = finalF + "/" + maxP;
                        String fText = finalP + "/" + maxF;
                        totalP.setText(pText);
                        totalF.setText(fText);
                        totalC.setText(cText);
                    }
                }
            }
        });

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
        Log.d("TAG", "display called");
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
                        Log.d("TAG", "Read workout");
                        ArrayList<HashMap> arrayList = (ArrayList<HashMap>) documentChange.getDocument().get("list");
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