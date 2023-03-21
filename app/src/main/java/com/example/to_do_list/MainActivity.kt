package com.example.to_do_list

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.content.Intent

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addTask = findViewById<ImageButton>(R.id.addTask)
        val goPageClothe = findViewById<ImageButton>(R.id.goPageClothe)
        val neckMain = findViewById<ImageView>(R.id.neckMain)
        val goViewModel = findViewById<FrameLayout>(R.id.frameLayout)

        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotification("coucou", "avez vous pensé a cette tache")
        goPageClothe.setOnClickListener{
            val intent = Intent(this@MainActivity, ChangeClothesActivity::class.java)
            startActivity(intent)
        }

        addTask.setOnClickListener{
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        goViewModel.setOnClickListener{
            val intent = Intent(this@MainActivity, ListTaskActivity::class.java)
            startActivity(intent)
        }

    }

}