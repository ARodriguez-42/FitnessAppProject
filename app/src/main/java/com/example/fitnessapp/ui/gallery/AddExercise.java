package com.example.fitnessapp.ui.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.fitnessapp.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.RecyclerViewInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddExercise extends AppCompatActivity implements RecyclerViewInterface {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AddExerciseAdapter addExerciseAdapter;
    ArrayList<Exercise> list;
    String d;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.addExerciseList);
        databaseReference = FirebaseDatabase.getInstance().getReference("Exercises");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        d = getIntent().getExtras().getString("date");

        list = new ArrayList<Exercise>();
        addExerciseAdapter = new AddExerciseAdapter(this, this, list);
        recyclerView.setAdapter(addExerciseAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Exercise exercise = dataSnapshot.getValue(Exercise.class);
                    list.add(exercise);

                }

                addExerciseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(AddExercise.this, AddSets.class);
        intent.putExtra("exerciseName", list.get(position).getExerciseName());
        intent.putExtra("date", d);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                addExerciseAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                addExerciseAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}