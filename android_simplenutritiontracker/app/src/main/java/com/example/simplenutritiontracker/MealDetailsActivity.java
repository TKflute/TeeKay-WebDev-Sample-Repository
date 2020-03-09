package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplenutritiontracker.models.Macros;
import com.example.simplenutritiontracker.models.Meal;
import com.example.simplenutritiontracker.sqlite.SQLMacrosDataAccess;
import com.example.simplenutritiontracker.sqlite.SQLMealDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MealDetailsActivity";
    public static final String EXTRA_MEAL_ID = "mealId";

    SQLMealDataAccess meda;
    Meal meal;
    ArrayList<Meal> mealsByDate;

    EditText txtMealDescriptionDV;
    EditText txtProteinSourceDV;
    EditText txtCarbSourceDV;
    EditText txtFatSourceDV;
    EditText txtDate;

    Button btnSave;
    Button btnDelete;

    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        txtMealDescriptionDV = findViewById(R.id.txtMealDescriptionDV);
        txtProteinSourceDV = findViewById(R.id.txtProteinSourceDV);
        txtCarbSourceDV = findViewById(R.id.txtCarbSourceDV);
        txtFatSourceDV = findViewById(R.id.txtFatSourceDV);
        txtDate = findViewById(R.id.txtDate);

        btnSave = findViewById(R.id.btnSaveMeal);
        btnDelete = findViewById(R.id.btnDeleteMeal);

        meda = new SQLMealDataAccess(this);
        dateFormat= new SimpleDateFormat("M/d/yyyy");

        //probably won't need a custom Adapter here; was for general testing
        //customMealList();

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_MEAL_ID, 0);

        if (id > 0) {
            Log.d(TAG, "Display the meal in with the id of: " + id);
            meal = meda.getMealById(id);
            //NEXT: add this method and test that data is transferring/can edit details page DONE
            //THEN: change details View to something better w/ svgs if can***
            putMealDataIntoUI(meal);
        } else {
            Log.d(TAG, "Creating new meal");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Meal m = getMealDataFromUI();
                    if(m.getId() > 0){
                        meda.updateMeal(m);
                    }else{
                        meda.insertMeal(m);
                    }
                    Intent i = new Intent(MealDetailsActivity.this, MealListActivity.class);
                    i.putExtra(MealListActivity.EXTRA_MACROS_DATE, dateFormat.format(m.getMealDate()));
                    startActivity(i);
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meal m = getMealDataFromUI();
                String mealDate = dateFormat.format(m.getMealDate());
                meda.deleteMeal(m);
                Intent i = new Intent(MealDetailsActivity.this, MealListActivity.class);
                i.putExtra(MealListActivity.EXTRA_MACROS_DATE, mealDate);
                startActivity(i);
            }
        });

    }//end of onCreate()

    public void putMealDataIntoUI(Meal meal) {

        txtMealDescriptionDV.setText(meal.getMealDescription());
        txtProteinSourceDV.setText(meal.getProteinSource());
        txtCarbSourceDV.setText(meal.getCarbSource());
        txtFatSourceDV.setText(meal.getFatSource());
        txtDate.setText(dateFormat.format(meal.getMealDate()));

    }

    public Meal getMealDataFromUI() {

        String mealDescription = txtMealDescriptionDV.getText().toString();
        String proteinSource = txtProteinSourceDV.getText().toString();
        String carbSource = txtCarbSourceDV.getText().toString();
        String fatSource = txtFatSourceDV.getText().toString();

        Date mealDate = null;
        try {
            mealDate = dateFormat.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(meal == null){
            //WILL NEED TO ADD FIELD FOR DATE ENTRY
            meal = new Meal(mealDescription, proteinSource, carbSource, fatSource, mealDate);
        }else{
            meal.setMealDescription(mealDescription);
            meal.setProteinSource(proteinSource);
            meal.setCarbSource(carbSource);
            meal.setFatSource(fatSource);
            meal.setMealDate(mealDate);
        }

        return meal;
    }

    public boolean validate(){

        boolean isValid = true;
        if(txtMealDescriptionDV.getText().toString().equals("")){
            txtMealDescriptionDV.setError("Please enter a meal description");
            isValid = false;
        }
        if(txtProteinSourceDV.getText().toString().equals("")){
            txtProteinSourceDV.setError("Please enter your protein source");
            isValid = false;
        }
        if(txtCarbSourceDV.getText().toString().equals("")){
            txtCarbSourceDV.setError("Please enter your carb source");
            isValid = false;
        }
        if(txtFatSourceDV.getText().toString().equals("")){
            txtFatSourceDV.setError("Please enter your fat Source");
            isValid = false;
        }
        if(txtDate.getText().toString().equals("")){
            txtDate.setError("Please enter your meal date");
            isValid = false;
        }else {
            Date mealDate = null;
            try {
                mealDate = dateFormat.parse(txtDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                txtDate.setError("You have an invalid date");
                isValid = false;
            }
        }
        return isValid;
    }


}