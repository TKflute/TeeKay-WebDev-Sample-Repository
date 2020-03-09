package com.example.simplenutritiontracker.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.simplenutritiontracker.models.Macros;
import com.example.simplenutritiontracker.models.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLMacrosDataAccess {

    //this data access class is just for one table
    //for another table would need another data access class
    public static final String TAG = "SQLMacrosDataAccess";

    // Instance variables
    //private ArrayList<Macros> allMacross;
    private Context context;
    //class that helps create and get database
    private MySQLiteHelper dbHelper;
    private SQLiteDatabase database;
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");

    // Constructors
    public SQLMacrosDataAccess(Context context){
        Log.d(TAG, "Instantiating SQLDataAccess");
        this.context = context;
        this.dbHelper = new MySQLiteHelper(context);
        //'database' is equiv of connection to DB, but object represents whole database. will run queries against it
        //getWritableDatabase() checks if database exists; if doesn't calls onCreate()
        //onCreate only gets called once
        this.database = this.dbHelper.getWritableDatabase();
    }


    // We should create static constants for the table name, and all column names
    public static final String TABLE_NAME = "macros";
    //Android convention to name primary key as '_id'
    //??Followed naming convention from Macros app- isn't this still insecure? Is it only for convenience?
    public static final String  COLUMN_MACROS_ID = "_id";
    public static final String  COLUMN_CALORIE_INTAKE = "calorieIntake";
    public static final String  COLUMN_PROTEIN_MACROS = "proteinMacros";
    public static final String  COLUMN_CARB_MACROS = "carbMacros";
    public static final String  COLUMN_FAT_MACROS = "fatMacros";
    public static final String  COLUMN_MACROS_DATE = "macrosDate";

    //I think these have be be strings (%s)
    public static final String TABLE_CREATE = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s DATETIME)",
            TABLE_NAME,
            COLUMN_MACROS_ID,
            COLUMN_CALORIE_INTAKE,
            COLUMN_PROTEIN_MACROS,
            COLUMN_CARB_MACROS,
            COLUMN_FAT_MACROS,
            COLUMN_MACROS_DATE
    );

    //***NOT SURE WILL WANT/NEED TO USE AN INTERFACE W/ ONLY ONE TYPE OF DA CLASS?
   // @Override
    public ArrayList<Macros> getAllMacros() {

        ArrayList<Macros> allMacros = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s", COLUMN_MACROS_ID, COLUMN_CALORIE_INTAKE, COLUMN_PROTEIN_MACROS, COLUMN_CARB_MACROS,
                                     COLUMN_FAT_MACROS, COLUMN_MACROS_DATE, TABLE_NAME);

        Cursor c = database.rawQuery(query, null);

        // make sure we got some results from the db before processing them
        if((c != null) && (c.getCount() > 0)) {

            c.moveToFirst();

            while (!c.isAfterLast()) {

                //have to know data type of all the columns
                long id = c.getLong(0);
                int calories = c.getInt(1);
                int protein = c.getInt(2);
                //leave as example - will be 0 or 1 in db
                //boolean done = (c.getLong(3) == 1 ? true : false);
                int carb = c.getInt(3);
                int fat = c.getColumnIndex(COLUMN_FAT_MACROS);
                String fatStr = Integer.toString(fat);
                Log.d(TAG, fatStr);
                fat = c.getInt(4);

                Date macrosDate = null;

                try {
                    macrosDate = dateFormat.parse(c.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Macros m = new Macros(id, calories, protein, carb, fat, macrosDate);
                allMacros.add(m);
                c.moveToNext(); // DONT FORGET THIS LINE!!!!!!
            }
            c.close();
        }
        return allMacros;
    }

    //@Override
    public Macros getMacrosById(long id) {

        String query = String.format("SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = %d", COLUMN_MACROS_ID, COLUMN_CALORIE_INTAKE, COLUMN_PROTEIN_MACROS,
                        COLUMN_CARB_MACROS, COLUMN_FAT_MACROS, COLUMN_MACROS_DATE, TABLE_NAME, COLUMN_MACROS_ID, id);
        Log.d(TAG, query);
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();

        id = c.getLong(0); //this was commented out in TaskApp - will we use it later?
        int calories = c.getInt(1);
        int protein = c.getInt(2);
        int carb = c.getInt(3);
        int fat = c.getInt(4);

        Date macrosDate = null;

        try {
            macrosDate = dateFormat.parse(c.getString(5));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.close();

        Macros m = new Macros(id,calories, protein, carb, fat, macrosDate);
        return  m;
    }

    //@Override
    public Macros insertMacros(Macros m) {

        //like a hashmap
        ContentValues values = new ContentValues();
        values.put(COLUMN_CALORIE_INTAKE, m.getCalorieIntake());
        //converting date to string - leave this in for later if add a date obj
        //values.put(COLUMN_PROTEIN_MACROS, dateFormat.format(m.getProteinMacros()));
        values.put(COLUMN_PROTEIN_MACROS, m.getProteinMacros());
        values.put(COLUMN_CARB_MACROS, m.getCarbMacros());
        //leave this in as an example for booleans w/ DB
        //values.put(COLUMN_FAT_MACROS, m.getFatMacros() ? 1 : 0));
        values.put(COLUMN_FAT_MACROS, m.getFatMacros());
        values.put(COLUMN_MACROS_DATE, dateFormat.format(m.getMacrosDate()));



        //??What does the null do? Meant to ask this in class
        long insertId = database.insert(TABLE_NAME, null, values);
        // note: insertId will be -1 if the insert failed

        //set id on Macros object
        m.setId(insertId);
        return m;
    }

    //@Override
    public Macros updateMacros(Macros m) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CALORIE_INTAKE, m.getCalorieIntake());
        values.put(COLUMN_PROTEIN_MACROS, m.getProteinMacros());
        values.put(COLUMN_CARB_MACROS, m.getCarbMacros());
        values.put(COLUMN_FAT_MACROS, m.getFatMacros());
        values.put(COLUMN_MACROS_DATE, dateFormat.format(m.getMacrosDate()));
        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_MACROS_ID + " = " + m.getId(), null);
        // this method returns the number of rows that were updated in the db
        // so that you could use it to confirm that your update worked
        return m;
        //??why aren't we returning rowsUpdated?? Did I miss that change?
        //I think it's so we can see the changed field values
    }

    //@Override
    public int deleteMacros(Macros m) {
        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_MACROS_ID + " = " + m.getId(), null);
        // the above method returns the number of row that were deleted (an int)
        return rowsDeleted;
    }
}