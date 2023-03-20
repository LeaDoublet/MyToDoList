package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        var btnRetour = findViewById<Button>(R.id.btnRetour)
        btnRetour.setOnClickListener{
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}