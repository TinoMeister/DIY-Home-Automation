package com.example.diyhomeautomation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    val reg_name: TextInputLayout by lazy {
        findViewById(R.id.register_name_til)
    }
    val reg_email: TextInputLayout by lazy {
        findViewById(R.id.register_email_til)
    }
    val reg_password: TextInputLayout by lazy {
        findViewById(R.id.register_password_til)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn = findViewById<TextView>(R.id.register_register_btn)
        btn.setOnClickListener {  }

        val btnLogin = findViewById<Button>(R.id.register_login_btn)
        btnLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}