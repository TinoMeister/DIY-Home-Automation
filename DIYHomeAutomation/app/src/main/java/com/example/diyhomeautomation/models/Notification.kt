package com.example.diyhomeautomation.models

import java.util.Date

data class Notification(
    var id: Int,
    var name: String,
    var description: String,
    var time: Date,
    var visualizeState: Boolean,
    var taskId: Int,
    var deviceId: Int
)