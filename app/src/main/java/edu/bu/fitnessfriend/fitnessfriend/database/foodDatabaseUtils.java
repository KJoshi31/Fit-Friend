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

        ArrayList<String> reverseFoodList = new ArrayList<>();

         Collections.reverse(foodrecords);




        cursor.close();
        return foodrecords;
    }

}
