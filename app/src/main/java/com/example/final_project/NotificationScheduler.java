package com.example.final_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationScheduler {

    public static void scheduleNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction("com.example.final_project.ACTION_SHOW_NOTIFICATION");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Schedule the alarm after 5 minutes
        long triggerTime = System.currentTimeMillis() +  1000; // 5 minutes in milliseconds

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }
}