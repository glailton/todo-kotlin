package br.com.grsoft.todo.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.grsoft.todo.R
import br.com.grsoft.todo.adapter.TaskAdapter
import br.com.grsoft.todo.dao.implementation.TaskDAO
import br.com.grsoft.todo.listener.IOnTaskListener
import br.com.grsoft.todo.model.Task
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), IOnTaskListener {

    private lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        loadTasks()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTasks() {
        val taskDao = TaskDAO(this)
        tasks = taskDao.list()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TaskAdapter(tasks, this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    override fun onItemClick(position: Int) {
        val selectedTask = tasks[position]

        val intent = Intent(this, AddTaskActivity::class.java)
        intent.putExtra("task", selectedTask)

        startActivity(intent)
    }

    override fun onLongItemClick(position: Int): Unit {
        val selectedTask = tasks[position]
        val taskDao = TaskDAO(this)

        val builder  = AlertDialog.Builder(this)
        builder.setTitle("Deletar tarefa")
        builder.setMessage("Deseja deletar a tarefa: ${selectedTask.description}?")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            if (taskDao.delete(selectedTask)) {
                loadTasks()
                Toast.makeText(this, "Tarefa removida com sucesso!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro ao remover tarefa!", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()

    }
}