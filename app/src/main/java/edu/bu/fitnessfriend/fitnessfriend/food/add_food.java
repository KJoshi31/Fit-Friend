package edu.bu.fitnessfriend.fitnessfriend.food;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.model.food;
import edu.bu.fitnessfriend.fitnessfriend.utilities.button_validation_utility;

/**
 * Created by karan on 9/9/2017.
 */

public class add_food extends AppCompatActivity{

    private ArrayList<EditText> addFoodInputs = new ArrayList<>();
    private button_validation_utility btnUtility = new button_validation_utility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);


        //notification stuff
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(getIntent()!=null){
            int notifId = getIntent().getIntExtra("notification_id",0);
            Log.d("add food notif id",String.valueOf(notifId));
            notificationManager.cancel(notifId);
        }



        Button logFoodButton = (Button) findViewById(R.id.add_food_btn);
        logFoodButton.setEnabled(false);
        EditText foodNameField = (EditText) findViewById(R.id.food_name_input);
        EditText qtyField = (EditText) findViewById(R.id.qty_input);
        EditText foodCaloriesField = (EditText) findViewById(R.id.food_cal_input);


        addFoodInputs.add(foodNameField);
        addFoodInputs.add(qtyField);
        addFoodInputs.add(foodCaloriesField);

        for(int i = 0; i<addFoodInputs.size(); i++){
            addFoodInputs.get(i).addTextChangedListener(textWatcher);
        }
        

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = btnUtility.inputsFilled(addFoodInputs);
            Button logFoodButton = (Button)findViewById(R.id.add_food_btn);
            btnUtility.enableAddButton(logFoodButton, enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected void logFood(View view){

        food foodObj = new food();

        EditText foodNameField = (EditText) findViewById(R.id.food_name_input);
        EditText qtyField = (EditText) findViewById(R.id.qty_input);
        EditText foodCaloriesField = (EditText) findViewById(R.id.food_cal_input);

        String foodName = foodNameField.getText().toString().trim();
        double foodQuantity = Double.parseDouble(qtyField.getText().toString().trim());
        double foodCalorie = Double.parseDouble(foodCaloriesField.getText().toString().trim());

        foodObj.setFoodName(foodName);
        foodObj.setQuantity(foodQuantity);
        foodObj.setCalorie(foodCalorie);

        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        foodDatabaseUtils foodDatabaseUtils = new foodDatabaseUtils(dbHandler);
        foodDatabaseUtils.insertFood(foodObj);

        Snackbar logFoodSnackbar = Snackbar.make(findViewById(R.id.addFood), "Food Logged", 0);
        btnUtility.clearInputs(addFoodInputs);
        logFoodSnackbar.show();
    }


}

