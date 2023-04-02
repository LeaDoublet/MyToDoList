package com.example.to_do_list

import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "MyChannelID"

    val calendar = Calendar.getInstance()


    fun createNotification(title : String,task : Task) {
        var barrage = false
        if (task.deadline != "false" && task.deadline != "" && !barrage ){


            val dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val notifDate = LocalDate.parse(task.deadline, dateFormat)

            calendar.set(Calendar.YEAR, notifDate.year)
            calendar.set(Calendar.MONTH, notifDate.monthValue)
            calendar.set(Calendar.DAY_OF_MONTH, notifDate.dayOfMonth)

            calendar.set(Calendar.HOUR_OF_DAY, 15)
            calendar.set(Calendar.MINUTE, 42)
            calendar.set(Calendar.SECOND, 0)

            val intent = Intent(context, BroadcastReceiver::class.java)
            intent.putExtra("title", title)
            intent.putExtra("message", "Cette tâche doit être éffectuer aujourd'hui !")

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager : AlarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            println("Tâche bien ajouté")
        }
    }
}
