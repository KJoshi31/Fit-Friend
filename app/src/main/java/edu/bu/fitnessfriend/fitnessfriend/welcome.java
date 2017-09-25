package edu.bu.fitnessfriend.fitnessfriend;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.util.Date;

public class welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
}
