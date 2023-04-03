package com.example.to_do_list

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list.databinding.ListOfTaskBinding

class ListTaskActivity: AppCompatActivity(), TaskClickListener {

    private lateinit var binding: ListOfTaskBinding;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ListOfTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var btnRetour = findViewById<Button>(R.id.btnRetour)
        btnRetour.setBackgroundColor(getColor(R.color.button))
        btnRetour.setOnClickListener{
            val intent = Intent(this@ListTaskActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val emp: List<Task> = databaseHandler.viewTask()
        val listTaskTodo : MutableList<Task> = mutableListOf()
        val listTaskLATE : MutableList<Task> = mutableListOf()
        val listTaskFINISH : MutableList<Task> = mutableListOf()
        if (!emp.isEmpty()){
            for (task in emp) {
                println(task.titre + " " + task.state)
                if (task.state == "TODO") {
                    listTaskTodo.add(task)
                }
                if (task.state == "LATE") {
                    listTaskLATE.add(task)
                }
                if (task.state == "FINISHED") {
                    listTaskFINISH.add(task)
                }
            }

            val mainActivity = this
            binding.recyclerViewTODO.apply {
                layoutManager = LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = MyListAdapter(listTaskTodo,mainActivity)
            }
            binding.recyclerViewLATE.apply {
                layoutManager = LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = MyListAdapter(listTaskLATE,mainActivity)
            }
            binding.recyclerViewFINISH.apply {
                layoutManager = LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = MyListAdapter(listTaskFINISH,mainActivity)
            }
        }
    }
    override fun onClick(Task : Task)
    {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BOOK_ID_EXTRA, Task.id)
        startActivity(intent)
    }

    override fun FinishTask(task: Task) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup, null)
        var dialogBox = ""
        var status : Int? = 0
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        if (task.state == "FINISHED"){
            status = databaseHandler.deleteTask(Task(task.id,"","","",0,""))
            dialogBox = "Tâche supprimé"
        }
        if (task.state == "LATE" || task.state == "TODO"){
            dialogBuilder.setView(dialogView)
            task.state = "FINISHED"
            status = task?.let { it1 -> databaseHandler.updateTask(it1) }
            dialogBox = "Tâche finis"
        }
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
            println(task.titre + " " +task.state)
            if (status != null) {
                if(status > -1){
                    Toast.makeText(applicationContext,dialogBox, Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(applicationContext,"Mise à jour impossible", Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this@ListTaskActivity, ListTaskActivity::class.java)
            startActivity(intent)
        })
        val b = dialogBuilder.create()
        b.show()
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        val intent = Intent(this@ListTaskActivity, MainActivity::class.java)
        startActivity(intent)
    }

}

