package edu.bu.fitnessfriend.fitnessfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class exercise_reminder extends AppCompatActivity {

    private boolean radioButtonSelected = false;
    private String reminderType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);

        Button setExerciseReminderBtn = (Button) findViewById(R.id.set_ex_reminder_btn);
        setExerciseReminderBtn.setEnabled(false);
    }



    public void selectReminder(View view){
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton texts = (RadioButton) findViewById(R.id.notif_ex_radio_btn);

        switch (view.getId()){
            case R.id.notif_ex_radio_btn:
                if(checked){
                    radioButtonSelected = true;
                    reminderType = "notification";
                }
                break;

            case R.id.txt_ex_radio_btn:
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
