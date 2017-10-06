package edu.bu.fitnessfriend.fitnessfriend.exercise;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.exerciseDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.model.exercise;
import edu.bu.fitnessfriend.fitnessfriend.utilities.button_validation_utility;

/**
 * Created by karan on 9/9/2017.
 */

public class add_exercise extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<EditText> addExerciseInputs = new ArrayList<>();
    button_validation_utility btnUtility = new button_validation_utility();
    boolean textInputsFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        Button logExerciseButton = (Button)findViewById(R.id.add_ex_btn);
        logExerciseButton.setEnabled(false);
        EditText exerciseNameField = (EditText) findViewById(R.id.ex_name_input);
        EditText exerciseCaloriesField = (EditText) findViewById(R.id.ex_calorie_input);
        EditText durationField = (EditText) findViewById(R.id.duration_input);

        addExerciseInputs.add(exerciseNameField);
        addExerciseInputs.add(exerciseCaloriesField);
        addExerciseInputs.add(durationField);

        for(int i = 0; i<addExerciseInputs.size(); i++){
            addExerciseInputs.get(i).addTextChangedListener(textWatcher);
        }

        //find spinner
        Spinner durationSpinner = (Spinner) findViewById(R.id.duration_spinner);
        //create adapter that stores the duration array string as a simple spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.duration_array,android.R.layout.simple_spinner_item);
        //displays item in the adapter as a simple spinner drop down item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        durationSpinner.setAdapter(adapter);
        durationSpinner.setOnItemSelectedListener(this);


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Button logExerciseButton =(Button) findViewById(R.id.add_ex_btn);
            Spinner unitSpinner = (Spinner) findViewById(R.id.duration_spinner);

            textInputsFilled = btnUtility.inputsFilled(addExerciseInputs, unitSpinner);

            String selectedItem = unitSpinner.getSelectedItem().toString().trim();

            if(textInputsFilled && !selectedItem.equals("Unit:") ){
                logExerciseButton.setEnabled(true);
            }else{
                logExerciseButton.setEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Button logExerciseButton =(Button) findViewById(R.id.add_ex_btn);

        if(position!=0 && textInputsFilled == true){
            logExerciseButton.setEnabled(true);
        }else{
            logExerciseButton.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Button logExerciseButton = (Button) findViewById(R.id.add_ex_btn);
        String selectedItem = parent.getSelectedItem().toString().trim();


        if(textInputsFilled && !selectedItem.equals("Unit:") ){
            logExerciseButton.setEnabled(true);
        }else{
            logExerciseButton.setEnabled(false);
        }
        
    }

    public void logExercise(View view){
        exercise exerciseObj = new exercise();

        EditText exerciseNameField = (EditText) findViewById(R.id.ex_name_input);
        EditText exerciseCaloriesField = (EditText) findViewById(R.id.ex_calorie_input);
        EditText durationField = (EditText) findViewById(R.id.duration_input);
        Spinner durationSpinner = (Spinner) findViewById(R.id.duration_spinner);

        String exName = exerciseNameField.getText().toString().trim();
        double exCalorie = Double.parseDouble(exerciseCaloriesField.getText().toString().trim());
        double exDuration = Double.parseDouble(durationField.getText().toString().trim());
        String exUnit = durationSpinner.getSelectedItem().toString().trim();

        exerciseObj.setExerciseName(exName);
        exerciseObj.setCalorie(exCalorie);
        exerciseObj.setDuration(exDuration);
        exerciseObj.setUnit(exUnit);

        myDatabaseHandler handler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        exerciseDatabaseUtils exerciseDatabaseUtils = new exerciseDatabaseUtils(handler);
        exerciseDatabaseUtils.insertExercise(exerciseObj);


        //get spinner on page
        Spinner unitSpinner = (Spinner) findViewById(R.id.duration_spinner);
        //create spinner to display
        Snackbar logExerciseSnackbar =
                Snackbar.make(findViewById(R.id.addExercise),"Exercise Logged", 0);
        //clear all the arraylist full of inputs that are on the page
        btnUtility.clearInputs(addExerciseInputs);
        //set the selection to the default
        unitSpinner.setSelection(0);
        //show the snackbar
        logExerciseSnackbar.show();
    }
}
