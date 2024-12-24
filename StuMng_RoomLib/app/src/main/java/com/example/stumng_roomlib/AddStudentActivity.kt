package com.example.stumng_roomlib

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val mssvEditText = findViewById<EditText>(R.id.mssvEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val addButton = findViewById<Button>(R.id.addButton)

        val dao = AppDatabase.getDatabase(application).studentDao()

        addButton.setOnClickListener {
            val mssv = mssvEditText.text.toString()
            val name = nameEditText.text.toString()
            val dob = dobEditText.text.toString()
            val email = emailEditText.text.toString()

            if (mssv.isEmpty() || name.isEmpty() || dob.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newStudent = Student(mssv, name, dob, email)
            GlobalScope.launch {
                dao.insertStudent(newStudent)
                runOnUiThread {
                    Toast.makeText(this@AddStudentActivity, "Student added!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}