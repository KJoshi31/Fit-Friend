package edu.bu.fitnessfriend.fitnessfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
                }else{
                    if(inchInputString.length()>0 && !inchInputString.equals("")){
                        inchInputString = inchInputString.trim() + " in";
                        inchInput.setText(inchInputString.trim());
                    }

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
                String weightInputString = s.toString();

                if(weightInputString.length()>0 &&
                        (weightInputString.startsWith("0") || weightInputString.startsWith(".") ) ){
                    weightInputString = weightInputString.substring(1,weightInputString.length());
                    weightInput.setText(weightInputString);
                }

                if( weightInputString.startsWith("0") || weightInputString.startsWith(".") ) {
                    weightInput.setText("");
                }

                int decimalCounter = 0;
                for(int i = 0;i<weightInputString.length(); i++){
                    if(weightInputString.toCharArray()[i] == '.'){
                        decimalCounter++;
                    }
                }

                if(decimalCounter == 2 && weightInputString.contains(".")){
                    weightInputString = weightInputString.substring(0,weightInputString.length()-1);
                    weightInput.setText(weightInputString);
                    weightInput.setSelection(weightInput.getText().length());

                }

            }
        });

    }
}
