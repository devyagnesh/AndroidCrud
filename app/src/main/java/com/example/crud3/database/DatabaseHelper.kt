package com.example.crud3.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crud3.model.TaskListModel

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {


    companion object{
        private const val DB_NAME= "task"
        private const val DB_VERSION= 1
        private const val TABLE_NAME= "tasklist"

        private const val ID:String = "id"
        private const val TASK_NAME: String= "taskname"
        private const val TASK_DETAILS : String = "taskdetails"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE:String = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY,$TASK_NAME TEXT NOT NULL, $TASK_DETAILS TEXT NOT NULL)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //select all data
    @SuppressLint("Range")
    fun getAllTask():List<TaskListModel>{
        val taskList = ArrayList<TaskListModel>()
        val db = this.writableDatabase;

        val selectQuery = "SELECT * FROM $TABLE_NAME "
        val cursor = db.rawQuery(selectQuery,null)

        if(cursor != null){
            if(cursor.moveToFirst()){
                    do {
                        val task = TaskListModel()
                        task.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                        task.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                        task.detail = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                        taskList.add(task)
                    }while (cursor.moveToNext())
            }
        }

        cursor.close()
        return taskList
    }

    //insert data
    fun addTask(tasks:TaskListModel):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(TASK_NAME,tasks.name)
        values.put(TASK_DETAILS,tasks.detail)

        val success = db.insert(TABLE_NAME,null,values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    @SuppressLint("Range")
    fun selectDataWithId(id:Int):TaskListModel{
        val tasks = TaskListModel()
        val db = this.writableDatabase

        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID =$id;"
        val cursor = db.rawQuery(selectQuery,null)

        cursor?.moveToFirst()
        tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
        tasks.detail = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor.close()
        return tasks
    }

    fun deleteTask(id:Int):Boolean{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString())).toLong()
        db.close()

        return Integer.parseInt("$success") != -1
    }

    fun updateTask(task:TaskListModel):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(TASK_NAME,task.name)
        values.put(TASK_DETAILS,task.detail)
        val success = db.update(TABLE_NAME,values,"$ID=?", arrayOf(task.id.toString())).toLong()

        return Integer.parseInt("$success") != -1
    }
}