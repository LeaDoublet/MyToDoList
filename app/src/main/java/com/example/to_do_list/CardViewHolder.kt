package com.example.to_do_list

import android.app.DatePickerDialog
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: TaskClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindTask(Task: Task)
    {
        cardCellBinding.textViewTitre.text = Task.titre

        cardCellBinding.cardView.setOnClickListener{
            clickListener.onClick(Task)
        }

        cardCellBinding.checkBoxFINISH.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked){
                clickListener.FinishTask(Task)
            }
            Log.v(
                "checkBoxFinish State=",
                "" + isChecked
            )
        }
    }
}