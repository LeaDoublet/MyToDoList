package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ListOfTaskBinding
import javax.sql.RowSet

class ListTaskActivity: AppCompatActivity() {

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

        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        val emp: List<Task> = databaseHandler.viewTask()

        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter = MyListAdapter(emp)
        }


}}
