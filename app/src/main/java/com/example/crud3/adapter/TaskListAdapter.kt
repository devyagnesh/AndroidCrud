package com.example.crud3.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ContentInfoCompat.Flags
import androidx.recyclerview.widget.RecyclerView
import com.example.crud3.AddTaskActivity
import com.example.crud3.model.TaskListModel
import java.util.ArrayList
import com.example.crud3.R
import kotlinx.coroutines.NonDisposableHandle.parent


class TaskListAdapter(tasklist:List<TaskListModel>,private var context: Context): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

        private var tasklist :List<TaskListModel> = ArrayList()

    init {
        this.tasklist = tasklist
    }

    inner class TaskViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name:TextView = view.findViewById(R.id.task_name)
        val details : TextView = view.findViewById(R.id.task_details)
        val editButton:Button = view.findViewById(R.id.btn_edit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_task_list,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist.size;
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasklist[position]
        holder.name.text = task.name
        holder.details.text = task.detail

        holder.editButton.setOnClickListener {
            val intent = Intent(context,AddTaskActivity::class.java)
            intent.putExtra("MODE","E")
            intent.putExtra("ID",task.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}