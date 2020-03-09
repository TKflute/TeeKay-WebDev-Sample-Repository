package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.simplenutritiontracker.models.FoodGroups;
import com.example.simplenutritiontracker.models.Macros;
import com.example.simplenutritiontracker.models.Meal;
import com.example.simplenutritiontracker.sqlite.SQLFoodGroupsDataAccess;
import com.example.simplenutritiontracker.sqlite.SQLMacrosDataAccess;
import com.example.simplenutritiontracker.sqlite.SQLMealDataAccess;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //ListView sportsView;
    public static final String TAG = "MainActivity";
    //MAY WANT TO MAKE AN INTERFACE - but would you make an interface for two different da classes for two tables?
    SQLMacrosDataAccess mda;
    ArrayList<Macros> allMacros;

    SQLMealDataAccess meda;
    ArrayList<Meal> allMeals;

    SQLFoodGroupsDataAccess fgda;
    ArrayList<FoodGroups> allFoodGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //CODE FOR TESTING MACROS DA
        mda = new SQLMacrosDataAccess(this);

        Log.d(TAG, "INSERT...");
        Macros ma = new Macros(1920, 100, 200, 80, new Date(119, 11, 06));
        mda.insertMacros(ma);
        Log.d(TAG, ma.toString());

        Log.d(TAG, "GET ALL");
        showAllMacros();


/*
        //after we insert a Macros obj, will get it by its id
        Log.d(TAG, "GET BY ID");
        ma = mda.getMacrosById(ma.getId());
        Log.d(TAG, ma.toString());

        Log.d(TAG, "UPDATE");
        ma.setCalorieIntake(2100);
        ma.setProteinMacros(120);
        ma.setCarbMacros(225);
        mda.updateMacros(ma);
        showAllMacros();


        Log.d(TAG, "DELETE");
        int rowsDeleted = mda.deleteMacros(ma);
        Log.d(TAG, "ROWS DELETED " + rowsDeleted);

        showAllMacros();
        */


        //CODE FOR TESTING MEALS DA
        meda = new SQLMealDataAccess(this);

        Log.d(TAG, "INSERT...");
        Meal m = new Meal("Overnight oats", "Peanut butter", "oats and banana", "peanut butter", new Date(119, 11, 06));
        meda.insertMeal(m);
        Meal m2 = new Meal("Chicken salad", "Chicken", "quinoa", "salad dressing", new Date(119, 11, 06));
        meda.insertMeal(m2);
        Meal m3 = new Meal("Turkey burger, fries, vegetables", "Turkey", "potato and bun", "cheese", new Date(119, 11, 06));
        meda.insertMeal(m3);
       // Log.d(TAG, m.toString());

        Log.d(TAG, "GET ALL");
        showAllMeals();
        /*
        Log.d(TAG, "GET BY ID");
        m = meda.getMealById(m.getId());
        Log.d(TAG, m.toString());

        Log.d(TAG, "UPDATE");
        m.setMealDescription("Fish with potatoes");
        m.setProteinSource("fish");
        m.setCarbSource("potato");
        m.setFatSource("butter");
        //m.setMealDate(new Date(119, 11, 07));
        meda.updateMeal(m);
        showAllMeals();



        Log.d(TAG, "GET BY DATE");
        ArrayList<Meal> mealDateList = meda.getMealByDate("12/6/2019");
        for(Meal meal : mealDateList){
            Log.d(TAG, meal.toString());
        }


        Log.d(TAG, "DELETE");
        int mealRowsDeleted = meda.deleteMeal(m);
        Log.d(TAG, "ROWS DELETED " + mealRowsDeleted);

        showAllMeals();

        */
        //CODE FOR TESTING FOOD GROUPS DA
        fgda = new SQLFoodGroupsDataAccess(this);
        Log.d(TAG, "INSERT...");
        FoodGroups f = new FoodGroups(2, 2, 2, 2, 2, 2, 2, 2, 64, 3, new Date(119, 11, 06));
        fgda.insertFoodGroups(f);
        Log.d(TAG, f.toString());

        Log.d(TAG, "GET ALL");
        showAllFoodGroups();
        /*
        Log.d(TAG, "GET BY ID");
        f = fgda.getFoodGroupsById(f.getId());
        Log.d(TAG, f.toString());

        Log.d(TAG, "UPDATE");
        f.setGrainServings(3);
        f.setBeanServings(3);
        f.setVegetableServings(3);
        f.setFruitServings(3);
        f.setMeatFishEggsServings(3);
        f.setDairyServings(3);
        f.setDessertFoodsServings(3);
        f.setWaterOunces(80);
        f.setCupsOfCoffee(4);
        fgda.updateFoodGroups(f);
        showAllFoodGroups();

        /*
        Log.d(TAG, "DELETE");
        int foodGroupsRowsDeleted = fgda.deleteFoodGroups(f);
        Log.d(TAG, "ROWS DELETED " + foodGroupsRowsDeleted);

        showAllFoodGroups();
        */

    }//end of onCreate()

    private void showAllMacros(){
        Log.d(TAG, "Showing all macros.........");
        allMacros = mda.getAllMacros();

        for(Macros m : allMacros){
            Log.d(TAG, m.toString());
        }
    }

    private void showAllMeals(){
        Log.d(TAG, "Showing all meals.........");
        allMeals = meda.getAllMeals();

        for(Meal m : allMeals){
            Log.d(TAG, m.toString());
        }
    }

    private void showAllFoodGroups(){
        Log.d(TAG, "Showing all food groups.........");
        allFoodGroups = fgda.getAllFoodGroups();

        for(FoodGroups f : allFoodGroups){
            Log.d(TAG, f.toString());
        }
    }
}
