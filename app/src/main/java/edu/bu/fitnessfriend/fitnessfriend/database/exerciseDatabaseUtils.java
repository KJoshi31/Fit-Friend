package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import edu.bu.fitnessfriend.fitnessfriend.model.exercise;

/**
 * Created by karan on 9/24/2017.
 */

public class exerciseDatabaseUtils {

    private myDatabaseHandler.exerciseDB exerciseDatabase = null;
    private myDatabaseHandler.exerciseRemindersDB exerciseRemindersDB = null;
    private myDatabaseHandler.addedRemindersDB addedRemindersDB = null;
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
        db.close();

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
        db.close();

        return exerciseCalorieBurntToday;
    }

    public ArrayList<String> getExerciseHistoryList(){


        ArrayList<String> exerciserecords = new ArrayList<>();

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();
        cursor = db.rawQuery("select * from exercises",null);

        while (cursor.moveToNext()){
            String exName = cursor.getString(cursor.getColumnIndex("exercise_name"));
            String exCal = cursor.getString(cursor.getColumnIndex("exercise_calorie"));
            String exDuration = cursor.getString(cursor.getColumnIndex("exercise_duration"));
            String exUnit = cursor.getString(cursor.getColumnIndex("exercise_unit"));
            String exRecordDate = cursor.getString(cursor.getColumnIndex("exercise_log_date"));

            String record = "\nExercise Name: "+exName+"\nExercise Calories: "+exCal+
                    "\nExercise Duration: "+exDuration+" "+exUnit+
                    "\nAdd Date: "+exRecordDate;

            exerciserecords.add(record);
        }

        cursor.close();
        db.close();
        Collections.reverse(exerciserecords);

        return exerciserecords;
    }

    public void insertReminderDate(String reminderType, String logType, long milliseconds){
        ContentValues values = new ContentValues();

        values.put(exerciseRemindersDB.LOG_TYPE, logType);
        values.put(exerciseRemindersDB.REMINDER_TYPE, reminderType);
        values.put(exerciseRemindersDB.WAIT_MILLISECONDS,String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();
        db.insert(exerciseRemindersDB.TABLE_EX_REMINDER,null, values);
        db.close();
    }

    public ArrayList<String> getLogInfo(){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();
        cursor = db.rawQuery("select * from "+exerciseRemindersDB.TABLE_EX_REMINDER,null);

        cursor.moveToLast();

        String logType = cursor.getString(cursor.getColumnIndex(exerciseRemindersDB.LOG_TYPE));
        String reminderType = cursor.getString(cursor.getColumnIndex(exerciseRemindersDB.REMINDER_TYPE));
        String milliseconds = cursor.getString(cursor.getColumnIndex(exerciseRemindersDB.WAIT_MILLISECONDS));

        cursor.close();
        db.close();

        ArrayList<String> logInfo = new ArrayList<>();
        logInfo.add(logType);
        logInfo.add(reminderType);
        logInfo.add(milliseconds);

        return logInfo;
    }

    public void deleteAllFoodReminders(){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+exerciseRemindersDB.TABLE_EX_REMINDER);
        cursor.close();
        db.close();
    }

    public void deleteAllReminderType(String reminderType){
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+exerciseRemindersDB.TABLE_EX_REMINDER+" WHERE "+
                exerciseRemindersDB.REMINDER_TYPE+" = "+"\"reminderType\"");
        db.close();
    }

    public void deleteSpecificReminder(String logType,String reminderType, long milliseconds){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+exerciseRemindersDB.TABLE_EX_REMINDER+" WHERE "+"("+
                exerciseRemindersDB.REMINDER_TYPE+" = "+"\"reminderType\""+  " AND "+exerciseRemindersDB.LOG_TYPE+
                " = "+"\"logType\"" + " AND "+exerciseRemindersDB.WAIT_MILLISECONDS + " = "
                +"\"String.valueOf(milliseconds)\""+")");
        cursor.close();
        db.close();
    }

    public void insertLastLogged(String logType){
        ContentValues values = new ContentValues();
        values.put(addedRemindersDB.LOGGED_REMINDER,logType);
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.insert(addedRemindersDB.TABLE_LAST_REMINDERS,null, values);

        db.close();
    }
}
