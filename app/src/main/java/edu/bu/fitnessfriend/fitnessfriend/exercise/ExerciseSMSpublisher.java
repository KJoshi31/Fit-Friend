package edu.bu.fitnessfriend.fitnessfriend.exercise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExerciseSMSpublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        exercise_sms_service.sendSmsMessage(context);
    }
}
