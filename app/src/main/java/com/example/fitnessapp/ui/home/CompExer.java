package com.example.fitnessapp.ui.home;

import java.util.ArrayList;

public class CompExer {

    String date;
    String name;
    ArrayList<Set> list;

    public CompExer(String date, String name, ArrayList<Set> list) {
        this.date = date;
        this.name = name;
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String displayExercises(){
        ArrayList<Set> compList = this.list;
        String s = "";
        for (Set set: compList){
            String temp = "Reps: " + set.getReps() + " Weight Used: " + set.getWeight() + "; ";
        }
        if (s.length() > 0){
            s = s.substring(0, s.length() -1);
        }
        return s;
    }
}
