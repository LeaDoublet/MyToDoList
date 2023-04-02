package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        private var lastUpdateTime: Long = 0
        private var isTenMinutesPassed = false
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
    fun CheckedBox(checkBoxId : Int, comparateur : List<List<Int>>, listTask : List<Task>){
        var index = 0
        var taskId : Int = 0
        for (checkBox in comparateur[1]){
            if (checkBox == checkBoxId){
                taskId = comparateur[0][index]
                println("taskId récupérer")
            }
            index += 1
        }
        for (task in listTask){
            if (taskId == task.id){
                task.state = "FINISHED"
                val databaseHandler: DatabaseHandler= DatabaseHandler(this)
                val status = task?.let { it1 -> databaseHandler.updateTask(it1) }
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
    private fun SetTaskLate(task : Task) {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = format.parse(task.deadline)
        val currentTime = Calendar.getInstance().timeInMillis // Temps actuel
        if (currentTime - lastUpdateTime >= date.time) { // Vérifier si 10 minutes sont écoulées
            task.state = "LATE"
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            task?.let { it1 -> databaseHandler.updateTask(it1) }
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
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                for (task in listTask) {
                    if (task.deadline.isNotBlank()){
                        SetTaskLate(task)
                    }
                }
                handler.postDelayed(this, 2000)
            }
        }, 10000)

        neck.setImageBitmap(bitMapNeck)
        eyes.setImageBitmap(bitMapEye)

        var comparateur = TaskInCheckBox(listCheckBox, listTask)
        for (checkBox in listCheckBox){
            checkBox.setOnCheckedChangeListener{
                    buttonView, isChecked ->
                if (isChecked){
                    CheckedBox(checkBox.contentDescription.toString().toInt(), comparateur, listTask)
                }
            }
        }

        val notificationHelper = NotificationHelper(this)
        for (task in listTask){
            if (task.state == "TODO" || task.state == "LATE") {
                notificationHelper.createNotification(task.titre, task)
            }
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
            val intent = Intent(this@MainActivity, ListTaskActivity::class.java)
            startActivity(intent)
        }

    }
    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finish()
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.addCategory(Intent.CATEGORY_HOME)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
    }

}