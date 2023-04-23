package com.example.crud3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud3.adapter.TaskListAdapter
import com.example.crud3.database.DatabaseHelper
import com.example.crud3.model.TaskListModel
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recycle_task:RecyclerView
    lateinit var btn_add :Button
    private var taskListAdapter : TaskListAdapter ?= null
    private var dbHandler : DatabaseHelper ?= null
    private var tasklist:List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager:LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycle_task = findViewById(R.id.task_view)
        btn_add = findViewById(R.id.btn_add)

        dbHandler = DatabaseHelper(this)
        fetchList()
        
        btn_add.setOnClickListener { 
            val intent = Intent(this,AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchList(){
        tasklist = dbHandler!!.getAllTask()
        taskListAdapter = TaskListAdapter(tasklist,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)

        recycle_task.layoutManager = linearLayoutManager
        recycle_task.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}