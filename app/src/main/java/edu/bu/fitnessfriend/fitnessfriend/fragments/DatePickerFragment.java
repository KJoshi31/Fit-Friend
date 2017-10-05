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

    private Activity mActivity;
    private DatePickerDialog.OnDateSetListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        mActivity = activity;

        try{
            mListener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (Exception e){
            throw new ClassCastException(activity.toString()+ " must implement OnDateSetListener");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(mActivity,mListener, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
