package edu.bu.fitnessfriend.fitnessfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class food_reminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reminder);

        Button setFoodReminderBtn = (Button) findViewById(R.id.set_food_reminder_btn);
        setFoodReminderBtn.setEnabled(false);
    }
}
