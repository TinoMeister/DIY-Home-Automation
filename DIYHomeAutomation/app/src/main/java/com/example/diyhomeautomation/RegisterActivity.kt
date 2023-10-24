package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnLogin = findViewById<Button>(R.id.register_login_btn)
        btnLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}