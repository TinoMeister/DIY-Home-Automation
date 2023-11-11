package com.example.diyhomeautomation.homes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.customs.CustomDevicesAdapter

class DeviceActivity : AppCompatActivity() {
    private val lst: GridView by lazy {
        findViewById(R.id.device_gv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
            "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

        val adapter = CustomDevicesAdapter(baseContext,
            R.layout.custom_devices_cardview,
            testValues.toMutableList())
        lst.adapter = adapter
    }
}