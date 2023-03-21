package com.example.to_do_list

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.content.Intent

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addTask = findViewById<ImageButton>(R.id.addTask)
        val goPageClothe = findViewById<ImageButton>(R.id.goPageClothe)
        val goViewModel = findViewById<ImageButton>(R.id.imagecat)

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