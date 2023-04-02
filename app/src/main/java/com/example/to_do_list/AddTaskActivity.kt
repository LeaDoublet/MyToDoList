package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    var day =0
    var month = 0
    var year = 0
    var hour = 0
    var min = 0
    var second = 0

    var savedDay = 0
    var savedMonth =0
    var savedYear = 0
    var savedhour  = 0
    var savedmin = 0
    var savedsecond = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        var btnRetour = findViewById<Button>(R.id.btnRetour)
        btnRetour.setBackgroundColor(getColor(R.color.button))

        var btnEnregistrer = findViewById<Button>(R.id.btnSave)
        btnEnregistrer.setBackgroundColor(getColor(R.color.button))

        var textNouvelleTache = findViewById<TextView>(R.id.nouvelletache)
        val fontNouvelletache = Typeface.createFromAsset(assets,"police/spacetime-regular.ttf")
        textNouvelleTache.typeface = fontNouvelletache

        var textTitre = findViewById<TextView>(R.id.title)
        var textDescription = findViewById<TextView>(R.id.description)
        var textswitchDate = findViewById<Switch>(R.id.switchDate)

        val font = Typeface.createFromAsset(assets,"police/Reikna.ttf")
        textTitre .typeface = font
        textDescription.typeface = font
        textswitchDate.typeface = font

        btnRetour.setOnClickListener{
            val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(intent)
        }
        pickDate()
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        val tv_textTime = findViewById<TextView>(R.id.tv_textTime)
        tv_textTime.text = "$savedDay-$savedMonth-$savedYear"
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

    }

    private fun pickDate() {
        var switchDate = findViewById<Switch>(R.id.switchDate)
        switchDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                getDateTimeCalendar()
                DatePickerDialog(this,this,year,month,day).show()
                val current = LocalTime.now()
                println("Current Date and Time is: $current")
            }
        }
    }

    fun saveRecord(view : View) {
        val titre = findViewById<EditText>(R.id.u_titleNewTask).text.toString()
        val description = findViewById<EditText>(R.id.u_descriptionNewTask).text.toString()
        val databaseHandler= DatabaseHandler(this)
        val idMax = databaseHandler.Idmax()
        val todo = "TODO"
        val date = findViewById<TextView>(R.id.tv_textTime)
        var datefind = forDate(date)

        if(titre.trim()!=""){
            val Taski = Task(idMax,todo,titre,description, 40, datefind as String)
            val status = databaseHandler.addTask(Taski)

            if(status > -1){
                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_titleNewTask).text.clear()
                findViewById<EditText>(R.id.u_descriptionNewTask).text.clear()
                findViewById<TextView>(R.id.tv_textTime).text = " "
            }
        }else{
            Toast.makeText(applicationContext,"Pas de titre !!", Toast.LENGTH_LONG).show()
        }
    }

    private fun forDate(date : TextView) : CharSequence? {
        return if (date.text != " "){
            date.text
        }else{
            "false"
        }
    }
}