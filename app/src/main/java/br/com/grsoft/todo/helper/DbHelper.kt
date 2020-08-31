package br.com.grsoft.todo.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $TASK_TABLE" +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " description TEXT NOT NULL)"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {

        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Todo.db"
        const val TASK_TABLE = "tasks"
    }
}