package com.example.fitnessapp.ui.macro;

public class Meal {

    String name;
    int c, p, f;

    public Meal(String name, int c, int p, int f) {
        this.name = name;
        this.c = c;
        this.p = p;
        this.f = f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
