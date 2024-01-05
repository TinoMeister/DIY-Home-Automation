package com.example.diyhomeautomation.models

data class AuthResponse (
    var userID: String,
    var token: String,
    var expiration: String
)