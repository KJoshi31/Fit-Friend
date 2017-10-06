package edu.bu.fitnessfriend.fitnessfriend.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.exerciseDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;

/**
 * Created by karan on 9/9/2017.
 */

public class exercise_history extends AppCompatActivity {

    ArrayList<String> exercistList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_exercise_history);

        populateList();
    }

    @Override
    public void onResume(){
        super.onResume();
        populateList();
    }

    public void populateList(){
        ListView exercistHistoryList = (ListView) findViewById(R.id.exercise_lst);

        myDatabaseHandler handler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        exerciseDatabaseUtils exerciseDatabaseUtils = new exerciseDatabaseUtils(handler);

        exercistList = exerciseDatabaseUtils.getExerciseHistoryList();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,exercistList);

        exercistHistoryList.setAdapter(adapter);

    }

    protected void navigateAddExercisePage(View view){
        Intent addExerciseIntent = new Intent(this, add_exercise.class);
        startActivity(addExerciseIntent);
    }
}
