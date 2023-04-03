package com.example.to_do_list

interface TaskClickListener {
    fun onClick(task : Task)
    fun finishTask(task: Task)
}