package com.example.to_do_list

import android.graphics.Color
import java.sql.Date

val BOOK_ID_EXTRA = "bookExtra"

class Task ( val id : Int, val state: String, val titre: String , val description: String, val color: Int, val deadline: String) {
}