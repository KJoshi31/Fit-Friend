package edu.bu.fitnessfriend.fitnessfriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import edu.bu.fitnessfriend.fitnessfriend.database.demographicDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.exerciseDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences("pref",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean("registered",true);
        editor.commit();
        boolean alreadyRegistered = sharedPref.getBoolean("registered",true);

        if(alreadyRegistered == false){
            Intent intent = new Intent(this, setup.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        displayName();
        displayFoodTotalToday();
        displayExerciseTotalToday();
    }


    //function that gets called when add exercise button is pressed on welcome page
    protected void navigateAddExercisePage(View view){
        Intent addExerciseIntent = new Intent(this, add_exercise.class);
        startActivity(addExerciseIntent);
    }

    //function that gets called when the add food button is pressed on welcome page
    protected void navigateAddFoodPage(View view) {
        Intent addFoodIntent = new Intent(this, add_food.class);
        startActivity(addFoodIntent);
    }

    //function that gets called when the exercise history button is pressed on the welcome page
    protected void navigateExerciseHistoryPage(View view){
        Intent exerciseHistoryIntent = new Intent(this, exercise_history.class);
        startActivity(exerciseHistoryIntent);
    }

    //function that gets called when the food history button is pressed on welcome page
    protected void navigateFoodHistoryPage(View view) {
        Intent foodHistoryIntent = new Intent(this, food_history.class);
        startActivity(foodHistoryIntent);
    }

    //function that gets called when message reminder options button is pressed on welcome page
    protected void navigateMessageOptionsPage(View view){
        Intent messageReminderOptionsIntent = new Intent(this, message_options.class );
        startActivity(messageReminderOptionsIntent);
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_welcome);
        displayName();
        displayFoodTotalToday();
        displayExerciseTotalToday();

    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void displayName(){
        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
        demographicDatabaseUtils demoUtils = new demographicDatabaseUtils(dbHandler);

        String userName = demoUtils.getNameInfo();
        Log.d("username", userName);
        TextView welcomeDisplay = (TextView) findViewById(R.id.welcom_back_lbl);
        welcomeDisplay.setText("Welcome "+ userName);
    }

    public void displayFoodTotalToday(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);

        foodDatabaseUtils foodUtils = new foodDatabaseUtils(dbHandler);

        int foodItemCount = foodUtils.foodItemNumber(currentDateTimeString);

        Log.d("food item count",String.valueOf(foodItemCount));

        TextView foodLoggedTodayLabel = (TextView)findViewById(R.id.food_log_lbl);

        String foodLoggedTodayText = foodLoggedTodayLabel.getText() + " "
                +String.valueOf(foodItemCount)+" Items";

        foodLoggedTodayLabel.setText(foodLoggedTodayText);

        TextView foodCaloriesEatenTodayLabel = (TextView)findViewById(R.id.cal_eaten_lbl);
        int foodCaloriesToday = foodUtils.foodTotalCalories(currentDateTimeString);
        String foodCaloriesTodayText = foodCaloriesEatenTodayLabel.getText()+" "+
                String.valueOf(foodCaloriesToday)+ " Calories";

        foodCaloriesEatenTodayLabel.setText(foodCaloriesTodayText);
    }

    public void displayExerciseTotalToday(){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        myDatabaseHandler dbHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);

        exerciseDatabaseUtils exerciseDatabaseUtils = new exerciseDatabaseUtils(dbHandler);

        int exerciseCountToday = exerciseDatabaseUtils.exerciseItemNumber(currentDateTimeString);

        TextView exercisesLoggedTodayLabel = (TextView) findViewById(R.id.ex_log_lbl);

        String exLoggedTodayText = exercisesLoggedTodayLabel.getText() + " "
                +String.valueOf(exerciseCountToday)+" Exercises";

        exercisesLoggedTodayLabel.setText(exLoggedTodayText);


        TextView caloriesBurntTodayLabel = (TextView) findViewById(R.id.cal_burnt_lbl);
        int calsBurntToday = exerciseDatabaseUtils.exerciseTotalCalories(currentDateTimeString);

        String updatedCaloriesBurntTodayLabel = caloriesBurntTodayLabel.getText() + " "+
                String.valueOf(calsBurntToday) + " Calories";


        caloriesBurntTodayLabel.setText(updatedCaloriesBurntTodayLabel);
    }

}
