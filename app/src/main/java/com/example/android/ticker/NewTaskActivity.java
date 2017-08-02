package com.example.android.ticker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.ticker.data.TickerContract;

public class NewTaskActivity extends AppCompatActivity {

    public static RadioGroup mRadioGroup;
    public static EditText mTaskDescription;
    public static Button mCreateTaskButton;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        context = this;

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

        String keyTask = "Task";
        String keyPriority = "Priority";

        ContentValues contentValues = new ContentValues();

        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_high:
                contentValues.put(keyTask, taskDescription);
                contentValues.put(keyPriority, "High");
                break;

            case R.id.rb_medium:
                contentValues.put(keyTask, taskDescription);
                contentValues.put(keyPriority, "Medium");
                break;

            case R.id.rb_low:
                contentValues.put(keyTask, taskDescription);
                contentValues.put(keyPriority, "Low");
                break;

            default:
                throw new IllegalArgumentException("Radio-Button not valid");
        }
        mRadioGroup.clearCheck();
        context.getContentResolver().insert(TickerContract.TickerEntry.CONTENT_URI, contentValues);
    }

}
