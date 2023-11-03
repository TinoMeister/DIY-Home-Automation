package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.util.Patterns
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val emailET: EditText by lazy {
        findViewById(R.id.register_email_til)
    }
    private val passwordET: EditText by lazy {
        findViewById(R.id.register_password_til)
    }
    /*private val nameET: EditText by lazy {
        findViewById(R.id.register_name_til)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth

        val btnLogin = findViewById<Button>(R.id.register_login_btn)
        btnLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun doRegister(v: View){
        //val name = nameET.text.toString()
        val email = emailET.text.toString()
        val password = passwordET.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Register success",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Register failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}