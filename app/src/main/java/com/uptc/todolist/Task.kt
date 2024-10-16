package com.uptc.todolist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val name: String,
    var isCompleted: Boolean = false
) : Parcelable
