package br.com.grsoft.todo.dao.implementation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.com.grsoft.todo.dao.ITaskDAO
import br.com.grsoft.todo.helper.DbHelper
import br.com.grsoft.todo.model.Task
import java.sql.SQLDataException

class TaskDAO(val context: Context): ITaskDAO {

    private val dbHelper = DbHelper(context)
    private val write = dbHelper.writableDatabase
    private val read = dbHelper.readableDatabase
    private val contentValues = ContentValues()

    override fun save(task: Task): Boolean {
        contentValues.put("description", task.description)
        write.insert(DbHelper.TASK_TABLE, null, contentValues)
        return true
    }

    override fun update(task: Task): Boolean {
        val args = listOf<String>(task.id.toString())
        contentValues.put("description", task.description)
        write.update(DbHelper.TASK_TABLE, contentValues, "id=?", args.toTypedArray())
        return true
    }

    override fun delete(task: Task): Boolean {
        val args = listOf<String>(task.id.toString())
        write.delete(DbHelper.TASK_TABLE, "id=?", args.toTypedArray())

        return true
    }

    override fun list(): ArrayList<Task> {
        val tasks: ArrayList<Task> = ArrayList()

        val sql = "SELECT * FROM ${DbHelper.TASK_TABLE}" + " ;"
        val cursor: Cursor = read.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val task = Task()
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val description = cursor.getString(cursor.getColumnIndex("description"))

            task.id = id
            task.description = description

            tasks.add(task)
        }

        return tasks;
    }

}