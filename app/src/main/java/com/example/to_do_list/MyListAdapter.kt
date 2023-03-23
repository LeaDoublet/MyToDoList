package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.CardCellBinding

class MyListAdapter (private val tasks: List<Task>)
    : RecyclerView.Adapter<CardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}