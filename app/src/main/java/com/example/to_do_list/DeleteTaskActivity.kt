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
        val task = taskFromID(taskID,this)

        btnNo_Delete.setOnClickListener{
            move(taskID,DetailActivity::class.java)
        }

        btnYes_Delete.setOnClickListener{
            if(task != null)
            {
                val status = databaseHandler.deleteTask(Task(task.id,"","","",0,""))
                if(status > -1){
                    Toast.makeText(applicationContext,"Tâche supprimé", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"Erreur", Toast.LENGTH_LONG).show()
            }
           move(null,ListTaskActivity::class.java)
        }
    }
    private fun move(taskId : Int?, destination : Class<*>){
        val intent = Intent(this, destination)
        if (taskId != null){
            intent.putExtra(BOOK_ID_EXTRA, taskId)
        }
        startActivity(intent)
    }
    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
        move(null,ListTaskActivity::class.java)
    }

}