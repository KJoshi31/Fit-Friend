package edu.bu.fitnessfriend.fitnessfriend.food;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.bu.fitnessfriend.fitnessfriend.food.food_sms_service;

public class FoodSMSpublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        food_sms_service.sendSmsMessage(context);
    }
}
