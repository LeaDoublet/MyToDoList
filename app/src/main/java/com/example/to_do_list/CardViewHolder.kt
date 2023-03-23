package com.example.to_do_list

import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindTask(Task: Task)
    {
        cardCellBinding.textViewId.text = Task.id.toString()
        cardCellBinding.textViewTitre.text = Task.titre

    }
}