package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val id: Array<String>, private val name: Array<String>, private val email: Array<String>)
    : ArrayAdapter<String>(context, R.layout.list_tache, name) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_tache, null, true)

        val idText = rowView.findViewById(R.id.textViewId) as TextView
        val nameText = rowView.findViewById(R.id.textViewTitre) as TextView
        val emailText = rowView.findViewById(R.id.textViewDescription) as TextView

        idText.text = "Id: ${id[position]}"
        nameText.text = "Titre: ${name[position]}"
        emailText.text = "Description: ${email[position]}"
        return rowView
    }
}