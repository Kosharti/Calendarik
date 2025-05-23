package com.example.calendarik

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val eventName = intent.getStringExtra("eventName") ?: "Event"

        val notification = NotificationCompat.Builder(context, "event_channel")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Event Reminder")
            .setContentText("Don't forget about $eventName!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(intent.getIntExtra("noteId", 0), notification)
    }
}