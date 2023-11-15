package com.example.diyhomeautomation.models

import java.time.LocalTime

data class Task(
    var id: Int,
    var name: String,
    var state: GeneralState,
    var initHour: LocalTime,
    var endHour: LocalTime,
    var daysOfWeek: String,
    var taskDevices: List<TaskDevice>?
)