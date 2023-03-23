package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image

class MainActivity : AppCompatActivity() {
    companion object{
        var bitMapNeck : Bitmap? = null
        var bitMapEye : Bitmap? = null
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addTask = findViewById<ImageButton>(R.id.addTask)
        val goPageClothe = findViewById<ImageButton>(R.id.goPageClothe)
        val goViewModel = findViewById<FrameLayout>(R.id.frameLayout)
        val neck = findViewById<ImageView>(R.id.neckMain)
        val eyes = findViewById<ImageView>(R.id.eyesMain)

        val extras = intent.extras
        val byteArrayNeck = extras?.getByteArray("IMAGE_NECK")
        val byteArrayEyes = extras?.getByteArray("IMAGE_EYES")

        val bmpNeck = byteArrayNeck?.let { BitmapFactory.decodeByteArray(byteArrayNeck, 0, it.size) }
        if (bmpNeck != null){
            bitMapNeck = bmpNeck
        }

        val bmpEyes = byteArrayEyes?.let { BitmapFactory.decodeByteArray(byteArrayEyes, 0, it.size) }
        if (bmpEyes != null){
            bitMapEye = bmpEyes
        }
        neck.setImageBitmap(bitMapNeck)
        eyes.setImageBitmap(bitMapEye)

        val notificationHelper = NotificationHelper(this)
        goPageClothe.setOnClickListener{
            val intent = Intent(this@MainActivity, ChangeClothesActivity::class.java)
            startActivity(intent)
        }

        addTask.setOnClickListener{
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        goViewModel.setOnClickListener{
            println("on passe bien dans le goViewModel")
            val intent = Intent(this@MainActivity, ListTaskActivity::class.java)
            startActivity(intent)
        }

    }

}