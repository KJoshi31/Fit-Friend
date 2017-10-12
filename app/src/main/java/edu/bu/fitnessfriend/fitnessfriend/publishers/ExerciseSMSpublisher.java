package edu.bu.fitnessfriend.fitnessfriend.publishers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.bu.fitnessfriend.fitnessfriend.exercise.exercise_reminder;

public class ExerciseSMSpublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        exercise_reminder.sendSmsMessage(context);
    }
}
