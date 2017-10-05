package edu.bu.fitnessfriend.fitnessfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class food_reminder extends AppCompatActivity {

    private boolean radioButtonSelected = false;
    private String reminderType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reminder);

        Button setFoodReminderBtn = (Button) findViewById(R.id.set_food_reminder_btn);
        setFoodReminderBtn.setEnabled(false);
    }


    public void selectReminder(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.notif_food_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "notification";
                }
                break;

            case R.id.txt_food_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "text message";
                }
                break;
        }

        Log.d("Radio button selected?",String.valueOf(radioButtonSelected));
        Log.d("Reminder Type", reminderType);

    }


}
