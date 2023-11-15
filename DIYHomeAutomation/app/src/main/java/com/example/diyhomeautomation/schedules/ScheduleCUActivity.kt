package com.example.diyhomeautomation.schedules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.diyhomeautomation.R

class ScheduleCUActivity : AppCompatActivity() {
    val addDevice: Button by lazy{
        findViewById(R.id.schedule_cu_add_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_cu)


        addDevice.setOnClickListener {
            val intent = Intent(this, ScheduleDeviceAddActivity::class.java)
            startActivity(intent)
        }
    }
}