package com.uptc.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _pendingTasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val pendingTasks: LiveData<MutableList<Task>> get() = _pendingTasks

    private val _completedTasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val completedTasks: LiveData<MutableList<Task>> get() = _completedTasks

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> get() = _selectedTask

    fun addTask(taskName: String) {
        val newTask = Task(taskName, isCompleted = false)
        _pendingTasks.value?.add(newTask)
        _pendingTasks.notifyObserver()
    }

    fun completeTask(task: Task) {
        _pendingTasks.value?.let { pendingList ->
            if (pendingList.remove(task)) {
                _completedTasks.value?.add(task.copy(isCompleted = true))
                _pendingTasks.notifyObserver()
                _completedTasks.notifyObserver()
            }
        }
    }

    fun uncompleteTask(task: Task) {
        _completedTasks.value?.let { completedList ->
            if (completedList.remove(task)) {
                _pendingTasks.value?.add(task.copy(isCompleted = false))
                _pendingTasks.notifyObserver()
                _completedTasks.notifyObserver()
            }
        }
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}