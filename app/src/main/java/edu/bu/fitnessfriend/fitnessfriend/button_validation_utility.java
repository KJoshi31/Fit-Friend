package edu.bu.fitnessfriend.fitnessfriend;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by karan on 9/20/2017.
 */

public class button_validation_utility {

    public boolean spinnerSwitch = false;

    public boolean inputsFilled(ArrayList<EditText> inputFields) {
        boolean fieldEmpty = false;


        for(int i = 0; i<inputFields.size(); i++) {
            if(inputFields.get(i).getText().toString().trim().equals("")||
                    inputFields.get(i).getText().toString().length()==0){
                fieldEmpty = true;
                break;
            }
        }

        return fieldEmpty;

    }

    public boolean inputsFilled(ArrayList<EditText> inputFields, Spinner spinner) {

        boolean fieldsEmpty = false;
        for(int i = 0; i<inputFields.size(); i++) {
            if( (inputFields.get(i).getText().toString().trim().equals("") )  ||
                    (inputFields.get(i).getText().toString().length()==0)){
                fieldsEmpty = true;
                break;
            }
        }
        Log.d("spinner inputsfilled():", Boolean.toString(spinnerSwitch));
        Log.d("isEmpty inputsfilled():", Boolean.toString(fieldsEmpty));

        return fieldsEmpty;

    }

    public void enableAddButton(Button button, Boolean setter){
        if (setter) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }



}
