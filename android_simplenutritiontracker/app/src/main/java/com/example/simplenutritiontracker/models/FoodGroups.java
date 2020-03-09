package com.example.simplenutritiontracker.models;

import java.util.ArrayList;
import java.util.Date;

public class FoodGroups {

    private long id;

    private int grainServings;
    private int beanServings;
    private int vegetableServings;
    private int fruitServings;
    private int meatFishEggsServings;
    private int dairyServings;
    private int nutsAndSeedsServings;
    private int dessertFoodsServings;

    private int waterOunces;
    private int cupsOfCoffee;
    private Date foodGroupDate;

    public FoodGroups(int grainServings, int beanServings, int vegetableServings, int fruitServings,
                     int meatFishEggsServings, int dairyServings, int nutsAndSeedsServings,
                     int dessertFoodsServings, int waterOunces, int cupsOfCoffee, Date foodGroupDate) {

        this.grainServings = grainServings;
        this.beanServings = beanServings;
        this.vegetableServings = vegetableServings;
        this.fruitServings = fruitServings;
        this.meatFishEggsServings = meatFishEggsServings;
        this.dairyServings = dairyServings;
        this.nutsAndSeedsServings = nutsAndSeedsServings;
        this.dessertFoodsServings = dessertFoodsServings;
        this.waterOunces = waterOunces;
        this.cupsOfCoffee = cupsOfCoffee;
        this.foodGroupDate = foodGroupDate;
    }

    public FoodGroups(long id, int grainServings, int beanServings, int vegetableServings, int fruitServings,
                     int meatFishEggsServings, int dairyServings, int nutsAndSeedsServings,
                     int dessertFoodsServings, int waterOunces, int cupsOfCoffee, Date foodGroupDate) {

        this(grainServings, beanServings, vegetableServings, fruitServings, meatFishEggsServings, dairyServings, nutsAndSeedsServings, dessertFoodsServings, waterOunces, cupsOfCoffee, foodGroupDate);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGrainServings() {
        return grainServings;
    }

    public void setGrainServings(int grainServings) {
        this.grainServings = grainServings;
    }

    public int getBeanServings() {
        return beanServings;
    }

    public void setBeanServings(int beanServings) {
        this.beanServings = beanServings;
    }

    public int getVegetableServings() {
        return vegetableServings;
    }

    public void setVegetableServings(int vegetableServings) {
        this.vegetableServings = vegetableServings;
    }

    public int getFruitServings() {
        return fruitServings;
    }

    public void setFruitServings(int fruitServings) {
        this.fruitServings = fruitServings;
    }

    public int getMeatFishEggsServings() {
        return meatFishEggsServings;
    }

    public void setMeatFishEggsServings(int meatFishEggsServings) {
        this.meatFishEggsServings = meatFishEggsServings;
    }

    public int getDairyServings() {
        return dairyServings;
    }

    public void setDairyServings(int dairyServings) {
        this.dairyServings = dairyServings;
    }

    public int getNutsAndSeedsServings() {
        return nutsAndSeedsServings;
    }

    public void setNutsAndSeedsServings(int nutsAndSeedsServings) {
        this.nutsAndSeedsServings = nutsAndSeedsServings;
    }

    public int getDessertFoodsServings() {
        return dessertFoodsServings;
    }

    public void setDessertFoodsServings(int dessertFoodsServings) {
        this.dessertFoodsServings = dessertFoodsServings;
    }

    public int getWaterOunces() {
        return waterOunces;
    }

    public void setWaterOunces(int waterOunces) {
        this.waterOunces = waterOunces;
    }

    public int getCupsOfCoffee() {
        return cupsOfCoffee;
    }

    public void setCupsOfCoffee(int cupsOfCoffee) {
        this.cupsOfCoffee = cupsOfCoffee;
    }

    public Date getFoodGroupDate() {
        return foodGroupDate;
    }

    public void setFoodGroupDate(Date foodGroupDate) {
        this.foodGroupDate = foodGroupDate;
    }

    @Override
    public String toString(){
        return String.format("ID: %d GRAIN SERVINGS: %s BEAN SERVINGS: %s VEGETABLE SERVINGS: %s FRUIT SERVINGS: %s " +
                            "MEATFISHEGGS SERVINGS: %s DAIRY SERVINGS: %s NUTSSEEDS SERVINGS: %s DESSERT SERVINGS %s " +
                            "WATER OUNCES: %s CUPS COFFEE:%s FOOD GROUP DATE: %s", id, grainServings, beanServings, vegetableServings, fruitServings,
                             meatFishEggsServings, dairyServings, nutsAndSeedsServings, dessertFoodsServings, waterOunces, cupsOfCoffee, foodGroupDate);
    }

    public ArrayList<String> returnFoodGroupData(){
        ArrayList<String> foodGroupData = new ArrayList<>();
        foodGroupData.add ("Grains" + "-" + getGrainServings());
        foodGroupData.add ("Beans" + "-" + getBeanServings());
        foodGroupData.add ("Vegetables" + "-" + getVegetableServings());
        foodGroupData.add ("Fruits" + "-" + getFruitServings());
        foodGroupData.add ("Meats" + "-" + getMeatFishEggsServings());
        foodGroupData.add ("Dairy" + "-" + getDairyServings());
        foodGroupData.add ("Nuts" + "-" + getNutsAndSeedsServings());
        foodGroupData.add ("Dessert" + "-" + getDessertFoodsServings());
        foodGroupData.add ("Water ounces" + "-" + getWaterOunces());
        foodGroupData.add ("Cups coffee" + "-" + getCupsOfCoffee());

        return foodGroupData;
    }

}
