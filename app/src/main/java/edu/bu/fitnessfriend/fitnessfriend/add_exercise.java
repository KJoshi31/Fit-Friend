package edu.bu.fitnessfriend.fitnessfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by karan on 9/9/2017.
 */

public class add_exercise extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        //find spinner
        Spinner durationSpinner = (Spinner) findViewById(R.id.duration_spinner);

        //create adapter that stores the duration array string as a simple spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.duration_array,android.R.layout.simple_spinner_item);

        //displays item in the adapter as a simple spinner drop down item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //apply the adapter to the spinner
        durationSpinner.setAdapter(adapter);
    }
}
