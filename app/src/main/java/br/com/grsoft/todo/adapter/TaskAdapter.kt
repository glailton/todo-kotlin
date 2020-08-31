package br.com.grsoft.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.grsoft.todo.R
import br.com.grsoft.todo.listener.IOnTaskListener
import br.com.grsoft.todo.model.Task
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class TaskAdapter(private val tasks: ArrayList<Task>,
                  private val context: Context,
                  private val listener: IOnTaskListener
): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.recyclerview_item_row, parent, false)
        return TaskHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasks[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }

        holder.itemView.setOnLongClickListener {
            listener.onLongItemClick(position)
            return@setOnLongClickListener true
        }

        holder.bindView(task)
    }

    inner class TaskHolder(view: View): RecyclerView.ViewHolder(view){

        private val description: TextView = view.itemDescription

        fun bindView(task: Task) {
            description.text = task.description
        }
    }
}