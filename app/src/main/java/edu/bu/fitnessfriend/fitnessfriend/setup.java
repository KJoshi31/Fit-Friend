package edu.bu.fitnessfriend.fitnessfriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by karan on 9/9/2017.
 */

public class setup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<EditText> setupInputs = new ArrayList<>();
    private button_validation_utility btnUtility = new button_validation_utility();
    boolean textInputsFilled;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        alreadyRegistered();

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_setup);

        Button finishBtn = (Button) findViewById(R.id.finish_setup_btn);
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        EditText weightInput = (EditText) findViewById(R.id.weight_input);
        EditText ageInput = (EditText) findViewById(R.id.age_input);
        EditText feetInput = (EditText) findViewById(R.id.feet_input);
        EditText inchInput = (EditText) findViewById(R.id.inch_input);

        finishBtn.setEnabled(false);

        setupInputs.add(nameInput);
        setupInputs.add(weightInput);
        setupInputs.add(ageInput);
        setupInputs.add(feetInput);
        setupInputs.add(inchInput);

        for (int i = 0; i<setupInputs.size();i++){
            setupInputs.get(i).addTextChangedListener(textWatcher);
        }




        //created spinner for activity
        Spinner activitySpinner = (Spinner) findViewById(R.id.activity_spinner);

        //created array adapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_level_array, android.R.layout.simple_spinner_item);

        //specify the layout to use when the choices appear on the screen
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter to the activity spinner
        activitySpinner.setAdapter(adapter);



    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enableButton = btnUtility.inputsFilled(setupInputs);
            Button finishBtn = (Button) findViewById(R.id.finish_setup_btn);
            btnUtility.enableAddButton(finishBtn, enableButton);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void finishSetup(View view){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("registered",true);
        editor.commit();


        Intent welcomeIntent = new Intent(this, welcome.class);
        startActivity(welcomeIntent);
    }

    public void alreadyRegistered(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("pref",context.MODE_PRIVATE);

        boolean alreadyRegistered = sharedPref.getBoolean("registered",false);

        if(alreadyRegistered){
            Intent i = new Intent(this, welcome.class);
            startActivity(i);
        }


    }


}
