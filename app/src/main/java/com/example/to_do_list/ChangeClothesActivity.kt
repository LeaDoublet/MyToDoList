package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity


class ChangeClothesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_clothes)

        var btnRetour = findViewById<Button>(R.id.btnretourDressing)
        btnRetour.setOnClickListener{
            val intent = Intent(this@ChangeClothesActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}