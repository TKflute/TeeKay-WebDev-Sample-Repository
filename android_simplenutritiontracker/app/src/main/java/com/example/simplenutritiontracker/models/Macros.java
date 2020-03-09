package com.example.simplenutritiontracker.models;

import java.util.Date;

public class Macros {

    private long id;

    private int calorieIntake;
    private int proteinMacros;
    private int carbMacros;
    private int fatMacros;
    private Date macrosDate;

    public Macros(int calorieIntake, int proteinMacros, int carbMacros, int fatMacros, Date macrosDate) {
        this.calorieIntake = calorieIntake;
        this.proteinMacros = proteinMacros;
        this.carbMacros = carbMacros;
        this.fatMacros = fatMacros;
        this.macrosDate = macrosDate;
    }

    public Macros(long id, int calorieIntake, int proteinMacros, int carbMacros, int fatMacros, Date macrosDate) {
        this(calorieIntake, proteinMacros, carbMacros, fatMacros, macrosDate);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(int calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    public int getProteinMacros() {
        return proteinMacros;
    }

    public void setProteinMacros(int proteinMacros) {
        this.proteinMacros = proteinMacros;
    }


    public int getCarbMacros() {
        return carbMacros;
    }

    public void setCarbMacros(int carbMacros) {
        this.carbMacros = carbMacros;
    }

    public int getFatMacros() {
        return fatMacros;
    }

    public void setFatMacros(int fatMacros) {
        this.fatMacros = fatMacros;
    }

    @Override
    public String toString(){
        return String.format("ID: %s CALORIES: %s PROTEIN: %s CARB: %s FAT: %s DATE: %s", id, calorieIntake, proteinMacros, carbMacros, fatMacros, macrosDate);
    }

    public Date getMacrosDate() {
        return macrosDate;
    }

    public void setMacrosDate(Date macrosDate) {
        this.macrosDate = macrosDate;
    }
}
