package com.example.todolist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemRecyclerBinding
import com.example.todolist.domain.Todo
import java.text.SimpleDateFormat
import java.util.*


class TodoAdapter(private val context: Context, private val listener: OnTodoItemClickListener): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    interface OnTodoItemClickListener{
        fun onDeleteButtonClickListener(todo: Todo)
    }

    private var todoList: List<Todo> = emptyList()

    inner class TodoViewHolder(private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo, listener: OnTodoItemClickListener){
            binding.tvTodo.text = todo.content
            binding.tvTime.text = todo.timestamp

            binding.btnDelete.setOnClickListener{
                listener.onDeleteButtonClickListener(todo)
            }
        }
    }

    fun setTodoList(newTodoList: List<Todo>){
        this.todoList = newTodoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.bind(todo, listener)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

}