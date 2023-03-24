package com.example.to_do_list

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

class ChangeClothesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_clothes)
        var btnReset = findViewById<ImageView>(R.id.reset)
        var check = findViewById<ImageView>(R.id.check)
        btnReset.layoutParams = check.layoutParams
        var neck = findViewById<ImageView>(R.id.neck)
        var eyes = findViewById<ImageView>(R.id.eyes)
        var list_images = mutableListOf<ImageView>()
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        var btnRetour = findViewById<Button>(R.id.btnBack)
        btnRetour.setOnClickListener{
            val intent = Intent(this@ChangeClothesActivity, MainActivity::class.java)
            startActivity(intent)
        }
        btnReset.setOnClickListener{
            neck.setImageDrawable(null)
            eyes.setImageDrawable(null)
        }
        check.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            if (neck.drawable != null){
                val streamNeck = ByteArrayOutputStream()
                val bitMapNeck = neck.drawable.toBitmap(neck.drawable.intrinsicWidth,
                    neck.drawable.intrinsicHeight,
                    null)
                bitMapNeck.compress(Bitmap.CompressFormat.PNG, 100, streamNeck)
                val byteArrayNeck = streamNeck.toByteArray()
                intent.putExtra("IMAGE_NECK",byteArrayNeck)
            }
            if (eyes.drawable != null){
                val streamEyes = ByteArrayOutputStream()
                val bitMapEyes = eyes.drawable.toBitmap(eyes.drawable.intrinsicWidth,
                    eyes.drawable.intrinsicHeight,
                    null)
                bitMapEyes.compress(Bitmap.CompressFormat.PNG, 100, streamEyes)
                val byteArrayEyes = streamEyes.toByteArray()
                intent.putExtra("IMAGE_EYES",byteArrayEyes)
            }
            intent.putExtra("CLOTHE",1)


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
        for (image in list_images) {

            image.setOnClickListener {
                if (image.contentDescription == "neck") {

                    neck.visibility = View.VISIBLE
                    neck.setImageDrawable(image.drawable)
                } else {

                    eyes.visibility = View.VISIBLE
                    eyes.setImageDrawable(image.drawable)
                }
            }
        }

    }

}
