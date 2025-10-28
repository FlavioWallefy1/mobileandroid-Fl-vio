package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.TaskAdapter
import com.example.myapplication.Task

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextTaskTitle)
        val addButton = findViewById<Button>(R.id.buttonAdd)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerViewTasks)

        adapter = TaskAdapter(
            onTaskClicked = { task ->

                val idx = taskList.indexOfFirst { it.id == task.id }
                if (idx != -1) {
                    val old = taskList[idx]
                    taskList[idx] = old.copy(isCompleted = !old.isCompleted)
                    adapter.submitList(taskList.toList())
                }
            },
            onDeleteClicked = { task ->
                val idx = taskList.indexOfFirst { it.id == task.id }
                if (idx != -1) {
                    taskList.removeAt(idx)
                    adapter.submitList(taskList.toList())
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // botão Adicionar
        addButton.setOnClickListener {
            val title = editText.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(this, "Título vazio", Toast.LENGTH_SHORT).show()
            } else {
                val newTask = Task(title = title)
                taskList.add(newTask)
                adapter.submitList(taskList.toList())
                editText.text.clear()
            }
        }
    }
}
