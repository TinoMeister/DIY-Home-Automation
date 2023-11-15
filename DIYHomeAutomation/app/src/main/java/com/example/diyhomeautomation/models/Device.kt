package com.example.diyhomeautomation.models

data class Device(
    var id: Int,
    var name: String,
    var pinValue: String,
    var state: GeneralState,
    var value: Double?,
    var icon: String?,
    var roomId: Int,
    var typeSensorId: Int,
    var taskDevices: List<TaskDevice>?
)