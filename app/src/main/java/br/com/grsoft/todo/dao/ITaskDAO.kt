package br.com.grsoft.todo.dao

import br.com.grsoft.todo.model.Task

interface ITaskDAO {
    fun save(task: Task): Boolean
    fun update(task: Task): Boolean
    fun delete(task: Task): Boolean
    fun list(): List<Task>
}