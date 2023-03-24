package com.example.to_do_list

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.util.*


class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    var day =0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth =0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        var btnRetour = findViewById<Button>(R.id.btnRetour)

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
        tv_textTime.isVisible = true
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        var switchDate = findViewById<Switch>(R.id.switchDate)
        switchDate.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                getDateTimeCalendar()
                DatePickerDialog(this,this,year,month,day).show()
            }
            Log.v(
                "Switch State=",
                "" + isChecked
            )
        }
    }

    fun saveRecord(view: View) {
        val titre = findViewById<EditText>(R.id.u_titleNewTask).text.toString()
        val description = findViewById<EditText>(R.id.u_descriptionNewTask).text.toString()
        val databaseHandler= DatabaseHandler(this)
        val idMax = databaseHandler.Idmax()
        val todo = "TODO"
        val date = findViewById<TextView>(R.id.tv_textTime)
        var datefind = forDate(date)

        if(titre.trim()!=""){
            val Taski = Task(idMax,todo,titre,description, 40,datefind)
            val status = databaseHandler.addTask(Taski)

            if(status > -1){
                Toast.makeText(applicationContext,"record save", Toast.LENGTH_LONG).show()
                findViewById<EditText>(R.id.u_titleNewTask).text.clear()
                findViewById<EditText>(R.id.u_descriptionNewTask).text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"Pas de titre !!", Toast.LENGTH_LONG).show()
        }
    }

    fun forDate(date : TextView) : String{
        if (date.isVisible){
            return date.toString()
        }else{
            return "false"
        }
    }
}