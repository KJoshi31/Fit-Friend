package edu.bu.fitnessfriend.fitnessfriend.exercise;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import edu.bu.fitnessfriend.fitnessfriend.R;
import edu.bu.fitnessfriend.fitnessfriend.database.exerciseDatabaseUtils;
import edu.bu.fitnessfriend.fitnessfriend.database.myDatabaseHandler;
import edu.bu.fitnessfriend.fitnessfriend.fragments.DatePickerFragment;
import edu.bu.fitnessfriend.fitnessfriend.fragments.TimePickerFragment;
import edu.bu.fitnessfriend.fitnessfriend.permissionUtils;
import edu.bu.fitnessfriend.fitnessfriend.utilities.button_validation_utility;
import edu.bu.fitnessfriend.fitnessfriend.utilities.date_utility;
import edu.bu.fitnessfriend.fitnessfriend.utilities.misc_utility;

public class exercise_reminder extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private boolean radioButtonSelected = false;
    private String reminderType = "";

    private int _month = -1;
    private int _day = -1;
    private int _year = -1;

    private int _hour = -1;
    private int _minute = -1;

    private long millisecondsWait = new DateTime().getMillis();

    DateTime setDateTime = new DateTime();
    private String logType = "exercise";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);

        if(!permissionUtils.hasPhonePermissions(this)){
            permissionUtils.getPhonePermissions(this);
        }
    }



    public void selectReminder(View view){
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton texts = (RadioButton) findViewById(R.id.notif_ex_radio_btn);

        switch (view.getId()){
            case R.id.notif_ex_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "notification";
                }
                break;

            case R.id.txt_ex_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "text message";
                }
                break;
        }

        Log.d("Radio button selected?",String.valueOf(radioButtonSelected));
        Log.d("Reminder Type", reminderType);

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

        Log.d("dayOfMonth",String.valueOf(dayOfMonth));

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

    protected void setExerciseReminder(View v) {
        boolean hasPermissions = permissionUtils.hasPhonePermissions(getApplicationContext());

        millisecondsWait = date_utility.getWaitTime(setDateTime);
        boolean positiveTime = date_utility.millisecondsPositive(millisecondsWait);

        if (hasPermissions && positiveTime && radioButtonSelected) {
            misc_utility.successSetReminderSnackbar(v);

            Intent serviceIntent = new Intent(this, exercise_reminder_service.class);
            serviceIntent.putExtra("reminderType", reminderType);
            serviceIntent.putExtra("millis",millisecondsWait);
            serviceIntent.putExtra("logType",logType);


            myDatabaseHandler dbhandler = new myDatabaseHandler(getApplicationContext(),null,null,1);
            exerciseDatabaseUtils exerciseDatabaseUtils = new exerciseDatabaseUtils(dbhandler);
            exerciseDatabaseUtils.insertReminderDate(reminderType,"exercise",millisecondsWait);
            exerciseDatabaseUtils.insertLastLogged(logType);

            dbhandler.close();

            startService(serviceIntent);


            button_validation_utility.clearRadioGroup((RadioGroup)
                    findViewById(R.id.notif_type_ex_radio_group));
            radioButtonSelected = false;
            reminderType = "";
        } else {
            misc_utility.errorSetReminderSnackbar(v);

        }

    }

}
