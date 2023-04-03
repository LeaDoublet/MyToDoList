package com.example.to_do_list

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
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

        val font = Typeface.createFromAsset(assets,"police/Reikna.ttf")
        var textTitre = findViewById<TextView>(R.id.view_titre)
        textTitre.typeface = font


        val taskID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        println("ID Task de Detail " + taskID)

        var btnRetour = findViewById<Button>(R.id.retour)
        btnRetour.setBackgroundColor(getColor(R.color.green))
        btnRetour.setOnClickListener{
            move(null,ListTaskActivity::class.java)
        }

        var modifier = findViewById<ImageButton>(R.id.modifier)
        modifier.setOnClickListener{
            move(taskID,UpdateActivity::class.java)
        }

        var btnDelete = findViewById<ImageButton>(R.id.suprimer)
        btnDelete.setOnClickListener{
            move(taskID,DeleteTaskActivity::class.java)
        }


        val task = taskFromID(taskID,this)

        val calendrier = findViewById<ImageView>(R.id.calendrier)
        val dateEcrite = findViewById<TextView>(R.id.date_ecrite)
        if(task != null)
        {
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
    private fun move(taskId : Int?, destination : Class<*>){
        val intent = Intent(this@DetailActivity, destination)
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
    fun taskFromID(taskID: Int, context: Context): Task? {
    val databaseHandler: DatabaseHandler = DatabaseHandler(context)
    val emp: List<Task> = databaseHandler.viewTask()
    for (e in emp) {
        if (e.id == taskID)
            return e
    }
    return null
}