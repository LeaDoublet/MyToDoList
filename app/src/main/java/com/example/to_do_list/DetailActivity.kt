package com.example.to_do_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.to_do_list.databinding.TaskDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: TaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = TaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        println("ID Task de Detail " + taskID)
        var btnRetour = findViewById<Button>(R.id.retour)
        btnRetour.setOnClickListener{
            val intent = Intent(this@DetailActivity, ListTaskActivity::class.java)
            startActivity(intent)
        }


        var btnDelete = findViewById<ImageButton>(R.id.suprimer)
        btnDelete.setOnClickListener{
            val intent = Intent(this@DetailActivity, DeleteTaskActivity::class.java)
            intent.putExtra(BOOK_ID_EXTRA,taskID)
            startActivity(intent)
        }


        val task = TaskFromID(taskID,this)

        val calendrier = findViewById<ImageView>(R.id.calendrier)
        val dateEcrite = findViewById<TextView>(R.id.date_ecrite)
        if(task != null)
        {
            println(task.titre)
            println("voici la date de la tache" + task.deadline)
            binding.viewTitre.text = task.titre
            binding.textDescription.text = task.description
            if (task.deadline != "false"){
                binding.dateEcrite.text = task.deadline
            }else{
                calendrier.isVisible = false
                dateEcrite.isVisible = false
            }
        }
    }


}
    fun TaskFromID(taskID: Int,context: Context): Task? {
    val databaseHandler: DatabaseHandler = DatabaseHandler(context)
    val emp: List<Task> = databaseHandler.viewTask()
    for (e in emp) {
        if (e.id == taskID)
            return e
    }
    return null
}