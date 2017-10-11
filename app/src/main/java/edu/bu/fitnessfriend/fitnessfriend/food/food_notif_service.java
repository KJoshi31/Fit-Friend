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
 * Created by karan on 10/9/2017.
 */

public class food_notif_service extends Service {

    String reminderType = "";
    String logType = "";
    long waitMillis = 0L;
    static ArrayList<Thread> threadArrayList = new ArrayList<>();

    public food_notif_service(){
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.d("active threads",String.valueOf(Thread.activeCount()));

        final int currentId = startId;

        if(intent == null){
            myDatabaseHandler foodHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
            serviceDatabaseUtils serviceDatabaseUtils = new serviceDatabaseUtils(foodHandler);

            waitMillis = Long.valueOf(serviceDatabaseUtils.getFoodNotifWait());
            foodHandler.close();
        }else{
            waitMillis = Long.valueOf(intent.getLongExtra("millis",0L));
        }

        Log.d("waitMillis",String.valueOf(waitMillis));

        Runnable r = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        //SystemClock.sleep(waitMillis);
                        wait(waitMillis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notificationService();
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


    public void notificationService(){


        Notification reminderNotification = new Notification.Builder(this)
                .setContentTitle("Fitness Friend-Food Reminder")
                .setContentText("Reminder to log food calories!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{0,175})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        final NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1,reminderNotification);

    }


}
