package com.example.stumng_roomlib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var students: List<Student> = emptyList(),
    private val onItemClick: (Student) -> Unit = {},
    private val onCheckedChange: (Student, Boolean) -> Unit = { _, _ -> } // Sửa lại để nhận student và trạng thái checkbox
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private val selectedStudents = mutableSetOf<Student>()

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.studentName)
        val mssv: TextView = view.findViewById(R.id.studentMSSV)
        val checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.name.text = student.name
        holder.mssv.text = student.mssv

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = selectedStudents.contains(student)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(student, isChecked) // Truyền cả student và trạng thái checkbox
            if (isChecked) selectedStudents.add(student) else selectedStudents.remove(student)
        }

        holder.itemView.setOnClickListener { onItemClick(student) }
    }

    override fun getItemCount() = students.size

    // Cập nhật dữ liệu cho adapter
    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    // Lấy danh sách các sinh viên đã được chọn
    fun getSelectedStudents(): List<Student> = selectedStudents.toList()
}
