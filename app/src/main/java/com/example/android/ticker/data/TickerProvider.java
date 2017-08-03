package com.example.android.ticker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.ticker.NewTaskActivity;

/**
 * Created by ashwin on 8/1/17.
 */

public class TickerProvider extends ContentProvider {

    TickerOpenHelper mTickerHelper;
    SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        mTickerHelper = new TickerOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        mDb = mTickerHelper.getReadableDatabase();
        Cursor cursor;

        cursor = mDb.query(TickerContract.TickerEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        mDb = mTickerHelper.getWritableDatabase();
        mDb.insert(TickerContract.TickerEntry.TABLE_NAME,
                    null,
                    contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        mDb = mTickerHelper.getWritableDatabase();
        int numRowsDeleted;
        if(s == null)
            s = "1";

        numRowsDeleted = mDb.delete(TickerContract.TickerEntry.TABLE_NAME,
                s, strings);

        if(numRowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return numRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

}
