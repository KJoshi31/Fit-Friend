package edu.bu.fitnessfriend.fitnessfriend.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.content.Context;

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
