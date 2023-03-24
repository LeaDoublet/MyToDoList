package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.databinding.ActivityDeleteTaskBinding

class DeleteTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeleteTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnYes_Delete = findViewById<ImageView>(R.id.yes_delete)
        val btnNo_Delete = findViewById<ImageView>(R.id.no_delete)

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val taskID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        println("Test de verif " + taskID)
        val task = TaskFromID(taskID,this)

        btnNo_Delete.setOnClickListener{
            val intent = Intent(this@DeleteTaskActivity, DetailActivity::class.java)
            intent.putExtra(BOOK_ID_EXTRA, taskID)
            startActivity(intent)
        }

        btnYes_Delete.setOnClickListener{
            if(task != null)
            {
                val status = databaseHandler.deleteTask(Task(task.id,"","","",0,""))
                if(status > -1){
                    Toast.makeText(applicationContext,"record deleted", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"ca a pas fonctionner!!!! ", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this@DeleteTaskActivity, ListTaskActivity::class.java)
            startActivity(intent)
        }
    }
}