package com.example.fitnessapp.ui.weight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitnessapp.R;
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

public class MActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mactivity);

        bwgraph = (LineChart) findViewById(R.id.bwgraph);
        bfgraph = (LineChart) findViewById(R.id.bfgraph);
        bw = findViewById(R.id.weightInput);
        bf = findViewById(R.id.bodyFatInput);
        todayDate = findViewById(R.id.todayDate);
        update = findViewById(R.id.update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        bodyFat = new ArrayList<>();
        weight = new ArrayList<>();
        dates = new ArrayList<>();

        String d1 = new SimpleDateFormat("M-d-yyyy", Locale.getDefault()).format(new Date());
        temp = d1.replaceAll("-", ".");





        ArrayList<Entry> bfVals =new ArrayList<Entry>();


        bfVals.add(new Entry(0, 10));
        bfVals.add(new Entry(1, 30));
        bfVals.add(new Entry(2, 40));
        bfVals.add(new Entry(3, 80));
        bfVals.add(new Entry(4, 100));
        LineDataSet lineDataSet = new LineDataSet(bfVals, "Body Fat");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData();
        bfgraph.setBackgroundColor(Color.WHITE);
        bfgraph.setData(data);
        bfgraph.invalidate();



    }
}