package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.domain.Todo

// Data Access Objects
@Dao
interface TodoDAO {
    @Query("SELECT * FROM todos")
    fun getAll(): LiveData<List<Todo>>

    @Query("SELECT * FROM todos WHERE content = :content")
    fun getTodoByContent(content: String): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("DELETE FROM todos WHERE content = :content")
    fun deleteByContent(content: String)
}