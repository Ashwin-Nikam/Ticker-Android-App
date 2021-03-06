package com.example.android.ticker.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.android.ticker.MainActivity;
import com.example.android.ticker.R;
import com.example.android.ticker.data.TickerContract;

/**
 * Created by ashwin on 8/16/17.
 */

public class NotificationUtils {

    private static final int NOTIFICATION_ID = 777;

//    This method is used for creating a pending intent which is going to be used
//    by the notification manager to launch the MainActivity when clicked.

    public static PendingIntent createPendingIntent(Context context) {

        Intent intentToStartActivity = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(intentToStartActivity);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void remindUserAboutTask(Context context) {
        String task = getLatestTask(context);
        if(task.equals("")) return;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle("You have pending tasks")
                .setSmallIcon(R.drawable.ic_priority)
                .setContentText(task)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(task))
                .setContentIntent(createPendingIntent(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public static String getLatestTask(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(TickerContract.TickerEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        TickerContract.TickerEntry.COLUMN_PRIORITY);
        cursor.moveToFirst();
        if(cursor.getCount() != 0) {
            String task = cursor.getString(cursor.getColumnIndex(TickerContract.TickerEntry.COLUMN_TASK_NAME));
            return task;
        } else
            return "";
    }

}
