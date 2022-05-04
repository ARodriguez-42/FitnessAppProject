package com.example.fitnessapp.ui.weight;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;
import com.example.fitnessapp.ui.gallery.Set;
import com.example.fitnessapp.ui.home.CompExerDash;
import com.example.fitnessapp.ui.macro.DisplayMacroActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WeightFragment extends Fragment {

    String temp;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    LineChart bwgraph, bfgraph;
    EditText bw, bf;
    TextView todayDate;
    Button update;
    ArrayList<Integer> weight, bodyFat;
    ArrayList<String> dates;


    private WeightViewModel mViewModel;

    public static WeightFragment newInstance() {
        return new WeightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_fragment, container, false);

        bwgraph = (LineChart) view.findViewById(R.id.bwgraph);
        bfgraph = (LineChart) view.findViewById(R.id.bfgraph);
        bw = view.findViewById(R.id.weightInput);
        bf = view.findViewById(R.id.bodyFatInput);
        todayDate = view.findViewById(R.id.todayDate);
        update = view.findViewById(R.id.update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        bodyFat = new ArrayList<>();
        weight = new ArrayList<>();
        dates = new ArrayList<>();

        String d1 = new SimpleDateFormat("M-d-yyyy", Locale.getDefault()).format(new Date());
        temp = d1.replaceAll("-", ".");
        todayDate.setText(temp);


        firebaseFirestore.collection("users").document(userID)
                .collection("weight").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d("TAG", "display called");
                        HashMap hashMap1 = (HashMap) doc.get("map");
                        double w = (double) hashMap1.get("w");
                        double bf = (double) hashMap1.get("bf");
                        int w1 = (int) w;
                        int bf1 = (int) bf;
                        dates.add((String) hashMap1.get("date"));
                        weight.add(w1);
                        bodyFat.add(bf1);
                    }
                }
            }
        });

        ArrayList<Entry> bfVals =new ArrayList<Entry>();
        ArrayList<Entry> wVals =new ArrayList<Entry>();

        bfVals.add(new Entry(0f, 10f));
        bfVals.add(new Entry(1f, 30f));
        bfVals.add(new Entry(2f, 40f));
        bfVals.add(new Entry(3f, 80f));
        bfVals.add(new Entry(4f, 100f));
        LineDataSet lineDataSet = new LineDataSet(bfVals, "Body Fat");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.BLUE);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData();
        bfgraph.setBackgroundColor(Color.BLACK);
        bfgraph.setData(data);
        bfgraph.notifyDataSetChanged();
        bfgraph.invalidate();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MActivity.class);
                startActivity(intent);
            }
        });

        /*
        int c = 0;
        int y = 0;

        for (int d : weight){
            wVals.add(new Entry(c, d));
            c++;
        }

        for (int i : bodyFat){
            bfVals.add(new Entry(y, i));
            y++;
        }

        LineDataSet bwLine = new LineDataSet(wVals, "Body Weight");
        bwLine.setDrawCircles(false);
        bwLine.setColor(Color.BLUE);

        LineDataSet bfLine = new LineDataSet(bfVals, "Body Fat");
        bfLine.setDrawCircles(false);
        bfLine.setColor(Color.BLUE);

        int n = 0;
        String s[] = new String[dates.size()];
        for(String h : dates){
            s[n] = h;
        }


        bwgraph.setData(new LineData(bwLine));
        bwgraph.invalidate();



        bfgraph.setData(new LineData(bwLine));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String q = bw.getText().toString();
                String w = bf.getText().toString();
                int t = Integer.parseInt(q);
                int r = Integer.parseInt(w);
                bodyFat.add(r);
                weight.add(t);
                dates.add(temp);
            }
        });

         */

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeightViewModel.class);
        // TODO: Use the ViewModel
    }

}