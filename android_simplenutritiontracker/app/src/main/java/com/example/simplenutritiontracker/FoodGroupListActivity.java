package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simplenutritiontracker.models.FoodGroups;
import com.example.simplenutritiontracker.sqlite.SQLFoodGroupsDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodGroupListActivity extends AppCompatActivity {

    public static final String TAG = "FoodGroupListActivity";
    private ListView lsFoodGroups;
    private SQLFoodGroupsDataAccess fgda;
    private ArrayList<FoodGroups> foodGroupsByDate;
    public static final String EXTRA_MACROS_DATE = "macrosDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_list);

        //HAVE TO SET INSTANCE VARS HERE OR LV IS NULL
        lsFoodGroups = findViewById(R.id.lsFoodGroups);
        fgda = new SQLFoodGroupsDataAccess(this);
        //allFoodGroups = fgda.getAllFoodGroups();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodGroupListActivity.this, FoodGroupDetailsActivity.class);
                startActivity(i);
            }
        });

        /*
        //this code still works w/ sqlite (I'll still want this code)
        if(allFoodGroups.size() == 0){
            Intent i = new Intent(FoodGroupListActivity.this, FoodGroupDetailsActivity.class);
            startActivity(i);
        }*/

        Intent i = getIntent();
        String macrosDate = i.getStringExtra(EXTRA_MACROS_DATE);

        //foodGroupsByDate = fgda.getFoodGroupsByDate(macrosDate);

        //SET ADAPTER
        ArrayAdapter<FoodGroups> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodGroupsByDate);
        lsFoodGroups.setAdapter(adapter);

        //SET ONCLICK LISTENER
        lsFoodGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodGroups selectedFoodGroups = foodGroupsByDate.get(position);
                Log.d(TAG, "SELECTED FOOD GROUPS OBJ: " + selectedFoodGroups.toString());
                Intent i = new Intent(FoodGroupListActivity.this, FoodGroupDetailsActivity.class);
                i.putExtra(FoodGroupDetailsActivity.EXTRA_FOOD_GROUPS_ID, selectedFoodGroups.getId());
                startActivity(i);
            }
        });
    }
}
