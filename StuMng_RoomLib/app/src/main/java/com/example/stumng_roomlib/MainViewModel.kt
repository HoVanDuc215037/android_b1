package com.example.stumng_roomlib

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val studentDao: StudentDao) : ViewModel() {

    val students: LiveData<List<Student>> = studentDao.getAllStudents()

    fun searchStudents(query: String) {
        studentDao.searchStudents(query)
    }

    fun deleteStudents(selectedStudents: List<Student>) {
        viewModelScope.launch {
            studentDao.deleteStudents(selectedStudents)
        }
    }
}