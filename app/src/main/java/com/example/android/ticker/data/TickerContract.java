package com.example.android.ticker.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ashwin on 8/1/17.
 */

/*
---------------------------------
Basic contract class for storing all the important variables for the database.
---------------------------------
 */

public class TickerContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.ticker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "task";

    public static final class TickerEntry implements BaseColumns {

        //First we created the main content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_TASKS)
                .build();

        //Then we declared the table name, and name of all the columns
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "task";
        public static final String COLUMN_PRIORITY = "priority";

    }

}