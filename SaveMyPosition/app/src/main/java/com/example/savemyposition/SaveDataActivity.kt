package com.example.savemyposition

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_savedata.*


data class Postion (
    val Time: String,
    val latitude: String,
    val longitude: String
)


class SaveDataActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savedata)

        val helper = DBHelper(this)
        val adapter = SaveDataAdapter()

        val postions = helper.getPostion()
        adapter.listData.addAll(postions)

        position_recyle.adapter = adapter
        position_recyle.layoutManager = LinearLayoutManager(this)


        delete_button.setOnClickListener {
            Toast.makeText(this, "오래된 데이터를 지웁니다", Toast.LENGTH_SHORT).show()
            helper.deletePosition()
        }



    }

}