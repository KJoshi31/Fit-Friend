package edu.bu.fitnessfriend.fitnessfriend;

import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by karan on 9/20/2017.
 */

public class button_validation_utility {


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
