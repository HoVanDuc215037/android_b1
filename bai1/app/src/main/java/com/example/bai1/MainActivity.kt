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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    val chkChan: RadioButton = findViewById(R.id.radio_chan)
    val chkLe: RadioButton = findViewById(R.id.radio_le)
    val chkChinhPhuong: RadioButton = findViewById(R.id.radio_chinhphuong)
    val buttonShow: Button = findViewById(R.id.show_btn)
    val radgroup: RadioGroup = findViewById(R.id.group_radio)
    val inputtext: EditText = findViewById(R.id.input_n)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonShow.setOnClickListener {
            val str_n=inputtext.text
            val n = parseInt(str_n.toString())
            val radioId = radgroup.checkedRadioButtonId
            if (chkChan.id == radioId) perform(1, n)
            if (chkLe.isChecked) perform(2, n)
            if (chkChinhPhuong.isChecked) perform(1, n)
        }
    }
    fun perform(type: Int, n: Int) {
        var items: Array<Int> = arrayOf()
        var iter = 0;
        if(type==1){
            for( i in 0..n){
                if(i%2==0){
                    items[iter]=i;
                    iter++;
                }
            }
        }else if(type==2){
            for( i in 0..n){
                if(i%2!=0){
                    items[iter]=i;
                    iter++;
                }
            }
        }else if(type==3){//chinh phuong
            for( i in 0..n){
                if(i%2!=0){
                    items[iter]=0-i;
                    iter++;
                }
            }
        }

        var adapter: ArrayAdapter<Int> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items)
        val listView: ListView = findViewById(R.id.my_list)
        listView.adapter = adapter
    }
}

