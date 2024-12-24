package com.example.stumng_roomlib

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var dao: StudentDao
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Room Database và DAO
        dao = AppDatabase.getDatabase(applicationContext).studentDao()

        // Khởi tạo ViewModel
        viewModel = ViewModelProvider(this, MainViewModelFactory(dao)).get(MainViewModel::class.java)

        // Khởi tạo RecyclerView và Adapter
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Truyền dữ liệu và hành động vào adapter
        adapter = StudentAdapter(
            students = emptyList(),  // Dữ liệu ban đầu có thể là danh sách trống
            onItemClick = { student ->
                // Xử lý khi một item được click
                Toast.makeText(this, "Clicked: ${student.name}", Toast.LENGTH_SHORT).show()
            },
            onCheckedChange = { student, isChecked ->
                // Xử lý khi trạng thái checkbox thay đổi
                student.isChecked = isChecked
                dao.updateStudent(student)  // Lưu lại thay đổi trong database
                Toast.makeText(this, "Checked state: ${isChecked}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        // Kiểm tra nếu cơ sở dữ liệu trống và thêm dữ liệu mẫu
        addSampleDataIfNeeded()

        // Quan sát LiveData từ ViewModel
        viewModel.students.observe(this) { students ->
            // Cập nhật dữ liệu cho RecyclerView
            adapter.updateData(students)
        }
    }

    // Kiểm tra và thêm dữ liệu mẫu nếu cơ sở dữ liệu trống
    private fun addSampleDataIfNeeded() {
        CoroutineScope(Dispatchers.IO).launch {
            val existingStudents = dao.getAllStudentsSync()
            if (existingStudents == null || existingStudents.isEmpty()) {
val students = listOf(
    Student(mssv = "8888001", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
    Student(mssv = "8888002", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
    Student(mssv = "8888003", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
    Student(mssv = "8888004", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv")
                )
                dao.insertAll(students)
            }
        }
    }
}

//val students = listOf(
//    Student(mssv = "8888001", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
//    Student(mssv = "8888002", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
//    Student(mssv = "8888003", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv"),
//    Student(mssv = "8888004", name = "John Doe", dob = "2/2/2013", email="a.@gmail.cv")