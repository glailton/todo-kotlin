package br.com.grsoft.todo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.grsoft.todo.R
import br.com.grsoft.todo.dao.implementation.TaskDAO
import br.com.grsoft.todo.model.Task
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        task = intent.getSerializableExtra("task") as? Task

        if (task != null) {
            inpTextTask.setText( task?.description.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_save -> {
                if (inpTextTask.text.toString().isNullOrBlank()) {
                    inpTextTask.error = "Digite uma descrição para a tarefa"
                    return false
                }
                val taskDao = TaskDAO(this)
                if (task != null) {
                    val newTask = Task(task?.id, inpTextTask.text.toString())
                    if (taskDao.update(newTask)) {
                        Toast.makeText(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Erro ao atualizada tarefa!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val task = Task(inpTextTask.text.toString())
                    if (taskDao.save(task)) {
                        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Erro ao salvar tarefa!", Toast.LENGTH_LONG).show()
                    }
                }

                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}