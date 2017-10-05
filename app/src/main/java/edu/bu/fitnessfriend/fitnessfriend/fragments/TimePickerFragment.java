package edu.bu.fitnessfriend.fitnessfriend.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import android.text.format.DateFormat;
import java.util.Calendar;

/**
 * Created by karan on 10/4/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Activity mActivity;
    private TimePickerDialog.OnTimeSetListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mActivity = activity;
        try{
            mListener = (TimePickerDialog.OnTimeSetListener) activity;
        }catch (Exception e){
            throw new ClassCastException(activity.toString()+ " must implement OnTimeSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(mActivity,mListener, hour,minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
