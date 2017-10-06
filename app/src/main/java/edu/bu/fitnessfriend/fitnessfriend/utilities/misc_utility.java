package edu.bu.fitnessfriend.fitnessfriend.utilities;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by karan on 10/6/2017.
 */

public class misc_utility {

    public static void errorSetReminderSnackbar(View v){
        Snackbar errorSnackbar = Snackbar.make(v,"Error, please try again ",0);
        errorSnackbar.show();
    }

    public static void successSetReminderSnackbar(View v){
        Snackbar successSnackbar = Snackbar.make(v,"Success, reminder set",0);
        successSnackbar.show();;
    }


}
