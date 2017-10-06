package edu.bu.fitnessfriend.fitnessfriend.utilities;

import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by karan on 10/5/2017.
 */

public class date_utility {

    public static long getWaitTime(DateTime setDate){

        DateTime now = new DateTime();

        long difference = setDate.getMillis() - now.getMillis();

        Log.d("milliseconds",String.valueOf(difference));

        return difference;
    }

    public static boolean millisecondsPositive(long milliseconds){
         return  (milliseconds>0);
    }
}
