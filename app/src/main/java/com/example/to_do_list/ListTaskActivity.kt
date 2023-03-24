package com.example.to_do_list

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
            task.state = "FINISHED"
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            val status = task?.let { it1 -> databaseHandler.updateTask(it1) }
                if (status != null) {
                    if(status > -1){
                        Toast.makeText(applicationContext,"record update", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext,"ca fonctinne pas ", Toast.LENGTH_LONG).show()
                }
            val intent = Intent(this@ListTaskActivity, ListTaskActivity::class.java)
            startActivity(intent)
        })
        val b = dialogBuilder.create()
        b.show()
    }
}

