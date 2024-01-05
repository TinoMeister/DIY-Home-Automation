package com.example.diyhomeautomation.models

import java.io.Serializable

data class Room(
    var id: Int?,
    var name: String,
    var icon: String?,
    var userId: String,
    var espId: Int
)