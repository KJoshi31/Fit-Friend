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

    public String getFoodSMSWait(){

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from "+foodSMSDB.TABLE_FOOD_SMS,null);

        cursor.moveToLast();

        String milliseconds = cursor.getString(cursor.getColumnIndex(foodSMSDB.SMS_WAIT_MILLISECONDS));

        cursor.close();

        return milliseconds;
    }

    public String getFoodNotifWait(){

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from "+foodNotifDB.TABLE_FOOD_NOTIF,null);

        cursor.moveToLast();

        String milliseconds = cursor.getString(cursor.getColumnIndex(foodNotifDB.NOTIF_WAIT_MILLISECONDS));

        cursor.close();

        return milliseconds;
    }

    public String getExSMSWait(){

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from "+exerciseSMSDB.TABLE_EX_SMS,null);

        cursor.moveToLast();

        String milliseconds = cursor.getString(cursor.getColumnIndex(exerciseSMSDB.SMS_WAIT_MILLISECONDS));

        cursor.close();

        return milliseconds;
    }

    public String getExNotifWait(){

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from "+exerciseNotifDB.TABLE_EX_NOTIF,null);

        cursor.moveToLast();

        String milliseconds = cursor.getString(cursor.getColumnIndex(exerciseNotifDB.NOTIF_WAIT_MILLISECONDS));

        cursor.close();

        return milliseconds;
    }


}
