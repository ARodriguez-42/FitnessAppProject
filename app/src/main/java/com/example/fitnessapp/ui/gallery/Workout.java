package com.example.fitnessapp.ui.gallery;

public class Workout {

    String name, mg;

    public Workout(String name, String mg) {
        this.name = name;
        this.mg = mg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }
}
