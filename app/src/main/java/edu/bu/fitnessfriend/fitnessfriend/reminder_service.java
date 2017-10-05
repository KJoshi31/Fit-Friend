package edu.bu.fitnessfriend.fitnessfriend;

import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;
import android.view.View;

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
}
