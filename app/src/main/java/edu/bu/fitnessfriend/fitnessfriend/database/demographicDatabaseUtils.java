package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Date;

import edu.bu.fitnessfriend.fitnessfriend.model.demographic;

/**
 * Created by karan on 9/24/2017.
 */

public class demographicDatabaseUtils {

    private myDatabaseHandler.demographicDB demographicDatabase = null;
    private myDatabaseHandler _handler;

    public demographicDatabaseUtils(myDatabaseHandler handler){
        _handler = handler;
    }

    public void insertDemoInfo(demographic demo){


        ContentValues values = new ContentValues();
        values.put(demographicDatabase.DEMOGRAPHIC_NAME,demo.getName());
        values.put(demographicDatabase.DEMOGRAPHIC_WEIGHT, demo.getWeight());
        values.put(demographicDatabase.DEMOGRAPHIC_AGE, demo.getAge());
        values.put(demographicDatabase.DEMOGRAPHIC_FEET, demo.getFeet());
        values.put(demographicDatabase.DEMOGRAPHIC_INCH, demo.getInch());
        values.put(demographicDatabase.DEMOGRAPHIC_ACTIVITY_LVL, demo.getActivitylvl());


        SQLiteDatabase db = _handler.getWritableDatabase();

        db.insert(demographicDatabase.TABLE_DEMOGRAPH,null, values);
        db.close();
    }
}
