package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Task

class TaskAdapter(
    private val onTaskClicked: (Task) -> Unit,
    private val onDeleteClicked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)
        private val titleTv: TextView = itemView.findViewById(R.id.textViewTitle)
        private val deleteBtn: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(task: Task) {
            titleTv.text = task.title
            checkBox.isChecked = task.isCompleted
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = task.isCompleted

            checkBox.setOnCheckedChangeListener { _, _ ->
                onTaskClicked(task)
            }

            deleteBtn.setOnClickListener {
                onDeleteClicked(task)
            }
            titleTv.setOnClickListener {
                onTaskClicked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}