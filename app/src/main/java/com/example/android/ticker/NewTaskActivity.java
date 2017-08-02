package com.example.android.ticker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    public static RadioGroup mRadioGroup;
    public static EditText mTaskDescription;
    public static Button mCreateTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mTaskDescription = (EditText) findViewById(R.id.et_enter_task);

        mCreateTaskButton = (Button) findViewById(R.id.button_save);
        mCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });
    }

    public static void createTask() {
        String taskDescription = String.valueOf(mTaskDescription.getText());
        mTaskDescription.setText("");

        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_high:
                Log.i("RB", "High");
                break;

            case R.id.rb_medium:
                Log.i("RB", "Medium");
                break;

            case R.id.rb_low:
                Log.i("RB", "Low");
                break;

            default:
                throw new IllegalArgumentException("Radiobutton not valid");
        }
        mRadioGroup.clearCheck();
        Log.i("Task", taskDescription);
    }

}
