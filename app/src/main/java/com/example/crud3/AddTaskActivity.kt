package com.example.crud3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call.Details
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.crud3.database.DatabaseHelper
import com.example.crud3.model.TaskListModel

class AddTaskActivity : AppCompatActivity() {
    lateinit var btnSave:Button
    lateinit var btnDelete:Button
    lateinit var editName:EditText
    lateinit var editDetails: EditText
    var dbHandler : DatabaseHelper ?= null;
    var isEditMode : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)
        editName = findViewById(R.id.taskname)
        editDetails = findViewById(R.id.taskdetails)

        dbHandler = DatabaseHelper(this)

        if(intent != null && intent.getStringExtra("MODE") == "E"){
                //update data
            isEditMode = true;
            btnSave.text = "UPDATE"
            btnDelete.visibility = View.VISIBLE
            val tasks :TaskListModel = dbHandler!!.selectDataWithId(intent.getIntExtra("ID",0))

            editName.setText(tasks.name)
            editDetails.setText(tasks.detail)

        }
        else{
                //insert data
            isEditMode = false;
            btnSave.text = "SAVE"
            btnDelete.visibility = View.GONE

        }


        btnSave.setOnClickListener {
            var success:Boolean = false
            var tasks : TaskListModel = TaskListModel()
            if(isEditMode){
                //update
                tasks.id = intent.getIntExtra("ID",0)
                tasks.name = editName.text.toString()
                tasks.detail = editDetails.text.toString()

                success = dbHandler!!.updateTask(tasks) as Boolean
            }

            else{
                //insert data
                tasks.name = editName.text.toString()
                tasks.detail = editDetails.text.toString()

                success = dbHandler?.addTask(tasks) as Boolean


            }

            if(success){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                finish()
            }
            else{
                val toast = Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG)
                toast.show()
            }
        }
        btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Alert!").setMessage("Are you sure you want to delete task ?").setPositiveButton("YES") { dialog, i ->
                val success = dbHandler?.deleteTask(intent.getIntExtra("ID", 0)) as Boolean
                if (success) {
                    finish()
                    dialog.dismiss()
                }

            }.setNegativeButton("No") { dialog, i ->
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}