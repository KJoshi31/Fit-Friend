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

import edu.bu.fitnessfriend.fitnessfriend.database.demographicDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.model.demographic;

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
        activitySpinner.setOnItemSelectedListener(this);


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Button finishBtn = (Button) findViewById(R.id.finish_setup_btn);
            Spinner activitySpinner = (Spinner) findViewById(R.id.activity_spinner);

            textInputsFilled = btnUtility.inputsFilled(setupInputs);

            String selectedItem = activitySpinner.getSelectedItem().toString().trim();

            if(textInputsFilled && !selectedItem.equals("Choose Activity Level:")){
                finishBtn.setEnabled(true);
            }else{
                finishBtn.setEnabled(false);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Button finishBtn = (Button) findViewById(R.id.finish_setup_btn);

        if(position!= 0 && textInputsFilled == true){
            finishBtn.setEnabled(true);
        }else{
            finishBtn.setEnabled(false);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Button finishBtn = (Button) findViewById(R.id.finish_setup_btn);
        String selectedItem = parent.getSelectedItem().toString().trim();

        if(textInputsFilled && !selectedItem.equals("Choose Activity Level:")){
            finishBtn.setEnabled(true);
        }else{
            finishBtn.setEnabled(false);
        }
    }

    public void finishSetup(View view){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("registered",true);
        editor.commit();

        databaseOperation();

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

    protected void databaseOperation(){
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        EditText weightInput = (EditText) findViewById(R.id.weight_input);
        EditText ageInput = (EditText) findViewById(R.id.age_input);
        EditText feetInput = (EditText) findViewById(R.id.feet_input);
        EditText inchInput = (EditText) findViewById(R.id.inch_input);
        Spinner activitySpinner = (Spinner) findViewById(R.id.activity_spinner);

        demographic demographicObj = new demographic();
        demographicObj.setName(nameInput.getText().toString().trim());
        demographicObj.setWeight(Integer.parseInt(weightInput.getText().toString().trim()));
        demographicObj.setAge(Integer.parseInt(ageInput.getText().toString().trim()));
        demographicObj.setFeet(Integer.parseInt(feetInput.getText().toString().trim()));
        demographicObj.setInch(Integer.parseInt(inchInput.getText().toString().trim()));
        demographicObj.setActivitylvl(activitySpinner.getSelectedItem().toString().trim());
        

        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        demographicDatabaseUtils demoUtils = new demographicDatabaseUtils(dbHandler);
        demoUtils.insertDemoInfo(demographicObj);
    }


}
