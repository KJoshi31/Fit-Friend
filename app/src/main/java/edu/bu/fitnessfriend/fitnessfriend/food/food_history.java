package edu.bu.fitnessfriend.fitnessfriend.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;

/**
 * Created by karan on 9/9/2017.
 */

public class food_history extends AppCompatActivity {

    ArrayList<String> foodItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_food_history);

        populateList();

    }

    @Override
    protected void onResume(){
        super.onResume();
        populateList();
    }


    public void populateList(){
        ListView foodHistory = (ListView) findViewById(R.id.food_lst);

        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        foodDatabaseUtils foodDatabaseUtils = new foodDatabaseUtils(dbHandler);

        foodItems = foodDatabaseUtils.getFoodHistoryList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, foodItems);

        foodHistory.setAdapter(adapter);

    }

    protected void navigateAddFoodPage(View view) {
        Intent addFoodIntent = new Intent(this, add_food.class);
        startActivity(addFoodIntent);
    }


}
