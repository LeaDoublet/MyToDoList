package com.example.to_do_list

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class BroadcastReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Create the notification and show it
        println("class bien appelé")
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val message = intent?.getStringExtra("message") ?: "Message"
        val title = intent?.getStringExtra("title") ?: "Titre"
        val builder = NotificationCompat.Builder(context, "my_channel_id")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
        notificationManager.notify(0, builder.build())
    }

}