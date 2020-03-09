package com.example.simplenutritiontracker.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.simplenutritiontracker.models.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLMealDataAccess {

    public static final String TAG = "SQLMealDataAccess";

    // Instance variables
    //private ArrayList<Macros> allMacross;
    private Context context;
    //class that helps create and get database
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

    // Constructors
    public SQLMealDataAccess(Context context){
        Log.d(TAG, "Instantiating SQLMealDataAccess");
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        //'database' is equiv of connection to DB, but object represents whole database. will run queries against it
        //getWritableDatabase() checks if database exists; if doesn't calls onCreate()
        //onCreate only gets called once
        this.database = this.dbHelper.getWritableDatabase();
    }

    // We should create static constants for the table name, and all column names
    public static final String TABLE_NAME = "meals";
    //Android convention to name primary key as '_id'
    public static final String  COLUMN_MEAL_ID = "_id";
    public static final String  COLUMN_MEAL_DESCRIPTION = "mealDescription";
    public static final String  COLUMN_PROTEIN_SOURCE = "proteinSource";
    public static final String  COLUMN_CARB_SOURCE = "carbSource";
    public static final String  COLUMN_FAT_SOURCE = "fatSource";
    public static final String COLUMN_MEAL_DATE = "mealDate";

    public static final String TABLE_CREATE = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(255), %s VARCHAR(255), " +
                                            "%s VARCHAR(255), %s VARCHAR(255), %s DATETIME)",
            TABLE_NAME,
            COLUMN_MEAL_ID,
            COLUMN_MEAL_DESCRIPTION,
            COLUMN_PROTEIN_SOURCE,
            COLUMN_CARB_SOURCE,
            COLUMN_FAT_SOURCE,
            COLUMN_MEAL_DATE
    );

    //***NOT SURE WILL WANT/NEED TO USE AN INTERFACE W/ ONLY ONE TYPE OF DA CLASS?
    // @Override
    public ArrayList<Meal> getAllMeals() {

        ArrayList<Meal> meals = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s", COLUMN_MEAL_ID, COLUMN_MEAL_DESCRIPTION, COLUMN_PROTEIN_SOURCE,
                                    COLUMN_CARB_SOURCE, COLUMN_FAT_SOURCE, COLUMN_MEAL_DATE, TABLE_NAME);
        Cursor c = database.rawQuery(query, null);

        // make sure we got some results from the db before processing them
        if((c != null) && (c.getCount() > 0)) {

            c.moveToFirst();

            while (!c.isAfterLast()) {

                //have to know data type of all the columns
                long id = c.getLong(0);
                String mealDescription = c.getString(1);
                String proteinSource = c.getString(2);
                String carbSource = c.getString(3);
                String fatSource = c.getString(4);
                Date mealDate = null;

                try {
                    //DON'T KNOW IF THIS WILL WORK
                    mealDate = dateFormat.parse(c.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Meal m = new Meal(id, mealDescription, proteinSource, carbSource, fatSource, mealDate);
                meals.add(m);
                c.moveToNext(); // DONT FORGET THIS LINE!!!!!!
            }
            c.close();
        }
        return meals;
    }

    //@Override
    public Meal getMealById(long id) {

        String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = %d",
                        COLUMN_MEAL_ID, COLUMN_MEAL_DESCRIPTION, COLUMN_PROTEIN_SOURCE, COLUMN_CARB_SOURCE,
                        COLUMN_FAT_SOURCE, COLUMN_MEAL_DATE, TABLE_NAME, COLUMN_MEAL_ID, id);

        Log.d(TAG, query);
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();

        id = c.getLong(0);
        String mealDescription = c.getString(1);
        String proteinSource = c.getString(2);
        String carbSource = c.getString(3);
        String fatSource = c.getString(4);
        Date mealDate = null;

        try {
            //DON'T KNOW IF THIS WILL WORK
            mealDate = dateFormat.parse(c.getString(5));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.close();

        Meal m = new Meal(id, mealDescription, proteinSource, carbSource, fatSource, mealDate);
        return  m;
    }

    public Meal insertMeal(Meal m) {

        //like a hashmap
        ContentValues values = new ContentValues();
        //NOT SURE IF STRINGS WILL WORK SAME WAY AS INTS HERE
        values.put(COLUMN_MEAL_DESCRIPTION, m.getMealDescription());
        values.put(COLUMN_PROTEIN_SOURCE, m.getProteinSource());
        values.put(COLUMN_CARB_SOURCE, m.getCarbSource());
        values.put(COLUMN_FAT_SOURCE, m.getFatSource());
        values.put(COLUMN_MEAL_DATE, dateFormat.format(m.getMealDate()));


        //??What does the null do? Meant to ask this in class
        long insertId = database.insert(TABLE_NAME, null, values);
        // note: insertId will be -1 if the insert failed

        //set id on Macros object
        m.setId(insertId);
        return m;
    }

    public Meal updateMeal(Meal m) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEAL_DESCRIPTION, m.getMealDescription());
        values.put(COLUMN_PROTEIN_SOURCE, m.getProteinSource());
        values.put(COLUMN_CARB_SOURCE, m.getCarbSource());
        values.put(COLUMN_FAT_SOURCE, m.getFatSource());
        values.put(COLUMN_MEAL_DATE, dateFormat.format(m.getMealDate()));


        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_MEAL_ID + " = " + m.getId(), null);

        return m;
        //??why aren't we returning rowsUpdated?? Did I miss that change?
        //I think it's so we can see the changed field values
    }

    public int deleteMeal(Meal m) {

        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_MEAL_ID + " = " + m.getId(), null);
        return rowsDeleted;
    }

    public ArrayList<Meal> getMealByDate(String dateString){

        ArrayList<Meal> mealByDateList = new ArrayList<>();

        String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = '%s'",
                COLUMN_MEAL_ID, COLUMN_MEAL_DESCRIPTION, COLUMN_PROTEIN_SOURCE, COLUMN_CARB_SOURCE,
                COLUMN_FAT_SOURCE, COLUMN_MEAL_DATE, TABLE_NAME, COLUMN_MEAL_DATE, dateString);

        Log.d(TAG, query);
        Cursor c = database.rawQuery(query, null);

        // make sure we got some results from the db before processing them
        if((c != null) && (c.getCount() > 0)) {

            c.moveToFirst();

            while (!c.isAfterLast()) {

                //have to know data type of all the columns
                long id = c.getLong(0);
                String mealDescription = c.getString(1);
                String proteinSource = c.getString(2);
                String carbSource = c.getString(3);
                String fatSource = c.getString(4);
                Date mealDate = null;

                try {
                    //DON'T KNOW IF THIS WILL WORK
                    mealDate = dateFormat.parse(c.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Meal m = new Meal(id, mealDescription, proteinSource, carbSource, fatSource, mealDate);
                mealByDateList.add(m);
                c.moveToNext(); // DONT FORGET THIS LINE!!!!!!
            }
            c.close();
        }
        return mealByDateList;
    }

}
