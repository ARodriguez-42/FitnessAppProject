package com.example.fitnessapp.ui.home;

import com.example.fitnessapp.ui.gallery.Set;

import java.util.ArrayList;

public class CompExerDash {
    String name;
    ArrayList<Set> list;

    public CompExerDash(String name, ArrayList<Set> list) {

        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Set> getList() {
        return list;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(ArrayList<Set> list) {
        this.list = list;
    }

    public String displayExercises(ArrayList<Set> list){
        ArrayList<Set> compList = list;
        String s = "";
        for (Set set: compList){
            String temp = "Reps: " + set.getReps() + "      Weight Used: " + set.getWeight() + "; ";
            s = s + System.lineSeparator()  + temp;
        }
        if (s.length() > 0){
            s = s.substring(0, s.length() -1);
        }
        return s;
    }
}
