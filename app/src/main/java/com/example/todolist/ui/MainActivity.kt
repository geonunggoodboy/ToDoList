package com.example.todolist.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.domain.Todo
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TodoAdapter.OnTodoItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(this, this)
        binding.rvTodoList.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[TodoViewModel::class.java]

        viewModel.getAll().observe(this) {
            adapter.setTodoList(it)
            binding.rvTodoList.scrollToPosition(it.lastIndex)
        }

        adapter.notifyDataSetChanged()

        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(16)
        binding.rvTodoList.addItemDecoration(verticalSpaceItemDecoration)

        binding.btnInput.setOnClickListener {
            val input = binding.etTodoInput.text.toString()
            val dataFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            val currentTime = dataFormat.format(System.currentTimeMillis())
            if(input.isNotEmpty()){
                val todo = Todo(
                    content = input,
                    timestamp = currentTime
                )
                viewModel.insert(todo)
                binding.etTodoInput.text.clear()
                hideKeyBoard()
            } else {
                Toast.makeText(this, "Please write a to-do item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDeleteButtonClickListener(todo: Todo) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this to-do?")
            .setCancelable(false)
            .setPositiveButton("Yes"){dialog, _ ->
                viewModel.delete(todo)
                dialog.dismiss()
            }
            .setNegativeButton("No"){dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun hideKeyBoard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}