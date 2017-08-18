package com.example.android.ticker.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.android.ticker.MainActivity;
import com.example.android.ticker.R;

/**
 * Created by ashwin on 8/16/17.
 */

public class NotificationUtils {

    private static final int PENDING_INTENT_ID = 007;
    private static final int NOTIFICATION_ID = 777;

//    This method is used for creating a pending intent which is going to be used
//    by the notification manager to launch the MainActivity when clicked.

    public static PendingIntent createPendingIntent(Context context) {
        Intent intentToStartActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(context,
                PENDING_INTENT_ID, intentToStartActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void remindUserAboutTask(Context context) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle("Title")
                .setSmallIcon(R.drawable.ic_plus)
                .setContentText("This is the content")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is the content"))
                .setContentIntent(createPendingIntent(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

}
