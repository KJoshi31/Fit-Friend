package edu.bu.fitnessfriend.fitnessfriend.food;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Random;

import edu.bu.fitnessfriend.fitnessfriend.NotificationPublisher;
import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.foodDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.database.serviceDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.fragments.DatePickerFragment;
import edu.bu.fitnessfriend.fitnessfriend.fragments.TimePickerFragment;
import edu.bu.fitnessfriend.fitnessfriend.utilities.permissionUtils;
import edu.bu.fitnessfriend.fitnessfriend.utilities.button_validation_utility;
import edu.bu.fitnessfriend.fitnessfriend.utilities.date_utility;
import edu.bu.fitnessfriend.fitnessfriend.utilities.misc_utility;

public class food_reminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private boolean radioButtonSelected = false;
    private String reminderType = "";

    private int _month = -1;
    private int _day = -1;
    private int _year = -1;

    private int _hour = -1;
    private int _minute = -1;

    private long millisecondsWait = new DateTime().getMillis();

    private int notifCounter = 0;

    DateTime setDateTime = new DateTime();
    private String logType = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reminder);

        if(!permissionUtils.hasPhonePermissions(this)){
            permissionUtils.getPhonePermissions(this);
        }
    }


    public void selectReminder(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.notif_food_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "notification";
                }
                break;

            case R.id.txt_food_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "text message";
                }
                break;
        }

    }

    public void showTimePicker(View v){
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(),"time picker");
    }

    public void showDatePicker(View v){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(),"date picker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _month = month;
        _day = dayOfMonth;
        _year = year;

        setDateTime = setDateTime.withMonthOfYear(_month+1)
                .withDayOfMonth(_day)
                .withYear(_year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _hour = hourOfDay;
        _minute = minute;

        setDateTime = setDateTime.withHourOfDay(_hour)
                .withMinuteOfHour(_minute);
    }

    protected void setFoodReminder(View v){
        boolean hasPermissions = permissionUtils.hasPhonePermissions(getApplicationContext());

        millisecondsWait = date_utility.getWaitTime(setDateTime);
        boolean positiveTime = date_utility.millisecondsPositive(millisecondsWait);



        if(hasPermissions && positiveTime && radioButtonSelected){
            misc_utility.successSetReminderSnackbar(v);
            notifCounter++;


            //need to store reminderType, millisecondsWait, logType in the db
            //because if the app is closed, the service cant call the intent
            //to get the information

            myDatabaseHandler dbhandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
            serviceDatabaseUtils serviceDatabaseUtils = new serviceDatabaseUtils(dbhandler);


            //stopService(serviceIntent);
            Log.d("threads running",String.valueOf(Thread.activeCount()));

            if(reminderType.equals("notification")){

                serviceDatabaseUtils.insertFoodNotifReminder(millisecondsWait);

                Intent foodNotificationIntent = new Intent(this, food_notif_service.class);
                foodNotificationIntent.putExtra("millis",millisecondsWait);

                scheduleNotification(getApplicationContext(),millisecondsWait,notifCounter);

                //startService(foodNotificationIntent);


            }else{
                serviceDatabaseUtils.insertFoodSMSReminder(millisecondsWait);

                Intent smsFoodIntent = new Intent(this, food_sms_service.class);
                smsFoodIntent.putExtra("millis",millisecondsWait);

                startService(smsFoodIntent);
            }






            button_validation_utility.clearRadioGroup((RadioGroup)
                    findViewById(R.id.notif_type_food_radio_group));
            radioButtonSelected = false;
            reminderType = "";

            dbhandler.close();


        }else{
            misc_utility.errorSetReminderSnackbar(v);

        }


    }

    private void scheduleNotification(Context context, long delay,int notificationCounter){
        Notification smsNotification = getNotification();

        //code below handles notifications with NotificationPublisher

        int notificationID = notificationCounter;

        Log.d("notification ID",String.valueOf(notificationID));

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);

        notificationIntent.putExtra("notification",smsNotification);
        notificationIntent.putExtra("notification_id",notificationID);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context,notificationID,notificationIntent,PendingIntent.FLAG_ONE_SHOT);

        long waitTime = SystemClock.elapsedRealtime()+delay;

        Log.d("wait time delay",String.valueOf(waitTime));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, waitTime, pendingIntent);

    }


    private Notification getNotification(){

        Intent logFoodIntent = new Intent(this, add_food.class);

        PendingIntent logFood = PendingIntent.getActivity(this,0,logFoodIntent,PendingIntent.FLAG_ONE_SHOT);

        Notification reminderNotification = new Notification.Builder(this)
                .setContentTitle("Fitness Friend-Food Reminder")
                .setContentText("Reminder to log food calories!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{0,175})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(logFood)
                .setAutoCancel(true)
                .build();

        return reminderNotification;
    }


}
