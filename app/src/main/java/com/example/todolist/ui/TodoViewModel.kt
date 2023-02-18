package com.example.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.AppDatabase
import com.example.todolist.domain.Todo
import com.example.todolist.domain.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository
    val _todoList: LiveData<List<Todo>>

    init {
        val todoDAO = checkNotNull(AppDatabase.getInstance(application)){"Database is not initialized"}.todoDAO()
        repository = TodoRepository(todoDAO)
        _todoList = repository.todoList
    }

    fun getAll(): LiveData<List<Todo>>{
        return _todoList
    }

    fun insert(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }
}