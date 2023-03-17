package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow

import androidx.appcompat.app.AppCompatActivity


class ChangeClothesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_clothes)
        var list_images = mutableListOf<ImageView>()
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        var btnRetour = findViewById<Button>(R.id.btnBack)
        btnRetour.setOnClickListener{
            val intent = Intent(this@ChangeClothesActivity, MainActivity::class.java)
            startActivity(intent)
        }


        for (i in 0 until tableLayout.childCount) {
            val view = tableLayout.getChildAt(i)

            if (view is ImageView) {
                list_images.add(view)
            } else if (view is TableRow) {
                for (j in 0 until view.childCount) {
                    val childView = view.getChildAt(j)
                    if (childView is ImageView) {
                        list_images.add(childView)
                    }
                }
            }
        }
        for (image in list_images){
            image.setOnClickListener{
                println(image.contentDescription)
            }
        }

// Do something with the list of image views

    }

}