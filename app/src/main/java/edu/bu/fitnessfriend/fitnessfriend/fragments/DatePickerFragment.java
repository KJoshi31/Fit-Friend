package edu.bu.fitnessfriend.fitnessfriend.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by karan on 10/4/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Activity thisActivity;
    private DatePickerDialog.OnDateSetListener thisListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        thisActivity = activity;

        try{
            thisListener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (Exception e){
            throw new ClassCastException(activity.toString()+ " must implement OnDateSetListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(thisActivity, thisListener, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
