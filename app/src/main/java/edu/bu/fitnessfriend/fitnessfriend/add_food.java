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

    private static ArrayList<EditText> addFoodInputs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Button logFoodButton = (Button) findViewById(R.id.add_food_btn);
        EditText foodNameField = (EditText) findViewById(R.id.food_name_input);
        EditText qtyField = (EditText) findViewById(R.id.qty_input);
        EditText foodCaloriesField = (EditText) findViewById(R.id.food_cal_input);

        logFoodButton.setEnabled(false);

        addFoodInputs.add(foodNameField);
        addFoodInputs.add(qtyField);
        addFoodInputs.add(foodCaloriesField);

        for(int i = 0; i<addFoodInputs.size(); i++){
            String a = Integer.toString(i);
            Log.d("hi",a);
            addFoodInputs.get(i).addTextChangedListener(textWatcher);
        }

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = inputsFilled(addFoodInputs);
            Button logFoodButton = (Button)findViewById(R.id.add_food_btn);
            enableAddButton(logFoodButton, enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public boolean inputsFilled(ArrayList<EditText> inputFields) {
        boolean isEmpty = false;


        for(int i = 0; i<inputFields.size(); i++) {
            if(inputFields.get(i).getText().toString().trim().equals("")||
                    inputFields.get(i).getText().toString().length()==0){
                isEmpty = true;
                break;
            }
        }

        return isEmpty;

    }

    public void enableAddButton(Button button, Boolean setter){
        if (setter) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }


}

