package com.example.stumng_roomlib

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val mssv: String,
    val name: String,
    val dob: String,
    val email: String
)