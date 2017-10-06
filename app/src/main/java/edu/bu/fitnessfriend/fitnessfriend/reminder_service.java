package edu.bu.fitnessfriend.fitnessfriend;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by karan on 10/4/2017.
 */

public class reminder_service {


    public static void sendSmsMessage(Context c){
        TelephonyManager manager = (TelephonyManager)c.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String phoneNumber = manager.getLine1Number();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,"hi",null,null);
    }

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
