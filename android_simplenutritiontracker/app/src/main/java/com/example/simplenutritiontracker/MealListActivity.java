package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplenutritiontracker.models.Meal;
import com.example.simplenutritiontracker.sqlite.SQLMealDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealListActivity extends AppCompatActivity {

    public static final String TAG = "MealListActivity";
    public static final String EXTRA_MACROS_DATE = "macrosDate";
    private ListView lsMeals;
    private SQLMealDataAccess meda;
    private ArrayList<Meal> mealsByDate;
    private String macrosDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MealListActivity.this, MealDetailsActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fabHome = findViewById(R.id.fabHome);
        fabHome.setImageResource(R.drawable.home_button);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MealListActivity.this, MacrosListActivity.class);
                startActivity(i);
            }
        });

        lsMeals = findViewById(R.id.lsMeals);

        meda = new SQLMealDataAccess(this);

        /*allMeals = meda.getAllMeals();
        Log.d(TAG, allMeals.toString());

        //brings to details view if there is no csv file
        if(allMeals.size() == 0){
            Intent i = new Intent(MealListActivity.this, MealDetailsActivity.class);
            startActivity(i);
        }*/

        //TRYING TO GET MEALS BY DATE

        Intent i = getIntent();
        String macrosDate = i.getStringExtra(EXTRA_MACROS_DATE);
        //this method doesn't seem to cause problems if it returns null, but is that a good idea?
        mealsByDate = meda.getMealByDate(macrosDate);

        if(mealsByDate != null){
            customMealList();
        }

    }//end of onCreate()

    private void customMealList() {
        ArrayAdapter<Meal> adapter = new ArrayAdapter(this, R.layout.custom_list_item_meal_list_view, R.id.txtMealDescription, mealsByDate) {
            @Override
            public View getView(int position, View convertView, ViewGroup parentListView) {
                final View listItemView = super.getView(position, convertView, parentListView);
                EditText txtMealDescription = listItemView.findViewById(R.id.txtMealDescription);
                EditText txtProteinSource = listItemView.findViewById(R.id.txtProteinSource);
                EditText txtCarbSource = listItemView.findViewById(R.id.txtCarbSource);
                EditText txtFatSource = listItemView.findViewById(R.id.txtFatSource);

                Button btnEditMeal = listItemView.findViewById(R.id.btnEditMeal);

                final Meal currentMeal = mealsByDate.get(position);
                txtMealDescription.setText(currentMeal.getMealDescription());
                txtProteinSource.setText(currentMeal.getProteinSource());
                txtCarbSource.setText(currentMeal.getCarbSource());
                txtFatSource.setText(currentMeal.getFatSource());

                listItemView.setTag(currentMeal);

                btnEditMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Meal m = (Meal)listItemView.getTag();
                        Log.d(TAG, "EDIT THIS MEAL: " + m.toString());
                        Intent i = new Intent(MealListActivity.this, MealDetailsActivity.class);
                        i.putExtra(MealDetailsActivity.EXTRA_MEAL_ID, currentMeal.getId());
                        startActivity(i);
                    }
                });

                return listItemView;
            }
        };
        lsMeals.setAdapter(adapter);
    }

}
