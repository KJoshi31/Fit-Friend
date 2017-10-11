package edu.bu.fitnessfriend.fitnessfriend.exercise;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.database.serviceDatabaseUtils;

/**
 * Created by karan on 10/10/2017.
 */

public class exercise_notif_service extends Service {
    String reminderType = "";
    String logType = "";
    long waitMillis = 0L;
    static ArrayList<Thread> threadArrayList = new ArrayList<>();

    public exercise_notif_service(){
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.d("active threads",String.valueOf(Thread.activeCount()));

        final int currentId = startId;

        if(intent == null){
            myDatabaseHandler exerciseHandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
            serviceDatabaseUtils serviceDatabaseUtils = new serviceDatabaseUtils(exerciseHandler);

            waitMillis = Long.valueOf(serviceDatabaseUtils.getExNotifWait());
            exerciseHandler.close();
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
                .setContentTitle("Fitness Friend-Exercise Reminder")
                .setContentText("Reminder to log exercise calories!")
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
