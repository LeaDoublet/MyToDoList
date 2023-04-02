package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.databinding.ActivityMainBinding
import com.example.to_do_list.databinding.ActivityUpdateTaskBinding
import java.sql.Time
import java.util.*


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    companion object{
        var bitMapNeck : Bitmap? = null
        var bitMapEye : Bitmap? = null
    }
    fun DressUp(extras: Bundle?){
        val byteArrayNeck = extras?.getByteArray("IMAGE_NECK")
        val byteArrayEyes = extras?.getByteArray("IMAGE_EYES")


        val bmpNeck = byteArrayNeck?.let { BitmapFactory.decodeByteArray(byteArrayNeck, 0, it.size) }
        if (bmpNeck != null){
            bitMapNeck = bmpNeck
        }
        else{
            bitMapNeck = null
        }

        val bmpEyes = byteArrayEyes?.let { BitmapFactory.decodeByteArray(byteArrayEyes, 0, it.size) }
        if (bmpEyes != null){
            bitMapEye = bmpEyes
        }
        else{
            bitMapEye = null
        }
    }
    fun TaskInCheckBox(listCheckBox: List<CheckBox>, listTask: List<Task>): List<List<Int>> {
        var index = 0
        var listIdTask = kotlin.collections.mutableListOf<Int>()
        var listIdCheckbox= mutableListOf<Int>()
        for (task in listTask){
            if (listCheckBox.size - 1>= index && task.state == "TODO" || task.state =="LATE"){
                listCheckBox[index].text = task.titre
                listIdTask.add(task.id)
                listIdCheckbox.add(index)
                index += 1
            }
        }
        var listIdEquivalent :List<List<Int>> = listOf(listIdTask,listIdCheckbox)
        if (listCheckBox.size > index){
            for (i in index until listCheckBox.size){
                listCheckBox[i].visibility = View.INVISIBLE
            }
        }
        return listIdEquivalent
    }
    fun CheckedBox(checkBoxId : Int, comparateur : List<List<Int>>){
        var index = 0
        var taskId : Int = 0
        for (checkBox in comparateur[1]){
            if (checkBox == checkBoxId){
                taskId = comparateur[0][index]
            }
            index += 1
        }

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addTask = findViewById<ImageButton>(R.id.addTask)
        val goPageClothe = findViewById<ImageButton>(R.id.goPageClothe)
        val goViewModel = findViewById<FrameLayout>(R.id.frameLayout)
        val neck = findViewById<ImageView>(R.id.neckMain)
        val eyes = findViewById<ImageView>(R.id.eyesMain)

        val showedTask1 =  findViewById<CheckBox>(R.id.TaskMain1)
        val showedTask2 =  findViewById<CheckBox>(R.id.TaskMain2)
        val showedTask3 =  findViewById<CheckBox>(R.id.TaskMain3)
        val listCheckBox = listOf<CheckBox>(showedTask1,showedTask2,showedTask3)
        val dataBaseHandler = DatabaseHandler(this)
        val listTask = dataBaseHandler.viewTask()


        val extras = intent.extras
        if (extras?.getInt("CLOTHE") == 1){
            DressUp(extras)
        }

        neck.setImageBitmap(bitMapNeck)
        eyes.setImageBitmap(bitMapEye)

        var comparateur = TaskInCheckBox(listCheckBox, listTask)
        for (checkBox in listCheckBox){
            checkBox.setOnCheckedChangeListener{
                    buttonView, isChecked ->
                if (isChecked){
                    CheckedBox(checkBox.id, comparateur)
                }
            }
        }

        val notificationHelper = NotificationHelper(this)
        for (task in listTask){

            notificationHelper.createNotification(task.titre, task)
        }


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