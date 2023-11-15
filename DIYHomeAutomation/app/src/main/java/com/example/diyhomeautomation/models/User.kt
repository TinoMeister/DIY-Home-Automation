package com.example.diyhomeautomation.models

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class User(name: String, email: String, pass: String){
    private lateinit var auth: FirebaseAuth
    val name: String
    val email: String
    val pass: String
    init {
        this.name = name
        this.email= email
        this.pass = pass
    }

    val userList = mutableListOf(
        User("Francisco", "francisco@gmail.com", "random"),
        User("Tiago", "tiago@gmail.com","random2"),
        User("Luis", "luis@gmail.com", "random3")
    )
}


