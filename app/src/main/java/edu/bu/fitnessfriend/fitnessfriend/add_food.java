package edu.bu.fitnessfriend.fitnessfriend;

import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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

        Button logFoodButton = (Button) findViewById(R.id.add_food_btn);
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


}

