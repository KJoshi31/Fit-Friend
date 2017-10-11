package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by karan on 10/8/2017.
 */

public class serviceDatabaseUtils {

    myDatabaseHandler _handler;

    myDatabaseHandler.foodSMSDB foodSMSDB;
    myDatabaseHandler.foodNotifDB foodNotifDB;

    myDatabaseHandler.exerciseSMSDB exerciseSMSDB;
    myDatabaseHandler.exerciseNotifDB exerciseNotifDB;

    public serviceDatabaseUtils(myDatabaseHandler handler){
        _handler = handler;
    }

    public void insertFoodSMSReminder(long milliseconds){
        ContentValues values = new ContentValues();
        values.put(foodSMSDB.SMS_WAIT_MILLISECONDS,String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(foodSMSDB.TABLE_FOOD_SMS,null,values);

        db.close();
    }

    public void insertFoodNotifReminder(long milliseconds){
        ContentValues values = new ContentValues();
        values.put(foodNotifDB.NOTIF_WAIT_MILLISECONDS, String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(foodNotifDB.TABLE_FOOD_NOTIF,null,values);

        db.close();
    }

    public void insertExerciseSMSReminder(long milliseconds){
        ContentValues values = new ContentValues();
        values.put(exerciseSMSDB.SMS_WAIT_MILLISECONDS, String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(exerciseSMSDB.TABLE_EX_SMS,null,values);

        db.close();
    }

    public void insertExerciseNotifReminder(long milliseconds){
        ContentValues values = new ContentValues();
        values.put(exerciseNotifDB.NOTIF_WAIT_MILLISECONDS, String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(exerciseNotifDB.TABLE_EX_NOTIF,null,values);

        db.close();
    }
}
