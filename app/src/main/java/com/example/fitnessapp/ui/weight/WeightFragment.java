package com.example.fitnessapp.ui.weight;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.example.fitnessapp.ui.gallery.CompExer;
import com.example.fitnessapp.ui.gallery.DisplayWorkout;
import com.example.fitnessapp.ui.gallery.Set;
import com.example.fitnessapp.ui.home.CompExerDash;
import com.example.fitnessapp.ui.macro.DisplayMacroActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
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
    ArrayList<Float> weight, bodyFat;
    ArrayList<String> dates;
    ArrayList<Entry> bfVals;
    ArrayList<Entry> bwVals;
    int count = 0;
    float size = 0;


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
        bfVals =new ArrayList<Entry>();
        bwVals =new ArrayList<Entry>();

        String d1 = new SimpleDateFormat("M-d-yyyy", Locale.getDefault()).format(new Date());
        temp = d1.replaceAll("-", ".");
        todayDate.setText(temp);


        firebaseFirestore.collection("users").document(userID)
                .collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    float f = (float) count;
                    String sw = String.valueOf(documentChange.getDocument().get("w"));
                    String bfw = String.valueOf(documentChange.getDocument().get("bf"));
                    float w = Float.parseFloat(sw);
                    float bf = Float.parseFloat(bfw);
                    Log.d("TAG weight", String.valueOf(w));
                    bfVals.add(new Entry(f, bf));
                    bwVals.add(new Entry(f, w));
                    dates.add((String) documentChange.getDocument().get("date"));
                    weight.add(w);
                    bodyFat.add(bf);
                    Log.d("TAG weight list", String.valueOf(weight));
                    count++;
                    size++;
                    updateChart(bwVals,bfVals);
                }
            }

        });



        LineDataSet bwDataSet = new LineDataSet(bwVals, "Body Weight");
        bwDataSet.setDrawCircles(true);
        bwDataSet.setLineWidth(4f);
        bwDataSet.setColor(Color.YELLOW);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(bwDataSet);
        LineData wDate = new LineData(bwDataSet);
        bwgraph.setBackgroundColor(Color.WHITE);
        bwgraph.getDescription().setEnabled(true);
        bwgraph.getDescription().setText("Chart 1");
        YAxis leftAxis = bwgraph.getAxisLeft();
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(400f);
        leftAxis.setAxisMinimum(0f);
        XAxis xAxis = bwgraph.getXAxis();
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(20f);
        xAxis.setAxisMinimum(0f);
        bwgraph.setData(wDate);
        bwgraph.notifyDataSetChanged();
        bwgraph.invalidate();

        LineDataSet bfDataSet = new LineDataSet(bfVals, "Body Fat");
        bfDataSet.setDrawCircles(true);
        bfDataSet.setLineWidth(4f);
        bfDataSet.setColor(Color.YELLOW);
        ArrayList<ILineDataSet> bfDataSets = new ArrayList<>();
        bfDataSets.add(bfDataSet);
        LineData bfData = new LineData(bfDataSets);
        bfgraph.setBackgroundColor(Color.WHITE);
        bfgraph.getDescription().setEnabled(true);
        bfgraph.getDescription().setText("Chart 1");
        YAxis leftAxis1 = bfgraph.getAxisLeft();
        leftAxis1.setTextColor(Color.BLUE);
        leftAxis1.setDrawGridLines(false);
        leftAxis1.setAxisMaximum(60f);
        leftAxis1.setAxisMinimum(0f);
        XAxis xAxis1 = bfgraph.getXAxis();
        xAxis1.setTextColor(Color.BLUE);
        xAxis1.setDrawGridLines(false);
        xAxis1.setAxisMaximum(20f);
        xAxis1.setAxisMinimum(0f);
        bfgraph.setData(bfData);
        bfgraph.notifyDataSetChanged();
        bfgraph.invalidate();



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(bw.getText().toString()) || TextUtils.isEmpty(bf.getText().toString())){
                    Context context = getContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "Please Enter a Value for Both Inputs", duration);
                    toast.show();
                }
                else {
                    HashMap hashMap = new HashMap();
                    float w = Float.parseFloat(bw.getText().toString());
                    float bf1 = Float.parseFloat(bf.getText().toString());
                    hashMap.put("w", w);
                    hashMap.put("bf", bf1);
                    hashMap.put("date", temp);
                    firebaseFirestore.collection("users").document(userID)
                            .collection("weight").document(temp).set(hashMap);
                    if(dates.contains(temp)){

                        bwVals.set(weight.size()-1, new Entry(weight.size()-1, w));
                        bfVals.set(weight.size()-1, new Entry(weight.size()-1, bf1));

                    }
                    else {

                        bwVals.add(new Entry(weight.size(), w));
                        bfVals.add(new Entry(weight.size(), bf1));
                    }
                    updateChart(bwVals,bfVals);
                }


            }
        });

        return view;
    }

    private void updateChart(ArrayList<Entry> weight, ArrayList<Entry> bodyFat) {

        LineDataSet bwDataSet = new LineDataSet(weight, "Body Weight");
        bwDataSet.setDrawCircles(true);
        bwDataSet.setLineWidth(4f);
        bwDataSet.setColor(Color.YELLOW);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(bwDataSet);
        LineData wDate = new LineData(bwDataSet);
        bwgraph.setBackgroundColor(Color.WHITE);
        bwgraph.getDescription().setEnabled(true);
        bwgraph.getDescription().setText("Chart 1");
        YAxis leftAxis = bwgraph.getAxisLeft();
        leftAxis.setTextColor(Color.BLUE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(400f);
        leftAxis.setAxisMinimum(0f);
        XAxis xAxis = bwgraph.getXAxis();
        xAxis.setTextColor(Color.BLUE);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(size);
        xAxis.setAxisMinimum(0f);
        bwgraph.setData(wDate);
        bwgraph.notifyDataSetChanged();
        bwgraph.invalidate();

        LineDataSet bfDataSet = new LineDataSet(bodyFat, "Body Fat");
        bfDataSet.setDrawCircles(true);
        bfDataSet.setLineWidth(4f);
        bfDataSet.setColor(Color.YELLOW);
        ArrayList<ILineDataSet> bfDataSets = new ArrayList<>();
        bfDataSets.add(bfDataSet);
        LineData bfData = new LineData(bfDataSets);
        bfgraph.setBackgroundColor(Color.WHITE);
        bfgraph.getDescription().setEnabled(true);
        bfgraph.getDescription().setText("Chart 1");
        YAxis leftAxis1 = bfgraph.getAxisLeft();
        leftAxis1.setTextColor(Color.BLUE);
        leftAxis1.setDrawGridLines(false);
        leftAxis1.setAxisMaximum(60f);
        leftAxis1.setAxisMinimum(0f);
        XAxis xAxis1 = bfgraph.getXAxis();
        xAxis1.setTextColor(Color.BLUE);
        xAxis1.setDrawGridLines(false);
        xAxis1.setAxisMaximum(size);
        xAxis1.setAxisMinimum(0f);
        bfgraph.setData(bfData);
        bfgraph.notifyDataSetChanged();
        bfgraph.invalidate();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeightViewModel.class);
        // TODO: Use the ViewModel
    }

}