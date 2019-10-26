package com.sun.doitpat.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sun.doitpat.base.BaseViewModel
import com.sun.doitpat.data.model.ToDo
import com.sun.doitpat.data.repository.ToDoRepository
import com.sun.doitpat.util.Constants.COMPLETED
import com.sun.doitpat.util.Constants.NEW
import kotlinx.coroutines.launch

class ToDoViewModel(private val toDoRepository: ToDoRepository) : BaseViewModel() {

    val list = MutableLiveData<List<ToDo>>()

    init {
        getNewToDo()
    }

    fun deleteItem(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.deleteToDo(toDo)
        }
    }

    fun updateItem(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.updateToDo(toDo.copy(status = COMPLETED))
        }
    }

    fun undoItem(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.updateToDo(toDo.copy(status = NEW))
        }
    }

    fun getNewToDo() {
        viewModelScope.launch {
            list.value = toDoRepository.getNewToDo().asReversed()
        }
    }

    fun getCompletedToDo() {
        viewModelScope.launch {
            list.value = toDoRepository.getCompletedToDo().asReversed()
        }
    }

}
