package com.example.to_do_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListTaskActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_task)
        var btnRetour = findViewById<Button>(R.id.btnRetour)
        btnRetour.setOnClickListener{
            val intent = Intent(this@ListTaskActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<Task> = databaseHandler.viewTask()
        val empArrayId_TODO = Array<String>(emp.size){"0"}
        val empArrayTitre_TODO = Array<String>(emp.size){"null"}
        val empArrayDescription_TODO = Array<String>(emp.size){"null"}
        val empArrayState = Array<String>(emp.size){"null"}
        val empArrayId_LATE = Array<String>(emp.size){"0"}
        val empArrayTitre_LATE = Array<String>(emp.size){"null"}
        val empArrayDescription_LATE = Array<String>(emp.size){"null"}
        val empArrayId_FINISHED = Array<String>(emp.size){"0"}
        val empArrayTitre_FINISHED = Array<String>(emp.size){"null"}
        val empArrayDescription_FINISHED = Array<String>(emp.size){"null"}

        for((index, e) in emp.withIndex()){
            if (e.state == "TODO"){
                empArrayId_TODO[index] = e.id.toString()
                empArrayTitre_TODO[index] = e.titre
                empArrayDescription_TODO[index] = e.description
                empArrayState[index] = e.state
            }
            if (e.state == "LATE"){
                empArrayId_LATE[index] = e.id.toString()
                empArrayTitre_LATE[index] = e.titre
                empArrayDescription_LATE[index] = e.description
            }
            if (e.state == "FINISHED"){
                empArrayId_FINISHED[index] = e.id.toString()
                empArrayTitre_FINISHED[index] = e.titre
                empArrayDescription_FINISHED[index] = e.description
            }

        }
        //creating custom ArrayAdapter
        val myListAdapter_TODO = MyListAdapter(this,empArrayId_TODO,empArrayTitre_TODO,empArrayDescription_TODO,empArrayState)
        val myListAdapter_LATE = MyListAdapter(this,empArrayId_LATE,empArrayTitre_LATE,empArrayDescription_LATE,empArrayState)
        val myListAdapter_FINISHED = MyListAdapter(this,empArrayId_FINISHED,empArrayTitre_FINISHED,empArrayDescription_FINISHED,empArrayState)
        if (empArrayId_LATE[0].contentEquals("0")){

        }else{
            findViewById<ListView>(R.id.listViewLATE).adapter = myListAdapter_LATE
        }
        if (empArrayId_TODO[0].contentEquals("0")){

        }else{
            findViewById<ListView>(R.id.listViewTODO).adapter = myListAdapter_TODO
        }
        if (empArrayId_FINISHED[0].contentEquals("0")){

        }else{
            findViewById<ListView>(R.id.listViewFINISH).adapter = myListAdapter_FINISHED
        }


    }
}
