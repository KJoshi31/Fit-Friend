package edu.bu.fitnessfriend.fitnessfriend.food;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.database.serviceDatabaseUtils;

/**
 * Created by karan on 10/8/2017.
 */

public class food_sms_service extends Service {

    String reminderType = "";
    String logType = "";
    long waitMillis = 0L;

    public food_sms_service(){
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.d("active threads",String.valueOf(Thread.activeCount()));

        final int currentId = startId;

        if(intent == null){
            myDatabaseHandler foodHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
            serviceDatabaseUtils serviceDatabaseUtils = new serviceDatabaseUtils(foodHandler);

            waitMillis = Long.valueOf(serviceDatabaseUtils.getFoodSMSWait());
            foodHandler.close();
        }else{
            waitMillis = Long.valueOf(intent.getLongExtra("millis",0L));
        }

        Log.d("waitMillis",String.valueOf(waitMillis));

        Runnable r = new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try{
                        //SystemClock.sleep(waitMillis);
                        wait(waitMillis);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                        Log.d("hitting message","hitting sms");
                        sendSmsMessage(getApplicationContext());

                }
                stopSelf();
            }
        };


        Thread thread = new Thread(r);
        thread.start();



        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    public static void sendSmsMessage(Context c){
        String message = "Friendly reminder from Fitness Friend\n" +
                    "Please log your food calories";


        TelephonyManager manager = (TelephonyManager)c.getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        String phoneNumber = manager.getLine1Number();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,null,null);
    }

}
