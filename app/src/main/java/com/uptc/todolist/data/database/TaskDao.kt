package com.uptc.todolist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uptc.todolist.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE isCompleted = :isCompleted")
    fun getTasksByCompletionStatus(isCompleted: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task?>
}