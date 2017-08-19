package com.example.android.ticker;

import android.content.Context;

import com.example.android.ticker.utilities.NotificationUtils;

/**
 * Created by ashwin on 8/19/17.
 */

public class ReminderTasks {

    public static final String ACTION_TASK_REMINDER = "task-reminder";

    public static void executeTask(Context context, String action) {
        if(action.equals(ACTION_TASK_REMINDER)) {
            issueTaskReminder(context);
        }
    }

    private static void issueTaskReminder(Context context) {
        NotificationUtils.remindUserAboutTask(context, "Sample");
    }

}
