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

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static java.lang.Thread.sleep;

/**
 * Created by karan on 10/4/2017.
 */

public class reminder_service extends Service {

    String reminderType = "";
    String logType = "";
    long waitMillis = 0L;

    public reminder_service(){

    }

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        final int currentId = startId;

        logType = intent.getStringExtra("logType");
        reminderType = intent.getStringExtra("reminderType");
        waitMillis = Long.valueOf(intent.getLongExtra("millis",0L));

        Log.d("Service reminderType",reminderType);
        Log.d("waitMillis",String.valueOf(waitMillis));
        waitMillis = Long.valueOf(intent.getLongExtra("millis",0L));

        Runnable r = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try{
                        SystemClock.sleep(waitMillis);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(reminderType.equals("text message")){
                        Log.d("hitting message","hitting sms");
                        sendSmsMessage(getApplicationContext(), logType);
                    }else{

                    }
                }

               stopSelf();
            }
        };

        Thread thread = new Thread(r);
        thread.start();



        return Service.START_STICKY;
    }


    public static void sendSmsMessage(Context c, String logType){
        String message = "";
        if(logType.equals("food")){
            message = "Friendly reminder from Fitness Friend\n" +
                    "Please log your foods";
        }else if(logType.equals("exercise")){
            message = "Friendly reminder from Fitness Friend\n" +
                    "Please log your exercises";
        }else{
            message = "Friendly reminder from Fitness Friend\n" +
                    "Please log your foods & exercises";
        }

        Log.d("hi","from sms");

        TelephonyManager manager = (TelephonyManager)c.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String phoneNumber = manager.getLine1Number();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,null,null);
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


    @Override
    public IBinder onBind(Intent intent) {
        logType = intent.getStringExtra("logType");
        reminderType = intent.getStringExtra("reminderType");
        waitMillis = Long.valueOf(intent.getLongExtra("millis",0L));
        return null;
    }

    @Override
    public void onDestroy(){

    }
}
