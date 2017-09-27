package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Date;

import edu.bu.fitnessfriend.fitnessfriend.model.exercise;

/**
 * Created by karan on 9/24/2017.
 */

public class exerciseDatabaseUtils {

    private myDatabaseHandler.exerciseDB exerciseDatabase = null;
    private myDatabaseHandler _handler;

    public exerciseDatabaseUtils(myDatabaseHandler handler){
        _handler = handler;
    }

    public void insertExercise(exercise exerciseItem){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        ContentValues values = new ContentValues();
        values.put(exerciseDatabase.EXERCISE_NAME, exerciseItem.getExerciseName());
        values.put(exerciseDatabase.EXERCISE_CAL, exerciseItem.getCalorie());
        values.put(exerciseDatabase.EXERCISE_DURATION, exerciseItem.getDuration());
        values.put(exerciseDatabase.EXERCISE_UNIT, exerciseItem.getUnit());
        values.put(exerciseDatabase.EXERCISE_DATE, currentDateTimeString);

        SQLiteDatabase db = _handler.getWritableDatabase();
        db.insert(exerciseDatabase.TABLE_EXERCISE,null,values);
        db.close();
    }
}
