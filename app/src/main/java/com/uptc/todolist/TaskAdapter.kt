package com.uptc.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onTaskCheckChange: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks = listOf<Task>()

    fun submitList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskNameTextView: TextView = itemView.findViewById(R.id.tv_task_name)
        private val taskCheckbox: CheckBox = itemView.findViewById(R.id.cb_task)

        fun bind(task: Task) {
            taskNameTextView.text = task.name
            taskCheckbox.isChecked = task.isCompleted

            taskNameTextView.setOnClickListener {
                onTaskClick(task)
            }

            taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckChange(task, isChecked)
            }
        }
    }
}