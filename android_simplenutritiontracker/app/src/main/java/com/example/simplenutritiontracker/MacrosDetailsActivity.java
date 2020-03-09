package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.simplenutritiontracker.models.Macros;
import com.example.simplenutritiontracker.models.Meal;
import com.example.simplenutritiontracker.sqlite.SQLMacrosDataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MacrosDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MacrosDetailsActivity";
    public static final String EXTRA_MACROS_ID = "macrosId";

    SQLMacrosDataAccess mda;
    Macros macros;
    EditText txtCalories;
    EditText txtProtein;
    EditText txtCarb;
    EditText txtFat;
    EditText txtDate;
    Button btnSave;
    Button btnDelete;

    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_details);

        txtCalories = findViewById(R.id.txtCalories);
        txtProtein= findViewById(R.id.txtProtein);
        txtCarb = findViewById(R.id.txtCarb);
        txtFat = findViewById(R.id.txtFat);
        txtDate = findViewById(R.id.txtDate);

        btnSave = findViewById(R.id.btnSaveMacros);
        btnDelete = findViewById(R.id.btnDeleteMacros);

        mda = new SQLMacrosDataAccess(this);
        dateFormat = new SimpleDateFormat("M/d/yyyy");

        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_MACROS_ID, 0);

        if (id > 0) {
            Log.d(TAG, "Display the task in the UI with the id of: " + id);
            macros = mda.getMacrosById(id);
            //NEXT: add this method and test that data is transferring/can edit details page
            //THEN: change listView to something better w/ svgs
            putMacrosDataIntoUI(macros);
        } else {
            Log.d(TAG, "Creating new task");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Macros m = getMacrosDataFromUI();
                    if(m.getId() > 0){
                        mda.updateMacros(m);
                    }else{
                        mda.insertMacros(m);
                    }
                    Intent i = new Intent(MacrosDetailsActivity.this, MacrosListActivity.class);
                    startActivity(i);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Macros m = getMacrosDataFromUI();
                mda.deleteMacros(m);
                Intent i = new Intent(MacrosDetailsActivity.this, MacrosListActivity.class);
                startActivity(i);
            }
        });
    }//end of onCreate()

    public void putMacrosDataIntoUI(Macros m) {

        txtCalories.setText(Integer.toString(m.getCalorieIntake()));
        txtProtein.setText(Integer.toString(m.getProteinMacros()));
        txtCarb.setText(Integer.toString(m.getCarbMacros()));
        txtFat.setText(Integer.toString(m.getFatMacros()));
        txtDate.setText(dateFormat.format(m.getMacrosDate()));

    }

    public Macros getMacrosDataFromUI() {

        int calories = Integer.parseInt(txtCalories.getText().toString());
        int protein = Integer.parseInt(txtProtein.getText().toString());
        int carb = Integer.parseInt(txtCarb.getText().toString());
        int fat = Integer.parseInt(txtFat.getText().toString());

        Date macrosDate = null;
        try {
            macrosDate = dateFormat.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(macros == null){
            //UPDATE THIS CLASS/LAYOUT TO INCLUDE DATE LATER
            macros = new Macros(calories, protein, carb, fat, macrosDate);
        }else{
            macros.setCalorieIntake(calories);
            macros.setProteinMacros(protein);
            macros.setCarbMacros(carb);
            macros.setFatMacros(fat);
            macros.setMacrosDate(macrosDate);
        }

        return macros;
    }

    public boolean validate(){

        boolean isValid = true;
        if(txtCalories.getText().toString().equals("")){
            txtCalories.setError("Please enter a calorie intake");
            isValid = false;
        }
        if(txtProtein.getText().toString().equals("")){
            txtProtein.setError("Please enter your protein macros");
            isValid = false;
        }
        if(txtCarb.getText().toString().equals("")){
            txtCarb.setError("Please enter your carb macros");
            isValid = false;
        }
        if(txtFat.getText().toString().equals("")){
            txtFat.setError("Please enter your fat macros");
            isValid = false;
        }
        if(txtDate.getText().toString().equals("")){
            txtDate.setError("Please enter your macros date");
            isValid = false;
        }else {
            Date macrosDate = null;
            try {
                macrosDate = dateFormat.parse(txtDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                txtDate.setError("You have an invalid date");
                isValid = false;
            }
        }
        return isValid;
    }

}
