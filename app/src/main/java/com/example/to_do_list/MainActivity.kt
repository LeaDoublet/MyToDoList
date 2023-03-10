package com.example.to_do_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var addTaskBtn = findViewById<ImageButton>(R.id.imageButton)
        addTaskBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }
        var changeClothesBtn = findViewById<ImageButton>(R.id.imageButton3)
        changeClothesBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ChangeClothesActivity::class.java)
            startActivity(intent)
        }
    }
}