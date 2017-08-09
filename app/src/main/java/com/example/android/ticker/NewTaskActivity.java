package com.example.android.ticker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.ticker.data.TickerContract;

public class NewTaskActivity extends AppCompatActivity {

    public static RadioGroup mRadioGroup;
    public static EditText mTaskDescription;
    public static Button mCreateTaskButton;

    public static Context context;

    public static final String keyTask = TickerContract.TickerEntry.COLUMN_TASK_NAME;
    public static final String keyPriority = TickerContract.TickerEntry.COLUMN_PRIORITY;

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
                createTask(getBaseContext());
                Intent backIntent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

    public static void createTask(Context context) {
        String taskDescription = String.valueOf(mTaskDescription.getText());

        //Checks if the task description has been entered
        if(taskDescription.equals("")) {
            Toast.makeText(context, "Please enter task description", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checks if the priority is checked
        if(mRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, "Please select a priority", Toast.LENGTH_SHORT).show();
            return;
        }

        mTaskDescription.setText("");

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
        checkNumInserted();
    }

    public static void checkNumInserted() {
        String[] projection = {TickerContract.TickerEntry._ID};
        Cursor cursor = context.getContentResolver().query(TickerContract.TickerEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        cursor.close();
        Toast.makeText(context, "Task created", Toast.LENGTH_SHORT).show();
    }

}
