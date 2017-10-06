package edu.bu.fitnessfriend.fitnessfriend.utilities;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by karan on 9/20/2017.
 */

public class button_validation_utility {

    public boolean spinnerSwitch = false;

    public boolean inputsFilled(ArrayList<EditText> inputFields) {
        boolean allFieldsEmpty = true;


        for(int i = 0; i<inputFields.size(); i++) {
            if(inputFields.get(i).getText().toString().trim().equals("")||
                    inputFields.get(i).getText().toString().length()==0){
                allFieldsEmpty = false;
                break;
            }
        }

        return allFieldsEmpty;

    }

    public boolean inputsFilled(ArrayList<EditText> inputFields, Spinner spinner) {

        boolean allFieldsEmpty = true;


        for(int i = 0; i<inputFields.size(); i++) {
            if(inputFields.get(i).getText().toString().trim().equals("")||
                    inputFields.get(i).getText().toString().length()==0){
                allFieldsEmpty = false;
                break;
            }
        }

        return allFieldsEmpty;

    }

    public void enableAddButton(Button button, Boolean setter){
        if (setter == false) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    public void clearInputs(ArrayList<EditText> textFields){
        for(int i = 0; i<textFields.size(); i++){
            textFields.get(i).setText("");
        }
    }

    public static void clearRadioGroup(RadioGroup rdio){
        rdio.clearCheck();
    }



}
