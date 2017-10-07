package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import edu.bu.fitnessfriend.fitnessfriend.model.food;

/**
 * Created by karan on 9/24/2017.
 */

public class foodDatabaseUtils {

    private myDatabaseHandler.foodDB foodDatabase = null;
    private myDatabaseHandler.foodReminderDB foodRemindersDB = null;
    private myDatabaseHandler _handler;

    public foodDatabaseUtils(myDatabaseHandler handler){
        _handler = handler;
    }

    //method used to insert food entries into the food table of database
    public void insertFood(food foodItem){

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        ContentValues values = new ContentValues();
        values.put(foodDatabase.FOOD_NAME,foodItem.getFoodName());
        values.put(foodDatabase.FOOD_QTY, foodItem.getQuantity());
        values.put(foodDatabase.FOOD_CAL, foodItem.getCalorie());
        values.put(foodDatabase.FOOD_DATE, currentDateTimeString);

        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(foodDatabase.TABLE_FOOD,null, values);
        db.close();
    }

    public int foodItemNumber(String today){

        Cursor cursor = null;
        int foodItems = 0;

        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from foods",null);

        SimpleDateFormat dateFormater=new SimpleDateFormat("MMM dd, yyyy");


        while(cursor.moveToNext()){
            String foodRecordDate = cursor.getString(cursor.getColumnIndex("food_log_date"));

            Log.d("food's record date",foodRecordDate);

            try {
                Date recordDate = dateFormater.parse(foodRecordDate);
                Date todayDate = dateFormater.parse(today);

                int compare = recordDate.compareTo(todayDate);

                if(compare == 0) foodItems++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();

        return foodItems;
    }

    public int foodTotalCalories(String today){

        Cursor cursor = null;
        int foodCalorieTotal = 0;

        SQLiteDatabase db = _handler.getReadableDatabase();

        cursor = db.rawQuery("select * from foods",null);

        SimpleDateFormat dateFormater=new SimpleDateFormat("MMM dd, yyyy");

        while(cursor.moveToNext()){
            String foodRecordDate = cursor.getString(cursor.getColumnIndex("food_log_date"));
            int foodCal = Integer.parseInt(cursor.getString(cursor.getColumnIndex("food_calorie")));

            try {
                Date recordDate = dateFormater.parse(foodRecordDate);
                Date todayDate = dateFormater.parse(today);

                int compare = recordDate.compareTo(todayDate);

                if(compare == 0){
                    foodCalorieTotal = foodCalorieTotal + foodCal;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();

        return foodCalorieTotal;
    }

    public ArrayList<String> getFoodHistoryList (){

        ArrayList<String> foodrecords = new ArrayList<>();

        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();
        cursor = db.rawQuery("select * from foods",null);

        while(cursor.moveToNext()){
            String foodName = cursor.getString(cursor.getColumnIndex("food_name"));
            String foodQty = cursor.getString(cursor.getColumnIndex("food_qty"));
            String foodCal = cursor.getString(cursor.getColumnIndex("food_calorie"));
            String foodRecordDate = cursor.getString(cursor.getColumnIndex("food_log_date"));

            String record = "\nFood Name: "+foodName+"\nFood Qty: "+foodQty+"\nFood Cal: "+foodCal+
                    "\nAdd Date: "+foodRecordDate;

            foodrecords.add(record);

        }

        Collections.reverse(foodrecords);

        cursor.close();
        db.close();
        return foodrecords;
    }

    public void insertReminderDate(String reminderType, String logType, long milliseconds){
        ContentValues values = new ContentValues();

        values.put(foodRemindersDB.LOG_TYPE, logType);
        values.put(foodRemindersDB.REMINDER_TYPE, reminderType);
        values.put(foodRemindersDB.WAIT_MILLISECONDS,String.valueOf(milliseconds));

        SQLiteDatabase db = _handler.getWritableDatabase();
        db.insert(foodRemindersDB.TABLE_FOOD_REMINDER,null, values);
        db.close();
    }

    public ArrayList<String> getLogInfo(){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getReadableDatabase();
        cursor = db.rawQuery("select * from "+foodRemindersDB.TABLE_FOOD_REMINDER,null);

        cursor.moveToLast();

        String logType = cursor.getString(cursor.getColumnIndex(foodRemindersDB.LOG_TYPE));
        String reminderType = cursor.getString(cursor.getColumnIndex(foodRemindersDB.REMINDER_TYPE));
        String milliseconds = cursor.getString(cursor.getColumnIndex(foodRemindersDB.WAIT_MILLISECONDS));

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
        db.execSQL("DELETE FROM "+foodRemindersDB.TABLE_FOOD_REMINDER);
        cursor.close();
        db.close();
    }

    public void deleteAllReminderType(String reminderType){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+foodRemindersDB.TABLE_FOOD_REMINDER+" WHERE "+
        foodRemindersDB.REMINDER_TYPE+" = "+reminderType);
        cursor.close();
        db.close();
    }

    public void deleteSpecificReminder(String reminderType, String logType, long milliseconds){
        Cursor cursor = null;
        SQLiteDatabase db = _handler.getWritableDatabase();
        db.execSQL("DELETE FROM "+foodRemindersDB.TABLE_FOOD_REMINDER+" WHERE "+
                foodRemindersDB.REMINDER_TYPE+" = "+reminderType + " AND "+foodRemindersDB.LOG_TYPE+
        " = "+logType + " AND "+foodRemindersDB.WAIT_MILLISECONDS + " = " + String.valueOf(milliseconds));
        cursor.close();
        db.close();
    }

}
