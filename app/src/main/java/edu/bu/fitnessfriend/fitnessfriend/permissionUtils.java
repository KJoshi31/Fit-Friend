package edu.bu.fitnessfriend.fitnessfriend;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.bu.fitnessfriend.fitnessfriend.database.exerciseDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.miscDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static java.lang.Thread.sleep;

/**
 * Created by karan on 10/4/2017.
 */

public class permissionUtils {

    public static boolean hasPhonePermissions(Context c){
        int sms = ContextCompat.checkSelfPermission(c, Manifest.permission.SEND_SMS);
        int phone = ContextCompat.checkSelfPermission(c,Manifest.permission.READ_PHONE_STATE);

        return (sms == PackageManager.PERMISSION_GRANTED && phone == PackageManager.PERMISSION_GRANTED);

    }

    public static void getPhonePermissions(Activity activity){
        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
        requestPermissions(activity,permissions,1);
    }

}
