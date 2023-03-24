package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list.databinding.ListOfTaskBinding

class ListTaskActivity: AppCompatActivity(), TaskClickListener {

    private lateinit var binding: ListOfTaskBinding

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
            for (task in emp){
                if(task.state == "TODO"){
                    listTaskTodo.add(task)
                }
                if(task.state == "LATE"){
                    listTaskLATE.add(task)
                }
                if(task.state == "FINISHED"){
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
}
