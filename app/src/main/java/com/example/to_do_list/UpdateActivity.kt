package com.example.to_do_list

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.databinding.ActivityUpdateTaskBinding

class UpdateActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUpdateTaskBinding
    companion object{
        var taskIdentifer : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        taskIdentifer = taskID
        val task = taskFromID(taskID,this)

        val textmodifTache = findViewById<TextView>(R.id.updatetache)
        val fontmodiftache = Typeface.createFromAsset(assets,"police/spacetime-regular.ttf")
        textmodifTache.typeface = fontmodiftache

        val textTitreModif = findViewById<TextView>(R.id.titlemodif)
        val textDescriptionModif = findViewById<TextView>(R.id.descriptionmodif)
        val font = Typeface.createFromAsset(assets,"police/Reikna.ttf")
        textTitreModif.typeface = font
        textDescriptionModif.typeface = font

        val modifTitre = findViewById<EditText>(R.id.u_titleUpdateTask)
        val modifDes = findViewById<EditText>(R.id.u_descriptionUpdateTask)

        val btnRetour = findViewById<Button>(R.id.btnRetourUpdate)
        btnRetour.setBackgroundColor(getColor(R.color.button))
        btnRetour.setOnClickListener{
            retour()
        }

        val modifier = findViewById<Button>(R.id.btnUpdate)
        modifier.setBackgroundColor(getColor(R.color.button))
        modifier.setOnClickListener{
            val updateTitre = modifTitre.text.toString()
            val updateDes = modifDes.text.toString()
            val databaseHandler = DatabaseHandler(this)
            if(updateTitre.trim()!=""){
                if (task != null) {
                    task.titre = updateTitre
                    task.description = updateDes
                }
                val status = task?.let { it1 -> databaseHandler.updateTask(it1) }
                if (status != null) {
                    if(status > -1){
                        Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
                        findViewById<EditText>(R.id.u_titleUpdateTask).text.clear()
                        findViewById<EditText>(R.id.u_descriptionUpdateTask).text.clear()
                    }
                }
            }else{
                Toast.makeText(applicationContext,"Veuillez remplir un titre valide",Toast.LENGTH_LONG).show()
            }
        }


    }
    private fun retour(){
        val intent = Intent(this@UpdateActivity, DetailActivity::class.java)
        intent.putExtra(BOOK_ID_EXTRA, taskIdentifer)
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        retour()
    }

}