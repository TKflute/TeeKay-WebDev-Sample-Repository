package com.example.simplenutritiontracker.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.simplenutritiontracker.models.FoodGroups;
import com.example.simplenutritiontracker.models.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLFoodGroupsDataAccess {

    public static final String TAG = "SQLFoodGroupsDataAccess";

    private Context context;
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

    // Constructors
    public SQLFoodGroupsDataAccess(Context context){
        Log.d(TAG, "Instantiating SQLFoodGroupsDataAccess");
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
    }

    public static final String TABLE_NAME = "foodGroups";
    public static final String COLUMN_FOOD_GROUPS_ID = "_id";
    public static final String  COLUMN_GRAIN_SERVINGS = "grainServings";
    public static final String  COLUMN_BEAN_SERVINGS = "beanServings";
    public static final String  COLUMN_VEGETABLE_SERVINGS = "vegetableServings";
    public static final String  COLUMN_FRUIT_SERVINGS = "fruitServings";
    public static final String  COLUMN_MEAT_FISH_EGGS_SERVINGS = "meatFishEggsServings";
    public static final String  COLUMN_DAIRY_SERVINGS = "dairyServings";
    public static final String  COLUMN_NUTS_SEEDS_SERVINGS = "nutsSeedsServings";
    public static final String  COLUMN_DESSERT_SERVINGS = "dessertServings";
    public static final String  COLUMN_WATER_OUNCES = "waterOunces";
    public static final String  COLUMN_CUPS_COFFEE = "cupsCoffee";
    //SHOULD ALL DATE COLUMNS JUST BE NAMED 'DATE'?
    public static final String  COLUMN_FOOD_GROUPS_DATE = "foodGroupsDate";

    public static final String TABLE_CREATE = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, " +
                                                            "%s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, " +
                                                            "%s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s DATETIME)",
            TABLE_NAME,
            COLUMN_FOOD_GROUPS_ID,
            COLUMN_GRAIN_SERVINGS,
            COLUMN_BEAN_SERVINGS,
            COLUMN_VEGETABLE_SERVINGS,
            COLUMN_FRUIT_SERVINGS,
            COLUMN_MEAT_FISH_EGGS_SERVINGS,
            COLUMN_DAIRY_SERVINGS,
            COLUMN_NUTS_SEEDS_SERVINGS,
            COLUMN_DESSERT_SERVINGS,
            COLUMN_WATER_OUNCES,
            COLUMN_CUPS_COFFEE,
            COLUMN_FOOD_GROUPS_DATE
    );

    public ArrayList<FoodGroups> getAllFoodGroups() {

        ArrayList<FoodGroups> allFoodGroups = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s FROM %s",
                                    COLUMN_FOOD_GROUPS_ID, COLUMN_GRAIN_SERVINGS, COLUMN_BEAN_SERVINGS, COLUMN_VEGETABLE_SERVINGS, COLUMN_FRUIT_SERVINGS, COLUMN_MEAT_FISH_EGGS_SERVINGS, COLUMN_DAIRY_SERVINGS,
                                    COLUMN_NUTS_SEEDS_SERVINGS, COLUMN_DESSERT_SERVINGS, COLUMN_WATER_OUNCES, COLUMN_CUPS_COFFEE, COLUMN_FOOD_GROUPS_DATE, TABLE_NAME);

        Cursor c = database.rawQuery(query, null);

        // make sure we got some results from the db before processing them
        if((c != null) && (c.getCount() > 0)) {

            c.moveToFirst();

            while (!c.isAfterLast()) {

                //have to know data type of all the columns
                long id = c.getLong(0);
                int grainServings = c.getInt(1);
                int beanServings = c.getInt(2);
                int vegetableServings = c.getInt(3);
                int fruitServings = c.getInt(4);
                int meatFishEggsServings = c.getInt(5);
                int dairyServings = c.getInt(6);
                int nutsSeedsServings = c.getInt(7);
                int dessertServings = c.getInt(8);
                int waterOunces = c.getInt(9);
                int cupsCoffee = c.getInt(10);
                Date foodGroupDate = null;

                try {
                    //DON'T KNOW IF THIS WILL WORK
                    foodGroupDate = dateFormat.parse(c.getString(11));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                FoodGroups foodGroups = new FoodGroups(id, grainServings, beanServings,
                        vegetableServings, fruitServings, meatFishEggsServings, dairyServings, nutsSeedsServings,
                        dessertServings, waterOunces, cupsCoffee, foodGroupDate);
                allFoodGroups.add(foodGroups);
                c.moveToNext(); // DONT FORGET THIS LINE!!!!!!
            }
            c.close();
        }
        return allFoodGroups;
    }

    public FoodGroups getFoodGroupsById(long id) {

        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s " +
                        "FROM %s WHERE %s = %d", COLUMN_FOOD_GROUPS_ID, COLUMN_GRAIN_SERVINGS, COLUMN_BEAN_SERVINGS, COLUMN_VEGETABLE_SERVINGS,
                        COLUMN_FRUIT_SERVINGS, COLUMN_MEAT_FISH_EGGS_SERVINGS, COLUMN_DAIRY_SERVINGS, COLUMN_NUTS_SEEDS_SERVINGS, COLUMN_DESSERT_SERVINGS,
                        COLUMN_WATER_OUNCES, COLUMN_CUPS_COFFEE, COLUMN_FOOD_GROUPS_DATE, TABLE_NAME, COLUMN_FOOD_GROUPS_ID, id);

        Log.d(TAG, query);
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();

        id = c.getLong(0);
        int grainServings = c.getInt(1);
        int beanServings = c.getInt(2);
        int vegetableServings = c.getInt(3);
        int fruitServings = c.getInt(4);
        int meatFishEggsServings = c.getInt(5);
        int dairyServings = c.getInt(6);
        int nutsSeedsServings = c.getInt(7);
        int dessertServings = c.getInt(8);
        int waterOunces = c.getInt(9);
        int cupsCoffee = c.getInt(10);
        Date foodGroupDate = null;

        try {
            //DON'T KNOW IF THIS WILL WORK
            foodGroupDate = dateFormat.parse(c.getString(11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.close();

        FoodGroups foodGroups = new FoodGroups(id, grainServings, beanServings, vegetableServings, fruitServings, meatFishEggsServings, dairyServings, nutsSeedsServings,
                dessertServings, waterOunces, cupsCoffee, foodGroupDate);
        return foodGroups;
    }

    public FoodGroups insertFoodGroups(FoodGroups foodGroups) {

        //like a hashmap
        ContentValues values = new ContentValues();
        //NOT SURE IF STRINGS WILL WORK SAME WAY AS INTS HERE
        values.put(COLUMN_GRAIN_SERVINGS, foodGroups.getGrainServings());
        values.put(COLUMN_BEAN_SERVINGS, foodGroups.getBeanServings());
        values.put(COLUMN_VEGETABLE_SERVINGS, foodGroups.getVegetableServings());
        values.put(COLUMN_FRUIT_SERVINGS, foodGroups.getFruitServings());
        values.put(COLUMN_MEAT_FISH_EGGS_SERVINGS, foodGroups.getMeatFishEggsServings());
        values.put(COLUMN_DAIRY_SERVINGS, foodGroups.getDairyServings());
        values.put(COLUMN_NUTS_SEEDS_SERVINGS, foodGroups.getNutsAndSeedsServings());
        values.put(COLUMN_DESSERT_SERVINGS, foodGroups.getDessertFoodsServings());
        values.put(COLUMN_WATER_OUNCES, foodGroups.getWaterOunces());
        values.put(COLUMN_CUPS_COFFEE, foodGroups.getCupsOfCoffee());
        //DON'T KNOW IF THIS IS RIGHT
        values.put(COLUMN_FOOD_GROUPS_DATE, dateFormat.format(foodGroups.getFoodGroupDate()));

        long insertId = database.insert(TABLE_NAME, null, values);
        // note: insertId will be -1 if the insert failed

        //set id on Macros object
        foodGroups.setId(insertId);
        return foodGroups;
    }

    public FoodGroups updateFoodGroups(FoodGroups foodGroups) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_GRAIN_SERVINGS, foodGroups.getGrainServings());
        values.put(COLUMN_BEAN_SERVINGS, foodGroups.getBeanServings());
        values.put(COLUMN_VEGETABLE_SERVINGS, foodGroups.getVegetableServings());
        values.put(COLUMN_FRUIT_SERVINGS, foodGroups.getFruitServings());
        values.put(COLUMN_MEAT_FISH_EGGS_SERVINGS, foodGroups.getMeatFishEggsServings());
        values.put(COLUMN_DAIRY_SERVINGS, foodGroups.getDairyServings());
        values.put(COLUMN_NUTS_SEEDS_SERVINGS, foodGroups.getNutsAndSeedsServings());
        values.put(COLUMN_DESSERT_SERVINGS, foodGroups.getDessertFoodsServings());
        values.put(COLUMN_WATER_OUNCES, foodGroups.getWaterOunces());
        values.put(COLUMN_CUPS_COFFEE, foodGroups.getCupsOfCoffee());
        //DON'T KNOW IF THIS IS RIGHT
        values.put(COLUMN_FOOD_GROUPS_DATE, dateFormat.format(foodGroups.getFoodGroupDate()));

        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_FOOD_GROUPS_ID + " = " + foodGroups.getId(), null);

        return foodGroups;
    }

    public int deleteFoodGroups(FoodGroups foodGroups) {

        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_FOOD_GROUPS_ID + " = " + foodGroups.getId(), null);
        return rowsDeleted;
    }

    public FoodGroups getFoodGroupsByDate(String dateString){

        FoodGroups foodGroupsByDate;

        String query = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s " +
                        "FROM %s WHERE %s = '%s'", COLUMN_FOOD_GROUPS_ID, COLUMN_GRAIN_SERVINGS, COLUMN_BEAN_SERVINGS, COLUMN_VEGETABLE_SERVINGS,
                    COLUMN_FRUIT_SERVINGS, COLUMN_MEAT_FISH_EGGS_SERVINGS, COLUMN_DAIRY_SERVINGS, COLUMN_NUTS_SEEDS_SERVINGS, COLUMN_DESSERT_SERVINGS,
                    COLUMN_WATER_OUNCES, COLUMN_CUPS_COFFEE, COLUMN_FOOD_GROUPS_DATE, TABLE_NAME, COLUMN_FOOD_GROUPS_DATE, dateString);
        Log.d(TAG, query);
        Cursor c = database.rawQuery(query, null);

        if((c != null) && (c.getCount() > 0)){
            c.moveToFirst();

            //have to know data type of all the columns
            long id = c.getLong(0);
            int grainServings = c.getInt(1);
            int beanServings = c.getInt(2);
            int vegetableServings = c.getInt(3);
            int fruitServings = c.getInt(4);
            int meatFishEggsServings = c.getInt(5);
            int dairyServings = c.getInt(6);
            int nutsSeedsServings = c.getInt(7);
            int dessertServings = c.getInt(8);
            int waterOunces = c.getInt(9);
            int cupsCoffee = c.getInt(10);
            Date foodGroupDate = null;

            try {
                //DON'T KNOW IF THIS WILL WORK
                foodGroupDate = dateFormat.parse(c.getString(11));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            foodGroupsByDate = new FoodGroups(id, grainServings, beanServings,
                    vegetableServings, fruitServings, meatFishEggsServings, dairyServings, nutsSeedsServings,
                    dessertServings, waterOunces, cupsCoffee, foodGroupDate);
            //c.moveToNext(); // DONT FORGET THIS LINE!!!!!!

            c.close();
        }else{
            foodGroupsByDate = null;
        }
        return foodGroupsByDate;
    }

}


