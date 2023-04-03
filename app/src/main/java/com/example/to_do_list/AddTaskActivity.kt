package com.example.to_do_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.util.*


class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    var day =0
    var month = 0
    var year = 0

    var savedYear = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val btnRetour = findViewById<Button>(R.id.btnRetour)
        btnRetour.setBackgroundColor(getColor(R.color.button))

        val btnEnregistrer = findViewById<Button>(R.id.btnSave)
        btnEnregistrer.setBackgroundColor(getColor(R.color.button))

        val textNouvelleTache = findViewById<TextView>(R.id.nouvelletache)
        val fontNouvelletache = Typeface.createFromAsset(assets,"police/spacetime-regular.ttf")
        textNouvelleTache.typeface = fontNouvelletache

        val textTitre = findViewById<TextView>(R.id.title)
        val textDescription = findViewById<TextView>(R.id.description)
        val textswitchDate = findViewById<Switch>(R.id.switchDate)

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
    fun countDigits(number: Int): Int {
        var count = 0
        var n = number
        while (n != 0) {
            n /= 10
            ++count
        }
        return count
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedYear = year
        var savedDayString = dayOfMonth.toString()
        var savedMonthString = month.toString()

        if (countDigits(dayOfMonth) < 2){
            savedDayString = "0$dayOfMonth"
        }
        if (countDigits(month) < 2){
            savedMonthString = "0$month"
        }

        val tvTextTime = findViewById<TextView>(R.id.tv_textTime)
        tvTextTime.text = "$savedDayString-$savedMonthString-$savedYear"
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

    }

    private fun pickDate() {
        val switchDate = findViewById<Switch>(R.id.switchDate)
        switchDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                getDateTimeCalendar()
                DatePickerDialog(this,this,year,month,day).show()
                val current = LocalTime.now()
                println("Current Date and Time is: $current")
            }
        }
    }

    fun saveRecord(View : View) {
        val titre = findViewById<EditText>(R.id.u_titleNewTask).text.toString()
        val description = findViewById<EditText>(R.id.u_descriptionNewTask).text.toString()
        val databaseHandler= DatabaseHandler(this)
        val idMax = databaseHandler.Idmax()
        val todo = "TODO"
        val date = findViewById<TextView>(R.id.tv_textTime)

        if(titre.trim()!=""){
            println("Date de la notif " + date.text)
            val taskI = Task(idMax,todo,titre,description, 40, date.text as String)
            val status = databaseHandler.addTask(taskI)

            if(status > -1){
                Toast.makeText(applicationContext,"Tâche ajouté", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_titleNewTask).text.clear()
                findViewById<EditText>(R.id.u_descriptionNewTask).text.clear()
                findViewById<TextView>(R.id.tv_textTime).text = " "
                findViewById<Switch>(R.id.switchDate).isChecked = false
            }
        }else{
            Toast.makeText(applicationContext,"Pas de titre !!", Toast.LENGTH_LONG).show()
        }
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() {
        val intent = Intent(this@AddTaskActivity, MainActivity::class.java)
        startActivity(intent)
    }
}