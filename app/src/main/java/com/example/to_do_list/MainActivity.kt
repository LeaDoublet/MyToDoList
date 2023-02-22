package com.example.to_do_list

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addTask = findViewById<ImageButton>(R.id.addTask)
        val goPageClothe = findViewById<ImageButton>(R.id.goPageClothe)

        goPageClothe.setOnClickListener{
            setContentView(R.layout.activity_change_clothe)
        }

    }
}