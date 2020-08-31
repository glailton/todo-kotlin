package br.com.grsoft.todo.listener

interface IOnTaskListener {
    fun onItemClick(position: Int)
    fun onLongItemClick(position: Int): Unit
}