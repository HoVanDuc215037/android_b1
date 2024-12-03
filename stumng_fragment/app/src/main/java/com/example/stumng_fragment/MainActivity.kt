package com.example.stumng_fragment

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var lv: ListView
    val addFragment=AddFragment()
    var editMode=0
    var deleteMode=0
    val students = mutableListOf(
        Student("Nguyễn Văn An", "SV001"),
        Student("Trần Thị Bảo", "SV002"),
        Student("Lê Hoàng Cường", "SV003"),
        Student("Phạm Thị Dung", "SV004"),
        Student("Đỗ Minh Đức", "SV005"),
        Student("Vũ Thị Hoa", "SV006"),
        Student("Hoàng Văn Hải", "SV007"),
        Student("Bùi Thị Hạnh", "SV008"),
        Student("Đinh Văn Hùng", "SV009"),
        Student("Nguyễn Thị Linh", "SV010"),
        Student("Phạm Văn Long", "SV011"),
        Student("Trần Thị Mai", "SV012"),
        Student("Lê Thị Ngọc", "SV013"),
        Student("Vũ Văn Nam", "SV014"),
        Student("Hoàng Thị Phương", "SV015"),
        Student("Đỗ Văn Quân", "SV016"),
        Student("Nguyễn Thị Thu", "SV017"),
        Student("Trần Văn Tài", "SV018"),
        Student("Phạm Thị Tuyết", "SV019"),
        Student("Lê Văn Vũ", "SV020")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        lv=findViewById(R.id.lv)
        val adapter = StudentAdapter(this, R.layout.layout_item, students)
        lv.adapter = adapter
        registerForContextMenu(lv)

        findViewById<Button>(R.id.add_btn).setOnClickListener{
            supportFragmentManager.beginTransaction()
                .add(R.id.layout_add, addFragment, "TAG")
                .addToBackStack("TAG")
                .commit()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId) {
            R.id.menu_edit -> {
                val student = students[position]

                true
            }
            R.id.menu_remove -> {
                students.removeAt(position)
                (lv.adapter as StudentAdapter).notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}