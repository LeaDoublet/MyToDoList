package com.example.to_do_list

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.databinding.ActivityUpdateTaskBinding

class UpdateActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUpdateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        val task = TaskFromID(taskID,this)

        var textmodifTache = findViewById<TextView>(R.id.updatetache)
        val fontmodiftache = Typeface.createFromAsset(assets,"police/spacetime-regular.ttf")
        textmodifTache.typeface = fontmodiftache

        var textTitreModif = findViewById<TextView>(R.id.titlemodif)
        var textDescriptionModif = findViewById<TextView>(R.id.descriptionmodif)
        val font = Typeface.createFromAsset(assets,"police/Reikna.ttf")
        textTitreModif.typeface = font
        textDescriptionModif.typeface = font

        val modifTitre = findViewById<EditText>(R.id.u_titleUpdateTask)
        val modifDes = findViewById<EditText>(R.id.u_descriptionUpdateTask)

        var btnRetour = findViewById<Button>(R.id.btnRetourUpdate)
        btnRetour.setBackgroundColor(getColor(R.color.button))
        btnRetour.setOnClickListener{
            val intent = Intent(this@UpdateActivity, DetailActivity::class.java)
            intent.putExtra(BOOK_ID_EXTRA, taskID)
            startActivity(intent)
        }

        val modifier = findViewById<Button>(R.id.btnUpdate)
        modifier.setBackgroundColor(getColor(R.color.button))
        modifier.setOnClickListener{
            val updateTitre = modifTitre.text.toString()
            val updateDes = modifDes.text.toString()
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateTitre.trim()!="" && updateDes.trim()!=""){
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
                Toast.makeText(applicationContext,"ca fonctinne pas ",Toast.LENGTH_LONG).show()
            }
        }


    }
}