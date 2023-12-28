package com.example.diyhomeautomation.schedules

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.R

class ScheduleCUActivity : AppCompatActivity() {
    private lateinit var nextBtn: Button
    private lateinit var backBtn: Button
    private lateinit var addDeviceBtn: Button
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeView(R.layout.activity_schedule_cu)

    }

    private fun changeView(view: Int)
    {
        setContentView(view)

        if (view == R.layout.activity_schedule_cu)
        {
            nextBtn = findViewById(R.id.schedule_cu_next_btn)

            nextBtn.setOnClickListener {
                changeView(R.layout.activity_schedule_cu_cont)
            }
        }
        else
        {
            backBtn = findViewById(R.id.schedule_cu_back_btn)
            addDeviceBtn = findViewById(R.id.schedule_cu_add_btn)
            saveBtn = findViewById(R.id.schedule_cu_save_btn)

            backBtn.setOnClickListener {
                changeView(R.layout.activity_schedule_cu)
            }

            addDeviceBtn.setOnClickListener {
                val intent = Intent(this, ScheduleDeviceAddActivity::class.java)
                startActivity(intent)
            }

            saveBtn.setOnClickListener {
            }
        }
    }
}