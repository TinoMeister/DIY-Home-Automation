package com.example.diyhomeautomation.homes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.customs.CustomRoomDevicesAdapter

class RoomActivity : AppCompatActivity() {
    private val addBtn: Button by lazy {
        findViewById(R.id.room_add_btn)
    }

    private val btnEdit: Button by lazy {
        findViewById(R.id.room_edit_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
            "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

        val lst = findViewById<RecyclerView>(R.id.room_rv)
        lst.setHasFixedSize(true)
        lst.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = CustomRoomDevicesAdapter(testValues.toMutableList())
        lst.adapter = adapter

        adapter.onItemClick = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        addBtn.setOnClickListener {
            val intent = Intent(this, DeviceAddActivity::class.java)
            startActivity(intent)
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, DeviceActivity::class.java)
            startActivity(intent)
        }
    }
}