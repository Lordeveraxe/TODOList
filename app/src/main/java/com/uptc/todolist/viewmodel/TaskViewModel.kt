package com.uptc.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.uptc.todolist.data.database.TaskDatabase
import com.uptc.todolist.data.model.Task
import com.uptc.todolist.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val pendingTasks: LiveData<List<Task>>
    val completedTasks: LiveData<List<Task>>

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> get() = _selectedTask

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        pendingTasks = repository.getPendingTasks()
        completedTasks = repository.getCompletedTasks()
    }

    fun addTask(taskName: String) {
        val newTask = Task(name = taskName, isCompleted = false)
        viewModelScope.launch {
            repository.insert(newTask)
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = true)
            repository.update(updatedTask)
        }
    }

    fun uncompleteTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = false)
            repository.update(updatedTask)
        }
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun getTaskById(taskId: Int): LiveData<Task?> {
        return repository.getTaskById(taskId)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}