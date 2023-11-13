package com.example.diyhomeautomation.models

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Firebase(name: String, email: String, pass: String) {
    private lateinit var auth: FirebaseAuth
    val name: String
    val email: String
    val pass: String

    init {
        this.name = name
        this.email= email
        this.pass = pass
    }
    fun doRegister(name: String, email: String, pass: String){
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    TODO()
                } else {
                    TODO()
                }
            }
    }

    fun doLogin(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    TODO()
                } else {
                    TODO()
                }
            }
    }

}

