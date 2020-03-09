package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.simplenutritiontracker.models.FoodGroups;
import com.example.simplenutritiontracker.models.Meal;
import com.example.simplenutritiontracker.sqlite.SQLFoodGroupsDataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFoodGroupsActivity extends AppCompatActivity {

    FoodGroups foodGroups;
    Button btnSave;
    Button btnDelete;
    SQLFoodGroupsDataAccess fgda;
    SimpleDateFormat dateFormat;

    EditText txtGrainServings, txtBeanServings, txtVegetableServings, txtFruitServings,
            txtMeatServings, txtDairyServings, txtNutsAndSeedsServings, txtDessertServings,
            txtWaterOunces, txtCupsOfCoffee, txtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_groups);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        txtGrainServings = findViewById(R.id.txtGrainServings);
        txtBeanServings = findViewById(R.id.txtBeanServings);
        txtVegetableServings = findViewById(R.id.txtVegetableServings);
        txtFruitServings = findViewById(R.id.txtFruitServings);
        txtMeatServings = findViewById(R.id.txtMeatServings);
        txtDairyServings = findViewById(R.id.txtDairyServings);
        txtNutsAndSeedsServings = findViewById(R.id.txtNutsAndSeedsServings);
        txtDessertServings = findViewById(R.id.txtDessertServings);
        txtWaterOunces = findViewById(R.id.txtWaterOunces);
        txtCupsOfCoffee = findViewById(R.id.txtCupsOfCoffee);
        txtDate = findViewById(R.id.txtDate);

        dateFormat = new SimpleDateFormat("M/d/yyyy");
        fgda = new SQLFoodGroupsDataAccess(this);

        Intent i = getIntent();
        String dateString = i.getStringExtra(FoodGroupDetailsActivity.EXTRA_MACROS_DATE);

        if (dateString != null) {
            foodGroups = fgda.getFoodGroupsByDate(dateString);
            putFoodGroupsDataIntoUI(foodGroups);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    foodGroups = getFoodGroupsDataFromUI();
                    if(foodGroups.getId() > 0){
                        fgda.updateFoodGroups(foodGroups);
                    }else{
                        fgda.insertFoodGroups(foodGroups);
                    }
                    Intent i = new Intent(EditFoodGroupsActivity.this, FoodGroupDetailsActivity.class);
                    i.putExtra(FoodGroupDetailsActivity.EXTRA_MACROS_DATE, dateFormat.format(foodGroups.getFoodGroupDate()));
                    startActivity(i);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodGroups f = getFoodGroupsDataFromUI();
                String foodGroupsDate = dateFormat.format(f.getFoodGroupDate());
                fgda.deleteFoodGroups(f);
                Intent i = new Intent(EditFoodGroupsActivity.this, FoodGroupDetailsActivity.class);
                i.putExtra(FoodGroupDetailsActivity.EXTRA_MACROS_DATE, foodGroupsDate);
                startActivity(i);
            }
        });

    }//end of onCreate()

    public void putFoodGroupsDataIntoUI(FoodGroups f) {

        txtGrainServings.setText(Integer.toString(f.getGrainServings()));
        txtBeanServings.setText(Integer.toString(f.getBeanServings()));
        txtVegetableServings.setText(Integer.toString(f.getVegetableServings()));
        txtFruitServings.setText(Integer.toString(f.getFruitServings()));
        txtMeatServings.setText(Integer.toString(f.getMeatFishEggsServings()));
        txtDairyServings.setText(Integer.toString(f.getDairyServings()));
        txtNutsAndSeedsServings.setText(Integer.toString(f.getNutsAndSeedsServings()));
        txtDessertServings.setText(Integer.toString(f.getDessertFoodsServings()));
        txtWaterOunces.setText(Integer.toString(f.getWaterOunces()));
        txtCupsOfCoffee.setText(Integer.toString(f.getCupsOfCoffee()));
        txtDate.setText(dateFormat.format(f.getFoodGroupDate()));

    }

    public FoodGroups getFoodGroupsDataFromUI() {

        int grain = Integer.parseInt(txtGrainServings.getText().toString());
        int bean = Integer.parseInt(txtBeanServings.getText().toString());
        int vegetable = Integer.parseInt(txtVegetableServings.getText().toString());
        int fruit = Integer.parseInt(txtFruitServings.getText().toString());
        int meat = Integer.parseInt(txtMeatServings.getText().toString());
        int dairy = Integer.parseInt(txtDairyServings.getText().toString());
        int nuts = Integer.parseInt(txtNutsAndSeedsServings.getText().toString());
        int dessert = Integer.parseInt(txtDessertServings.getText().toString());
        int water = Integer.parseInt(txtWaterOunces.getText().toString());
        int coffee = Integer.parseInt(txtCupsOfCoffee.getText().toString());

        Date date = null;
        try {
            date = dateFormat.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(foodGroups == null){
            foodGroups = new FoodGroups(grain, bean, vegetable, fruit, meat, dairy, nuts, dessert, water, coffee, date);
        }else{
            foodGroups.setGrainServings(grain);
            foodGroups.setBeanServings(bean);
            foodGroups.setVegetableServings(vegetable);
            foodGroups.setFruitServings(fruit);
            foodGroups.setMeatFishEggsServings(meat);
            foodGroups.setDairyServings(dairy);
            foodGroups.setNutsAndSeedsServings(nuts);
            foodGroups.setDessertFoodsServings(dessert);
            foodGroups.setWaterOunces(water);
            foodGroups.setCupsOfCoffee(coffee);
            foodGroups.setFoodGroupDate(date);
        }

        return foodGroups;
    }

    public boolean validate(){

        boolean isValid = true;
        if(txtGrainServings.getText().toString().equals("")){
            txtGrainServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtBeanServings.getText().toString().equals("")){
            txtBeanServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtVegetableServings.getText().toString().equals("")){
            txtVegetableServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtFruitServings.getText().toString().equals("")){
            txtFruitServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtMeatServings.getText().toString().equals("")){
            txtMeatServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtDairyServings.getText().toString().equals("")){
            txtDairyServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtNutsAndSeedsServings.getText().toString().equals("")){
            txtNutsAndSeedsServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtDessertServings.getText().toString().equals("")){
            txtDessertServings.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtWaterOunces.getText().toString().equals("")){
            txtWaterOunces.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtCupsOfCoffee.getText().toString().equals("")){
            txtCupsOfCoffee.setError("Please enter a number of servings");
            isValid = false;
        }
        if(txtDate.getText().toString().equals("")){
            txtDate.setError("Please enter a date");
            isValid = false;
        }else {
            Date date = null;
            try {
                date = dateFormat.parse(txtDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                txtDate.setError("You have an invalid date");
                isValid = false;
            }
        }
        return isValid;
    }
}

