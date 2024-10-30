package com.uptc.todolist.data.repository

import androidx.lifecycle.LiveData
import com.uptc.todolist.data.database.TaskDao
import com.uptc.todolist.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    fun getPendingTasks(): LiveData<List<Task>> = taskDao.getTasksByCompletionStatus(false)
    fun getCompletedTasks(): LiveData<List<Task>> = taskDao.getTasksByCompletionStatus(true)

    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    fun getTaskById(taskId: Int): LiveData<Task?> {
        return taskDao.getTaskById(taskId)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
}