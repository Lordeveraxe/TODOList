package com.uptc.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class TaskDetailFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskCheckbox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.task_detail_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val taskDescriptionTextView: TextView = view.findViewById(R.id.tv_task_description)
        val taskDetailTextView: TextView = view.findViewById(R.id.tv_task_detail)
        taskCheckbox = view.findViewById(R.id.cb_task_completed)

        // Observar la tarea seleccionada y mostrar los detalles
        viewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            task?.let {
                taskDescriptionTextView.text = "Descripción de la tarea: ${it.name}"
                taskCheckbox.isChecked = it.isCompleted
                taskDetailTextView.text = "Detalles adicionales sobre la tarea."

                // Escuchar cambios en el estado del CheckBox
                taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    it.isCompleted = isChecked
                    updateTaskCompletion(it)
                }
            }
        }

        return view
    }

    // Actualizar el estado de la tarea en el ViewModel
    private fun updateTaskCompletion(task: Task) {
        if (task.isCompleted) {
            viewModel.completeTask(task) // Mover a completadas
        } else {
            viewModel.uncompleteTask(task) // Mover a pendientes
        }
    }
}