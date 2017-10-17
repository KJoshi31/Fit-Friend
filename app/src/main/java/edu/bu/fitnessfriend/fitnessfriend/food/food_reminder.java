package edu.bu.fitnessfriend.fitnessfriend.food;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import edu.bu.fitnessfriend.fitnessfriend.publishers.FoodSMSsender;
import edu.bu.fitnessfriend.fitnessfriend.publishers.NotificationSender;
import edu.bu.fitnessfriend.fitnessfriend.R;
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

    DateTimeFormatter twelveHourFormat = DateTimeFormat.forPattern("hh:mm aa");
    DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MMM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reminder);

        if(!permissionUtils.hasPhonePermissions(this)){
            permissionUtils.getPhonePermissions(this);
        }

        TextView dateLabel = (TextView)findViewById(R.id.food_date_lbl);
        TextView timeLabel = (TextView)findViewById(R.id.food_time_lbl);


        dateLabel.setText("Date Selected: "+dateFormat.print(setDateTime));
        timeLabel.setText("Time Selected: "+twelveHourFormat.print(setDateTime));


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

        TextView dateLabel = (TextView)findViewById(R.id.food_date_lbl);
        dateLabel.setText("Date Selected: "+dateFormat.print(setDateTime));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _hour = hourOfDay;
        _minute = minute;

        setDateTime = setDateTime.withHourOfDay(_hour)
                .withMinuteOfHour(_minute);

        TextView timeLabel = (TextView)findViewById(R.id.food_time_lbl);
        timeLabel.setText("Time Selected: "+twelveHourFormat.print(setDateTime));
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

                scheduleNotification(getApplicationContext(),millisecondsWait,notifCounter);

            }else{

                serviceDatabaseUtils.insertFoodSMSReminder(millisecondsWait);

                scheduleSmsNotification(getApplicationContext(),millisecondsWait);

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

    private void scheduleSmsNotification(Context context, long millisecondsDelay){

        Intent smsSendIntent = new Intent(context,FoodSMSsender.class);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context,notifCounter,smsSendIntent,PendingIntent.FLAG_ONE_SHOT);

        long waitTime = SystemClock.elapsedRealtime()+millisecondsDelay;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,waitTime , pendingIntent);
    }

    private void scheduleNotification(Context context, long millisecondsDelay,int notificationCounter){

        //code below handles notifications with NotificationSender

        int notificationID = notificationCounter;

        Notification smsNotification = getNotification(notificationID);

        Log.d("notification ID",String.valueOf(notificationID));

        Intent notificationIntent = new Intent(context, NotificationSender.class);

        notificationIntent.putExtra("notification",smsNotification);
        notificationIntent.putExtra("notification_id",notificationID);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context,notificationID,notificationIntent,PendingIntent.FLAG_ONE_SHOT);

        long waitTime = SystemClock.elapsedRealtime()+millisecondsDelay;

        Log.d("millisecondsDelay",String.valueOf(waitTime));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, waitTime, pendingIntent);

    }


    private Notification getNotification(int notificationID){

        Intent logFoodIntent = new Intent(this, add_food.class);
        Intent viewFoodHistoryIntent = new Intent(this,food_history.class);

        Log.d("Notif ID",String.valueOf(notificationID));

        logFoodIntent.putExtra("notification_id",notificationID);
        viewFoodHistoryIntent.putExtra("notification_id",notificationID);


        PendingIntent logFoodPending = PendingIntent
                .getActivity(this,0,logFoodIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent viewFoodPending = PendingIntent
                .getActivity(this,1,viewFoodHistoryIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


        Notification.Action LogFoodsNotifButton =
                new Notification.Action.Builder(
                        Icon.createWithResource(this,R.mipmap.ic_launcher)
                        ,"Log Food Calories",logFoodPending).build();

        Notification.Action ViewFoodHistoryButton =
                new Notification.Action.Builder(
                        Icon.createWithResource(this,R.mipmap.ic_launcher)
                        ,"Food History",viewFoodPending).build();


        Notification reminderNotification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.spoon_fork)
                .setLargeIcon(Icon.createWithResource(this,R.mipmap.eat))
                .setContentTitle("Fitness Friend-Food Reminder")
                .setContentText("Reminder to log food calories!")
                .setVibrate(new long[]{0,175})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(logFoodPending)
                .setAutoCancel(true)
                .addAction(ViewFoodHistoryButton)
                .addAction(LogFoodsNotifButton)
                .build();

        return reminderNotification;
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
