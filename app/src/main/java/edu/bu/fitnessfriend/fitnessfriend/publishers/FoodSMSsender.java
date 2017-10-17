package edu.bu.fitnessfriend.fitnessfriend.publishers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.bu.fitnessfriend.fitnessfriend.food.food_reminder;

public class FoodSMSsender extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        food_reminder.sendSmsMessage(context);
    }
}
