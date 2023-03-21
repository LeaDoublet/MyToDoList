package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    fun saveRecord(view: View) {
        val titre = findViewById<EditText>(R.id.u_titleNewTask).text.toString()
        val description = findViewById<EditText>(R.id.u_descriptionNewTask).text.toString()
        val databaseHandler= DatabaseHandler(this)
        val idMax = databaseHandler.Idmax()
        val todo = "TODO"

        if(titre.trim()!=""){
            val Taski = Task(idMax,todo,titre,description, 40,"10/03/2023")
            val status = databaseHandler.addTask(Taski)
            if(status > -1){
                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_titleNewTask).text.clear()
                findViewById<EditText>(R.id.u_descriptionNewTask).text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }
    }
}