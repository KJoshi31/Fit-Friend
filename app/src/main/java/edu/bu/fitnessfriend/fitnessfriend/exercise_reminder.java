package edu.bu.fitnessfriend.fitnessfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class exercise_reminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder);

        Button setExerciseReminderBtn = (Button) findViewById(R.id.set_ex_reminder_btn);
        setExerciseReminderBtn.setEnabled(false);
    }
}
