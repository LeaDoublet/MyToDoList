package com.example.to_do_list

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.content.BroadcastReceiver

class BroadcastReceiver : BroadcastReceiver() {
    companion object{
        const val CHANNEL_ID = "ToDoListNotification"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        // Create the notification and show it
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val message = intent?.getStringExtra("message") ?: "Message"
        val title = intent?.getStringExtra("title") ?: "Titre"
        val id = intent?.getIntExtra("TASK_ID",0)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .build()
        println(title + " " + message)
        notificationManager.notify(1, builder)

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("NOTIFICATION",1)
        intent.putExtra("LATE_TASK",id)
    }

}