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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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

        bfVals.add(new Entry(0f, 10f));
        bfVals.add(new Entry(10f, 30f));
        bfVals.add(new Entry(20f, 40f));
        bfVals.add(new Entry(30f, 80f));
        bfVals.add(new Entry(40f, 100f));
        LineDataSet lineDataSet = new LineDataSet(bfVals, "Body Fat");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setLineWidth(4f);
        lineDataSet.setColor(Color.BLUE);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        bfgraph.setBackgroundColor(Color.WHITE);
        bfgraph.getDescription().setEnabled(true);
        bfgraph.getDescription().setText("Chart 1");
        YAxis leftAxis = bfgraph.getAxisLeft();
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        XAxis xAxis = bfgraph.getXAxis();
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(100f);
        xAxis.setAxisMinimum(0f);


        bfgraph.setData(data);
        bfgraph.notifyDataSetChanged();
        bfgraph.invalidate();



    }
}