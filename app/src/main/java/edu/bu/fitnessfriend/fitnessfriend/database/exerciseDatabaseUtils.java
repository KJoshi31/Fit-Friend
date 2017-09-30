package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public int exerciseItemNumber(String today){

        Cursor cursor = null;
        int exerciseItems = 0;

        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from exercises",null);

        SimpleDateFormat dateFormater=new SimpleDateFormat("MMM dd, yyyy");


        while(cursor.moveToNext()){
            String exerciseRecordDate = cursor.getString(cursor.getColumnIndex("exercise_log_date"));

            Log.d("exercise's record date",exerciseRecordDate);

            try {
                Date recordDate = dateFormater.parse(exerciseRecordDate);
                Date todayDate = dateFormater.parse(today);

                int compare = recordDate.compareTo(todayDate);

                if(compare == 0) exerciseItems++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        return exerciseItems;
    }

    public int exerciseTotalCalories(String today){

        Cursor cursor = null;
        int exerciseCalorieBurntToday = 0;

        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from exercises",null);

        SimpleDateFormat dateFormater=new SimpleDateFormat("MMM dd, yyyy");

        while(cursor.moveToNext()){
            int calorieBurnt =
                    Integer.valueOf(cursor.getString(cursor.getColumnIndex("exercise_calorie")));

            String exerciseRecordDate = cursor.getString(cursor.getColumnIndex("exercise_log_date"));


            try {
                Date recordDate = dateFormater.parse(exerciseRecordDate);
                Date todayDate = dateFormater.parse(today);

                int compare = recordDate.compareTo(todayDate);

                if(compare == 0){
                    exerciseCalorieBurntToday = exerciseCalorieBurntToday + calorieBurnt;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        return exerciseCalorieBurntToday;
    }
}
