package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
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

}
