package com.example.to_do_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var addTaskBtn = findViewById<ImageButton>(R.id.imageButton)
        addTaskBtn.setOnClickListener(
            setContentView(R.layout.activity_add_task)
        )
    }
}