package com.example.to_do_list

import android.graphics.Color
import java.sql.Date

val BOOK_ID_EXTRA = "bookExtra"

class Task (val id : Int, val state: String, var titre: String, var description: String, val color: Int, val deadline: String) {
}