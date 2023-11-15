package com.example.diyhomeautomation.models

data class TaskDevice(
    var id: Int,
    var taskId: Int,
    var task: Task,
    var deviceId: Int,
    var device: Device
)