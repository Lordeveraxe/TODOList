package com.uptc.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompletedTasksFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var completedTaskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.completed_tasks_fragment, container, false)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        val completedTasksRecyclerView: RecyclerView = view.findViewById(R.id.rv_completed_tasks)

        completedTaskAdapter = TaskAdapter(
            onTaskClick = { task ->
                viewModel.selectTask(task)
                findNavController().navigate(R.id.action_completedTasksFragment_to_taskDetailFragment)
            },
            onTaskCheckChange = { task, isChecked ->
                if (!isChecked) {
                    viewModel.uncompleteTask(task) // Desmarcar como completada y mover a pendientes
                }
            }
        )

        completedTasksRecyclerView.adapter = completedTaskAdapter
        completedTasksRecyclerView.layoutManager = LinearLayoutManager(context)

        // Observar las tareas completadas
        viewModel.completedTasks.observe(viewLifecycleOwner) { tasks ->
            completedTaskAdapter.submitList(tasks.toList()) // Actualizar la lista de tareas completadas
        }

        return view
    }
}