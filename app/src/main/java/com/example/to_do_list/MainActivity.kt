package com.example.to_do_list

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.lang.Integer.parseInt

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

        addTask.setOnClickListener{
            setContentView(R.layout.activity_add_task)
        }

    }

    fun saveRecord(view: View) {
        val id = findViewById<EditText>(R.id.u_id).text.toString()
        val state = findViewById<EditText>(R.id.state).text.toString()
        val titre = findViewById<EditText>(R.id.titreNewtask).text.toString()
        val description = findViewById<EditText>(R.id.descriptionNewTask).text.toString()
        val couleur = findViewById<EditText>(R.id.color).text.toString()
        val date= findViewById<EditText>(R.id.date).text.toString()
        val databaseHandler= DatabaseHandler(this)

        val idMax = databaseHandler.lastID()

        if(id.trim()!="" && titre.trim()!="" && state.trim()!=""){
            val Taski = Task(idMax,"TODO","titre","description", 0xFCD8CA,"10/03/2023")
            val status = databaseHandler.addTask(Taski)
            if(status > -1){
                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_id).text.clear()
                findViewById<EditText>(R.id.state).text.clear()
                findViewById<EditText>(R.id.titre).text.clear()
                findViewById<EditText>(R.id.description).text.clear()
                findViewById<EditText>(R.id.color).text.clear()
                findViewById<EditText>(R.id.date).text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }
    }

    fun viewRecord(view: View){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<Task> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        var index = 0
        for(e in emp){
            empArrayId[index] = e.id.toString()
            empArrayName[index] = e.titre
            empArrayEmail[index] = e.description
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this,empArrayId,empArrayName,empArrayEmail)
        findViewById<ListView>(R.id.listView).adapter = myListAdapter
    }
}