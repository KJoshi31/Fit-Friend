package edu.bu.fitnessfriend.fitnessfriend;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import edu.bu.fitnessfriend.fitnessfriend.fragments.DatePickerFragment;
import edu.bu.fitnessfriend.fitnessfriend.fragments.TimePickerFragment;

public class exercise_reminder extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private boolean radioButtonSelected = false;
    private String reminderType = "";

    private int _month = -1;
    private int _day = -1;
    private int _year = -1;

    private int _hour = -1;
    private int _minute = -1;

    DateTime setDateTime = new DateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);

        Button setExerciseReminderBtn = (Button) findViewById(R.id.set_ex_reminder_btn);
        setExerciseReminderBtn.setEnabled(false);

        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
        requestPermissions(permissions,1);
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

        Log.d("month",String.valueOf(setDateTime.getMonthOfYear()));
        Log.d("day",String.valueOf(setDateTime.getDayOfMonth()));
        Log.d("year",String.valueOf(setDateTime.getYear()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _hour = hourOfDay;
        _minute = minute;

        setDateTime = setDateTime.withHourOfDay(_hour)
                .withMinuteOfHour(_minute);

        Log.d("hour",String.valueOf(setDateTime.getHourOfDay()));
        Log.d("minute",String.valueOf(setDateTime.getMinuteOfHour()));


    }

}
