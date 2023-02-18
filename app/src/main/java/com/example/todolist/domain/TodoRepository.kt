package com.example.todolist.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todolist.data.AppDatabase
import com.example.todolist.data.TodoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class TodoRepository(private val todoDAO: TodoDAO) {

    val todoList: LiveData<List<Todo>> = todoDAO.getAll()

    suspend fun insert(todo: Todo){
        withContext(Dispatchers.IO){
            val existingTodo = todoDAO.getTodoByContent(todo.content)
            if(existingTodo == null){
                todoDAO.insert(todo)
            } else {
                val dataFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
                val currentTime = dataFormat.format(System.currentTimeMillis())
                val updateTodo = existingTodo.copy(
                    timestamp = currentTime
                )
                todoDAO.update(updateTodo)
            }
        }
    }

    fun delete(todo: Todo){
        todoDAO.delete(todo)
    }

    init {
        todoList.observeForever{todos->
            Log.d("DataTest", "${todos.size}")
        }
    }
}