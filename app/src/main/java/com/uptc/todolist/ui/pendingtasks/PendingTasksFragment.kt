package com.uptc.todolist.ui.pendingtasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptc.todolist.R
import com.uptc.todolist.viewmodel.TaskViewModel
import com.uptc.todolist.ui.adapter.TaskAdapter

class PendingTasksFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.pending_tasks_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        val pendingTasksRecyclerView: RecyclerView = view.findViewById(R.id.rv_pending_tasks)
        val addTaskButton: Button = view.findViewById(R.id.btn_add)
        val completedTasksButton: Button = view.findViewById(R.id.btn_completed)
        val newTaskEditText: EditText = view.findViewById(R.id.et_new_task)

        taskAdapter = TaskAdapter(
            onTaskClick = { task ->
                // Navegación usando SafeArgs, pasando el taskId
                val action = PendingTasksFragmentDirections
                    .actionPendingTasksFragmentToTaskDetailFragment(task.id.toString())
                findNavController().navigate(action)
            },
            onTaskCheckChange = { task, isChecked ->
                if (isChecked) {
                    viewModel.completeTask(task) // Marca la tarea como completada
                }
            }
        )

        pendingTasksRecyclerView.adapter = taskAdapter
        pendingTasksRecyclerView.layoutManager = LinearLayoutManager(context)

        // Observar las tareas pendientes
        viewModel.pendingTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks.toList()) // Actualizar la lista de tareas pendientes
        }

        // Botón para agregar nuevas tareas
        addTaskButton.setOnClickListener {
            val taskName = newTaskEditText.text.toString().trim()
            if (taskName.isNotEmpty()) {
                viewModel.addTask(taskName) // Agregar la tarea al ViewModel
                newTaskEditText.text.clear()
            }
        }

        // Navegación a tareas completadas
        completedTasksButton.setOnClickListener {
            findNavController().navigate(R.id.action_pendingTasksFragment_to_completedTasksFragment)
        }

        return view
    }
}