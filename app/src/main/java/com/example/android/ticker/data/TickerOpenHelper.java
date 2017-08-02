package com.example.android.ticker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashwin on 8/1/17.
 */

public class TickerOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tasks.db";

    private static final String CREATE_STATEMENT = "CREATE TABLE " +
            TickerContract.TickerEntry.TABLE_NAME + "(" +
            TickerContract.TickerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            TickerContract.TickerEntry.COLUMN_TASK_NAME + " STRING NOT NULL, "+
            TickerContract.TickerEntry.COLUMN_PRIORITY + "INTEGER NOT NULL" +");";


    public TickerOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TickerContract.TickerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
