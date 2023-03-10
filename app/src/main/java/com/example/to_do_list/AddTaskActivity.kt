package com.example.to_do_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        var tfTitle = findViewById<EditText>(R.id.et1)
        var tfContent = findViewById<EditText>(R.id.et2)
        var btnSavTask = findViewById<Button>(R.id.btnSavTask)
        var btnRetour = findViewById<Button>(R.id.btnRetour)
        btnSavTask.setOnClickListener {
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
            var taskCreated = Task(tfTitle.text, Color.TASK1, tfContent.text, State.TODO, null)
            Toast.makeText(this@AddTaskActivity, "Task saved.", Toast.LENGTH_SHORT).show()
            println("test")
        }
        btnRetour.setOnClickListener{
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }




}