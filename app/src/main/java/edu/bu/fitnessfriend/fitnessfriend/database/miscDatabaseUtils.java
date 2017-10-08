package edu.bu.fitnessfriend.fitnessfriend.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by karan on 10/8/2017.
 */

public class miscDatabaseUtils {

    myDatabaseHandler _handler;
    myDatabaseHandler.addedRemindersDB addedRemindersDB = null;

    public miscDatabaseUtils(myDatabaseHandler handler){
        _handler = handler;
    }

    public String getLastLogged(){
        Cursor cursor = null;

        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from "+addedRemindersDB.TABLE_LAST_REMINDERS,null);

        cursor.moveToLast();

        String lastAddedLog = cursor.getString(cursor.getColumnIndex(addedRemindersDB.LOGGED_REMINDER));
        //removeAddedRemindersRecords();

        cursor.close();

        return lastAddedLog;
    }

    public ArrayList<String> getAllLogged(){
        Cursor cursor = null;

        ArrayList<String> logList = new ArrayList<>();

        SQLiteDatabase db = _handler.getReadableDatabase();
        cursor = db.rawQuery("select * from "+addedRemindersDB.TABLE_LAST_REMINDERS,null);

        while(cursor.moveToNext()){
            String lastAddedLog = cursor.getString(cursor.getColumnIndex(addedRemindersDB.LOGGED_REMINDER));
            logList.add(lastAddedLog);
        }

        cursor.close();
        db.close();

        return logList;
    }

    public void removeAddedRemindersRecords(){
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+addedRemindersDB.TABLE_LAST_REMINDERS);
        db.close();
    }
}
