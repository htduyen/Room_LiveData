package com.example.roomlivedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TodoRepository
    val allTodo: LiveData<List<Todo>>

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        allTodo = repository.allTodos
    }

    fun insert(todo:Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo:Todo) = viewModelScope.launch {
        repository.update(todo)
    }

    fun  delete(todo:Todo) = viewModelScope.launch {
        repository.delete(todo)
    }
    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }
}