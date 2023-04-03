package com.example.to_do_list

import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationHelper(private val context: Context) {



    private val calendar: Calendar = Calendar.getInstance()

    fun createNotification(title : String,task : Task) {
        if (task.deadline.isNotBlank()){

            val channel = NotificationChannel (
                BroadcastReceiver.CHANNEL_ID,
                "ToDo",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val notifDate = LocalDate.parse(task.deadline, dateFormat)

            calendar.set(Calendar.YEAR, notifDate.year)
            calendar.set(Calendar.MONTH, notifDate.monthValue)
            calendar.set(Calendar.DAY_OF_MONTH, notifDate.dayOfMonth)

            calendar.set(Calendar.HOUR_OF_DAY, 17)
            calendar.set(Calendar.MINUTE, 21)
            calendar.set(Calendar.SECOND, 0)

            val intent = Intent(context, BroadcastReceiver::class.java)
            intent.putExtra("title", title)
            intent.putExtra("message", "Cette tâche doit être effectuer aujourd'hui !")
            intent.putExtra("TASK_ID", task.id)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                task.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            channel.description = "Utiliser afin de montrer les différentes notifications à l'utilisateurs"
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
