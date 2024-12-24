package com.example.stumng_roomlib

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val studentId = intent.getStringExtra("STUDENT_ID") ?: return
        val dao = AppDatabase.getDatabase(application).studentDao()
        viewModel = ViewModelProvider(this, MainViewModelFactory(dao)).get(MainViewModel::class.java)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        viewModel.students.observe(this) { students ->
            student = students.firstOrNull { it.mssv == studentId } ?: return@observe
            nameEditText.setText(student.name)
            dobEditText.setText(student.dob)
            emailEditText.setText(student.email)
        }

        updateButton.setOnClickListener {
            val updatedStudent = student.copy(
                name = nameEditText.text.toString(),
                dob = dobEditText.text.toString(),
                email = emailEditText.text.toString()
            )
            GlobalScope.launch {
                dao.insertStudent(updatedStudent)
                runOnUiThread { Toast.makeText(this@DetailActivity, "Updated!", Toast.LENGTH_SHORT).show() }
            }
        }

        deleteButton.setOnClickListener {
            GlobalScope.launch {
                dao.deleteStudents(listOf(student))
                runOnUiThread {
                    Toast.makeText(this@DetailActivity, "Deleted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}