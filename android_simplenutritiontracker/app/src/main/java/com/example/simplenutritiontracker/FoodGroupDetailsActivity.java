package com.example.simplenutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplenutritiontracker.models.FoodGroups;
import com.example.simplenutritiontracker.sqlite.SQLFoodGroupsDataAccess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FoodGroupDetailsActivity extends AppCompatActivity {

    FoodGroups foodGroupsByDate;
    ListView lsFoodGroups;

    Button btnEdit;

    ArrayList<String> foodGroupData;

    SQLFoodGroupsDataAccess fgda;

    public static final String EXTRA_FOOD_GROUPS_ID = "foodGroupsId";
    public static final String TAG = "FoodGroupDetailsActivity";
    public static final String EXTRA_MACROS_DATE = "macrosDate";

    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_group_list);

        fgda = new SQLFoodGroupsDataAccess(this);
        lsFoodGroups = findViewById(R.id.lsFoodGroups);
        dateFormat = new SimpleDateFormat("M/d/yyyy");
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodGroupDetailsActivity.this, EditFoodGroupsActivity.class);
                startActivity(i);
            }
        });

        FloatingActionButton fabHome = findViewById(R.id.fabHome);
        fabHome.setImageResource(R.drawable.home_button);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodGroupDetailsActivity.this, MacrosListActivity.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        String macrosDate = i.getStringExtra(EXTRA_MACROS_DATE);

        foodGroupsByDate = fgda.getFoodGroupsByDate(macrosDate);
        if(foodGroupsByDate != null){
            foodGroupData = foodGroupsByDate.returnFoodGroupData();
            customFoodGroupDetails();
        }
    }//end of onCreate()

    private void customFoodGroupDetails() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.custom_list_item_fg_details, R.id.lblGrainServings, foodGroupData) {


            @Override
            public View getView(int position, View convertView, ViewGroup parentListView) {
                final View listItemView = super.getView(position, convertView, parentListView);
                //this array will contain 2 pieces of data
                final String[] strArr = foodGroupData.get(position).split("-");
                final String foodGroupLabel = strArr[0];
                final String numberOfServings = strArr[1];

                TextView lblGrainServings = listItemView.findViewById(R.id.lblGrainServings);
                final EditText txtGrainServings = listItemView.findViewById(R.id.txtGrainServings);
                ImageView imageView = listItemView.findViewById(R.id.imageView);
                btnEdit = listItemView.findViewById(R.id.btnEdit);

                lblGrainServings.setText(foodGroupLabel);
                txtGrainServings.setText(numberOfServings);


                switch(position){
                    case 0:
                        imageView.setImageResource(R.drawable.grain);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.beans);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.vegetables);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.fruit);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.meat);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.dairy);
                        break;
                    case 6:
                        imageView.setImageResource(R.drawable.nuts);
                        break;
                    case 7:
                        imageView.setImageResource(R.drawable.dessert);
                        break;
                    case 8:
                        imageView.setImageResource(R.drawable.water);
                        break;
                    case 9:
                        imageView.setImageResource(R.drawable.coffee);
                        break;
                }

                //NOT SURE IF I NEED EITHER ONE OF THESE LINES
                //txtGrainServings.setTag(foodGroups);
                listItemView.setTag(foodGroupsByDate);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(FoodGroupDetailsActivity.this, EditFoodGroupsActivity.class);
                        i.putExtra(FoodGroupDetailsActivity.EXTRA_MACROS_DATE, dateFormat.format(foodGroupsByDate.getFoodGroupDate()));
                        startActivity(i);
                    }
                });

                return listItemView;
            }
        };
        lsFoodGroups.setAdapter(adapter);
    }
}
