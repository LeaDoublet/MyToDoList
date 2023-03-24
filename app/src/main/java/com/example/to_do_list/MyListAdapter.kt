package com.example.to_do_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.CardCellBinding

class MyListAdapter (
    private val tasks: List<Task>,
    private val clickListener: TaskClickListener
)
    : RecyclerView.Adapter<CardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding,clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}