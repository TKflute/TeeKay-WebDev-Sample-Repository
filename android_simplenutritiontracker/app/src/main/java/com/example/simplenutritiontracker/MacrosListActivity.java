package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplenutritiontracker.models.Macros;
import com.example.simplenutritiontracker.sqlite.SQLMacrosDataAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MacrosListActivity extends AppCompatActivity {

    public static final String TAG = "MacrosListActivity";
    private ListView lsMacros;
    private SQLMacrosDataAccess mda;
    private ArrayList<Macros> allMacros;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macros_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MacrosListActivity.this, MacrosDetailsActivity.class);
                startActivity(i);
            }
        });

        lsMacros = findViewById(R.id.lsMacros);

        dateFormat= new SimpleDateFormat("M/d/yyyy");

        mda = new SQLMacrosDataAccess(this);

        allMacros = mda.getAllMacros();
        Log.d(TAG, allMacros.toString());

        //brings to details view if there is no csv file
        if(allMacros.size() == 0){
            Intent i = new Intent(MacrosListActivity.this, MacrosDetailsActivity.class);
            startActivity(i);
        }

        customMacrosList();

    }//end of onCreate()

    private void customMacrosList() {
        ArrayAdapter<Macros> adapter = new ArrayAdapter(this, R.layout.custom_list_item_macros_list_view, R.id.lblCalorieNumber, allMacros) {
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView) {
                final View listItemView = super.getView(position, convertView, parentListView);
                TextView lblCalorieNumber = listItemView.findViewById(R.id.lblCalorieNumber);
                TextView lblProteinNumber = listItemView.findViewById(R.id.lblProteinNumber);
                TextView lblCarbNumber = listItemView.findViewById(R.id.lblCarbNumber);
                TextView lblFatNumber = listItemView.findViewById(R.id.lblFatNumber);
                TextView lblDate = listItemView.findViewById(R.id.lblDate);

                Button btnEditMacros = listItemView.findViewById(R.id.btnEditMacros);
                Button btnViewMeal = listItemView.findViewById(R.id.btnViewMeal);
                Button btnViewFoodGroups = listItemView.findViewById(R.id.btnViewFoodGroups);


                final Macros currentMacros = allMacros.get(position);

                lblCalorieNumber.setText(Integer.toString(currentMacros.getCalorieIntake()));
                lblProteinNumber.setText(Integer.toString(currentMacros.getProteinMacros()));
                lblCarbNumber.setText(Integer.toString(currentMacros.getCarbMacros()));
                lblFatNumber.setText(Integer.toString(currentMacros.getFatMacros()));
                lblDate.setText(dateFormat.format(currentMacros.getMacrosDate()));

                listItemView.setTag(currentMacros);
                //Don't quite get why there are two Macros obj's (check Task app)
                final Macros m = (Macros)listItemView.getTag();

                btnEditMacros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "EDIT THIS MACROS: " + m.toString());
                        Intent i = new Intent(MacrosListActivity.this, MacrosDetailsActivity.class);
                        i.putExtra(MacrosDetailsActivity.EXTRA_MACROS_ID, currentMacros.getId());
                        startActivity(i);
                    }
                });

                //NOT SURE HOW I WILL DO THESE BUTTONS YET OR IF WILL KEEP THEM
                //Now that I've looked at the screens linking together; I can see that it would be ideal to link this data together
                //So then maybe I should just store macros per meal, rather than per day (for now; could do a sum function later by searching by date to get total)
                btnViewMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MacrosListActivity.this, MealListActivity.class);
                        //just change all the date instance vars to 'date' later
                        //should I format the date here or in mealList? Don't think it matters
                        i.putExtra(MealListActivity.EXTRA_MACROS_DATE, dateFormat.format(currentMacros.getMacrosDate()));
                        startActivity(i);
                    }
                });

                btnViewFoodGroups.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MacrosListActivity.this, FoodGroupDetailsActivity.class);
                        //just change all the date instance vars to 'date' later
                        //should I format the date here or in mealList? Don't think it matters
                        i.putExtra(FoodGroupDetailsActivity.EXTRA_MACROS_DATE, dateFormat.format(currentMacros.getMacrosDate()));
                        startActivity(i);
                    }
                });


                return listItemView;
            }
        };
        lsMacros.setAdapter(adapter);
    }

}
