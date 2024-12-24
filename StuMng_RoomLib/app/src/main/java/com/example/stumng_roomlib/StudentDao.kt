package com.example.stumng_roomlib

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {
    // Thêm một sinh viên
    @Insert
    suspend fun insertStudent(student: Student)

    // Xóa danh sách sinh viên
    @Delete
    suspend fun deleteStudents(students: List<Student>)

    @Insert
    suspend fun insertAll(students: List<Student>)

    @Query("SELECT * FROM students")
    suspend fun getAllStudentsSync(): List<Student> // Phương thức đồng bộ

    // Tìm kiếm sinh viên
    @Query("SELECT * FROM students WHERE mssv LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%'")
    fun searchStudents(query: String): LiveData<List<Student>>

    // Lấy sinh viên theo MSSV
    @Query("SELECT * FROM students WHERE mssv = :mssv LIMIT 1")
    fun getStudentById(mssv: String): LiveData<Student>
}