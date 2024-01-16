package com.example.diyhomeautomation.models

import java.util.Date

/**
 * Data class representing historical data related to a device.
 *
 * @property id The unique identifier for the history entry.
 * @property value The recorded value associated with the history entry.
 * @property date The date of the recorded data.
 * @property deviceId The ID of the device associated with the history entry.
 *
 * @constructor Creates an instance of History with the provided properties.
 */
data class History(
    var id: Int,
    var value: Double,
    var date: Date,
    var deviceId: Int
)