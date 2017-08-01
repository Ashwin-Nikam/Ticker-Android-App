package com.example.android.ticker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    public Button mCreateTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mCreateTaskButton = (Button) findViewById(R.id.button_save);
    }

    public void create_task(View view) {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }
}
