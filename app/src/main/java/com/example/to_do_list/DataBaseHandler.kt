package com.example.to_do_list

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ToDoListDatabase"
        private val TABLE_CONTACTS = "TaskTable"
        private val KEY_ID = "id"
        private val KEY_TITRE = "titre"
        private val KEY_DESCRIP = "description"
        private val KEY_ETAT = "etat"
        private val KEY_COULEUR = "couleur"
        private val KEY_DATE = "date"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_ETAT  + " TEXT, " + KEY_TITRE + " TEXT, "
                + KEY_DESCRIP + " TEXT, "  + KEY_COULEUR + " INTEGER, " + KEY_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addTask(emp: Task):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        contentValues.put(KEY_ETAT,emp.state)
        contentValues.put(KEY_TITRE, emp.titre)
        contentValues.put(KEY_DESCRIP, emp.description)
        contentValues.put(KEY_COULEUR, emp.color)
        contentValues.put(KEY_DATE, emp.deadline)

        // Inserting Row
        Log.d(contentValues.toString(),"this contenValue")
        println(contentValues.toString())
        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    @SuppressLint("Range")
    fun Idmax():Int{
        var idMax = 1
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var Id: Int
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return 0
        }
        if (cursor.moveToFirst()) {
            do {
                Id = cursor.getInt(cursor.getColumnIndex("id"))
                if (Id > idMax){
                    idMax = idMax+1
                }
                idMax = idMax+1
            } while (cursor.moveToNext())
        }
        return idMax
    }
    //method to read data
    @SuppressLint("Range")
    fun viewTask():List<Task>{
        val empList:ArrayList<Task> = ArrayList<Task>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var Id: Int
        var Titre: String
        var Description: String
        var state : String
        if (cursor.moveToFirst()) {
            do {
                Id = cursor.getInt(cursor.getColumnIndex("id"))
                Titre = cursor.getString(cursor.getColumnIndex("titre"))
                Description = cursor.getString(cursor.getColumnIndex("description"))
                state = cursor.getString(cursor.getColumnIndex("etat"))
                val emp= Task(id = Id, titre = Titre, description = Description, deadline ="", color = 0 , state = state)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data
    fun updateTask(emp: Task):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        contentValues.put(KEY_ETAT,emp.state) // EmpModelClass Phone
        contentValues.put(KEY_TITRE, emp.titre)
        contentValues.put(KEY_DESCRIP, emp.description)
        contentValues.put(KEY_COULEUR, emp.color)
        contentValues.put(KEY_DATE, emp.deadline) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to delete data
    fun deleteTask(emp: Task):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)// EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}