package edu.bu.fitnessfriend.fitnessfriend.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karan on 9/24/2017.
 */

public class myDatabaseHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "FitnessFriend.db";


    protected class foodDB{
        public static final String TABLE_FOOD = "foods";
        public static final String FOOD_ID = "id";
        public static final String FOOD_NAME = "food_name";
        public static final String FOOD_QTY = "food_qty";
        public static final String FOOD_CAL = "food_calorie";
        public static final String FOOD_DATE = "food_log_date";
    }

    protected class exerciseDB{
        public static final String TABLE_EXERCISE = "exercises";
        public static final String EXERCISE_ID = "id";
        public static final String EXERCISE_NAME = "exercise_name";
        public static final String EXERCISE_CAL = "exercise_calorie";
        public static final String EXERCISE_DURATION = "exercise_duration";
        public static final String EXERCISE_UNIT = "exercise_unit";
        public static final String EXERCISE_DATE = "exercise_log_date";
    }

    protected class demographicDB{
        public static final String TABLE_DEMOGRAPH = "demographics";
        public static final String DEMOGRAPHIC_ID = "id";
        public static final String DEMOGRAPHIC_NAME = "name";
        public static final String DEMOGRAPHIC_WEIGHT = "weight";
        public static final String DEMOGRAPHIC_AGE = "age";
        public static final String DEMOGRAPHIC_FEET = "feet";
        public static final String DEMOGRAPHIC_INCH = "inch";
        public static final String DEMOGRAPHIC_ACTIVITY_LVL = "activity_level";
    }

    public myDatabaseHandler(Context context, String name ,
                             SQLiteDatabase.CursorFactory factory, int version){
        super(context,DB_NAME,factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOOD_TABLE = "CREATE TABLE "+foodDB.TABLE_FOOD+
                "("+foodDB.FOOD_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                foodDB.FOOD_NAME + " TEXT, "+ foodDB.FOOD_QTY+" REAL, "+
                foodDB.FOOD_CAL+ " REAL, " + foodDB.FOOD_DATE + " TEXT "+")";

        String CREATE_EXERCISE_TABLE = "CREATE TABLE "+exerciseDB.TABLE_EXERCISE+
                "("+exerciseDB.EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                exerciseDB.EXERCISE_NAME + " TEXT, " + exerciseDB.EXERCISE_CAL + " REAL, "+
                exerciseDB.EXERCISE_DURATION + " REAL, "+ exerciseDB.EXERCISE_UNIT + " TEXT, "+
                exerciseDB.EXERCISE_DATE + " TEXT "+")";

        String CREATE_DEMOGRAPHIC_TABLE = "CREATE TABLE "+demographicDB.TABLE_DEMOGRAPH+
                "("+demographicDB.DEMOGRAPHIC_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                demographicDB.DEMOGRAPHIC_NAME+ " TEXT, "+ demographicDB.DEMOGRAPHIC_WEIGHT +
                " INTEGER, " + demographicDB.DEMOGRAPHIC_AGE + "INTEGER, "+demographicDB.DEMOGRAPHIC_FEET+
                " INTEGER, "+ demographicDB.DEMOGRAPHIC_INCH + " INTEGER, "+ demographicDB.DEMOGRAPHIC_ACTIVITY_LVL+
                " TEXT "+")";

        db.execSQL(CREATE_DEMOGRAPHIC_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+demographicDB.TABLE_DEMOGRAPH);
        db.execSQL("DROP TABLE IF EXISTS "+foodDB.TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS "+exerciseDB.TABLE_EXERCISE);
        onCreate(db);
    }
}
