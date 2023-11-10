package com.example.diyhomeautomation.homes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.customs.CustomRoomDevicesRecyclerView

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
            "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

        val lst = findViewById<RecyclerView>(R.id.room_rv)
        lst.setHasFixedSize(true)
        lst.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = CustomRoomDevicesRecyclerView(testValues.toMutableList())
        lst.adapter = adapter

        adapter.onItemClick = {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}