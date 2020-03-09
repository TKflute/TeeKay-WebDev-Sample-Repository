package com.example.simplenutritiontracker.models;

import java.util.Date;

public class Meal {

    private long id;
    private String mealDescription;
    private String proteinSource;
    private String carbSource;
    private String fatSource;
    private Date mealDate;

    public Meal(String mealDescription, String proteinSource, String carbSource, String fatSource, Date mealDate) {

        this.mealDescription = mealDescription;
        this.proteinSource = proteinSource;
        this.carbSource = carbSource;
        this.fatSource = fatSource;
        this.mealDate = mealDate;
    }

    public Meal(long id, String mealDescription, String proteinSource, String carbSource, String fatSource, Date mealDate) {

        this(mealDescription, proteinSource, carbSource, fatSource, mealDate);
        this.id = id;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getProteinSource() {
        return proteinSource;
    }

    public void setProteinSource(String proteinSource) {
        this.proteinSource = proteinSource;
    }

    public String getCarbSource() {
        return carbSource;
    }

    public void setCarbSource(String carbSource) {
        this.carbSource = carbSource;
    }

    public String getFatSource() {
        return fatSource;
    }

    public void setFatSource(String fatSource) {
        this.fatSource = fatSource;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    @Override
    public String toString(){
        return String.format("ID: %d MEAL DESCRIPTION: %s PROTEIN SOURCE: %s CARB SOURCE: %s FAT SOURCE: %s MEAL DATE: %s", id, mealDescription, proteinSource, carbSource, fatSource, mealDate);
    }

}
