package com.example.fitnessapp.ui.gallery;

import java.io.Serializable;

public class Set implements Serializable {

    int weight, reps;

    public Set(int reps, int weight) {
        this.weight = weight;
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

}
