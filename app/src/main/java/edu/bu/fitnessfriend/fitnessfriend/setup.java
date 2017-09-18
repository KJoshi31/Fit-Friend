package edu.bu.fitnessfriend.fitnessfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by karan on 9/9/2017.
 */

public class setup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_setup);

        //created spinner for activity
        Spinner activitySpinner = (Spinner) findViewById(R.id.activity_spinner);

        //created array adapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_level_array, android.R.layout.simple_spinner_item);

        //specify the layout to use when the choices appear on the screen
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter to the activity spinner
        activitySpinner.setAdapter(adapter);


        final EditText feetInput = (EditText) findViewById(R.id.feet_input);
        final EditText inchInput = (EditText) findViewById(R.id.inch_input);
        final EditText weightInput = (EditText) findViewById(R.id.weight_input);

        //Input formatting for the feet input
        //later needs to be refactored into its own method
        feetInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String feetInputString = feetInput.getText().toString();
                //Log.d("feet input", feetInputString);

                if(hasFocus && feetInputString.length() > 0 && !feetInputString.equals("")){
                    feetInputString = feetInputString.substring(0,feetInputString.length()-2);
                    feetInputString = feetInputString.trim();
                    feetInput.setText(feetInputString);
                }else{
                    if(feetInputString.length()>0 && !feetInputString.equals("")){
                        feetInputString = feetInputString.trim() + " ft";
                        feetInput.setText(feetInputString.trim());
                    }

                }


            }
        });

        feetInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String feetInputString = s.toString().trim();
                //Log.d("hi",Integer.toString(inchInputString.length()));
                if(feetInputString.length() == 3){

                    feetInputString = feetInputString.substring(1,feetInputString.length());
                    feetInput.setText(feetInputString);
                }

            }
        });



        //input formatting for the inch input
        //later needs to be refactored into its own method
        inchInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String inchInputString = inchInput.getText().toString();
                //Log.d("feet input", feetInputString);

                if(hasFocus && inchInputString.length() > 0 && !inchInputString.equals("")){
                    inchInputString = inchInputString.substring(0,inchInputString.length()-2);
                    inchInputString = inchInputString.trim();
                    inchInput.setText(inchInputString);
                    inchInput.setSelection(inchInput.getText().length());

                }else{
                    if(inchInputString.length()>0 && !inchInputString.equals("")){
                        inchInputString = inchInputString.trim() + " in";
                        inchInput.setText(inchInputString.trim());
                        inchInput.setSelection(inchInput.getText().length());

                    }

                }
            }

        });

        inchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inchInputString = s.toString();
                //Log.d("hi",Integer.toString(inchInputString.length()));
                //Log.d("hi",inchInputString);

                if(inchInputString.length() == 3){

                    inchInputString = inchInputString.substring(1,inchInputString.length());
                    inchInput.setText(inchInputString);
                }

                if(inchInputString.startsWith("0")){
                    inchInput.setText("");
                }


            }
        });

        weightInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String weightInputString = weightInput.getText().toString();
                //Log.d("feet input", feetInputString);

                if(hasFocus && weightInputString.length() > 0 && !weightInputString.equals("")){
                    weightInputString = weightInputString.substring(0,weightInputString.length()-3);
                    weightInputString = weightInputString.trim();
                    weightInput.setText(weightInputString);
                    weightInput.setSelection(weightInput.getText().length());


                }else{

                    if(weightInputString.endsWith(".")){
                        weightInputString = weightInputString.substring(0,weightInputString.length()-1);
                    }

                    if(weightInputString.length()>0 && !weightInputString.equals("")){
                        weightInputString = weightInputString.trim() + " lbs";
                        weightInput.setText(weightInputString.trim());
                    }



                }
            }

        });


        weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String weightInputString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //get weight input string text
                String weightInputString = s.toString();

                //if the string is length greater than 0 and starts with 0 or starts with decimal
                //then remove those characters by doing a substring and then set the text field
                if(weightInputString.length()>0 &&
                        (weightInputString.startsWith("0") || weightInputString.startsWith(".") ) ){
                    weightInputString = weightInputString.substring(1,weightInputString.length());
                    weightInput.setText(weightInputString);
                }
                //if the user tries to type 0 or decimal as first characters when empty, make it ""
                if( weightInputString.startsWith("0") || weightInputString.startsWith(".") ) {
                    weightInput.setText("");
                }
                //count how many decimals are in the string populated in the field
                int decimalCounter = 0;
                for(int i = 0;i<weightInputString.length(); i++){
                    if(weightInputString.toCharArray()[i] == '.'){
                        decimalCounter++;
                    }
                }
                //if there are more than 2 decimals then remove the second decimal and set the text
                if(decimalCounter == 2 && weightInputString.contains(".")){
                    weightInputString = weightInputString.substring(0,weightInputString.length()-1);
                    weightInput.setText(weightInputString);
                    weightInput.setSelection(weightInput.getText().length());

                }

            }
        });

    }
}
