package com.example.bai1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bai1.R

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNumber: EditText
    private lateinit var buttonShow: Button
    private lateinit var listViewNumbers: ListView
    private lateinit var textViewError: TextView
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        buttonShow = findViewById(R.id.buttonShow)
        listViewNumbers = findViewById(R.id.listViewNumbers)
        textViewError = findViewById(R.id.textViewError)
        radioGroup = findViewById(R.id.radio_Group)

        buttonShow.setOnClickListener { showNumbers() }
    }

    private fun showNumbers() {
        val input = editTextNumber.text.toString()
        val n = input.toIntOrNull()

        if (n == null || n < 0) {
            textViewError.text = "Vui lòng nhập số nguyên dương hợp lệ."
            textViewError.visibility = View.VISIBLE
            listViewNumbers.adapter = null
            return
        }

        textViewError.visibility = View.GONE
        val selectedId = radioGroup.checkedRadioButtonId
        val numbersList = mutableListOf<Int>()

        when (selectedId) {
            R.id.radioEven -> {
                for (i in 0..n step 2) numbersList.add(i)
            }
            R.id.radioOdd -> {
                for (i in 1..n step 2) numbersList.add(i)
            }
            R.id.radioSquare -> {
                for (i in 0..n) {
                    val square = i * i
                    if (square <= n) numbersList.add(square)
                }
            }
            else -> {
                textViewError.text = "Vui lòng chọn loại số."
                textViewError.visibility = View.VISIBLE
                listViewNumbers.adapter = null
                return
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
        listViewNumbers.adapter = adapter
    }
}