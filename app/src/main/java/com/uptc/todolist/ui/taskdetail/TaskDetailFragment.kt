package com.uptc.todolist.ui.taskdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uptc.todolist.R
import com.uptc.todolist.viewmodel.TaskViewModel
import com.uptc.todolist.data.model.Task

class TaskDetailFragment : Fragment() {

    private val args: TaskDetailFragmentArgs by navArgs()
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskCheckbox: CheckBox
    private lateinit var deleteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.task_detail_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val taskDescriptionTextView: TextView = view.findViewById(R.id.tv_task_description)
        val taskDetailTextView: TextView = view.findViewById(R.id.tv_task_detail)
        taskCheckbox = view.findViewById(R.id.cb_task_completed)
        deleteButton = view.findViewById(R.id.btn_delete_task)

        val taskId = args.taskId
        loadTask(taskId, taskDescriptionTextView, taskDetailTextView)

        return view
    }

    private fun loadTask(taskId: String, taskDescriptionTextView: TextView, taskDetailTextView: TextView) {
        viewModel.getTaskById(taskId.toInt()).observe(viewLifecycleOwner) { task ->
            task?.let {
                taskDescriptionTextView.text = "Descripción de la tarea: ${it.name}"
                taskCheckbox.isChecked = it.isCompleted
                taskDetailTextView.text = "Detalles adicionales sobre la tarea."

                taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    it.isCompleted = isChecked
                    updateTaskCompletion(it)
                }

                // Configurar el botón de eliminar
                deleteButton.setOnClickListener {
                    deleteTask(task)
                }
            }
        }
    }

    private fun updateTaskCompletion(task: Task) {
        if (task.isCompleted) {
            viewModel.completeTask(task)
        } else {
            viewModel.uncompleteTask(task)
        }
    }

    private fun deleteTask(task: Task) {
        viewModel.deleteTask(task)
        findNavController().popBackStack()
    }
}