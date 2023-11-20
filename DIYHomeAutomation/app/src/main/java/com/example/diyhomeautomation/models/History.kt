package com.example.diyhomeautomation.models

import java.util.Date

data class History(
    var id: Int,
    var value: Double,
    var date: Date,
    var deviceId: Int
)