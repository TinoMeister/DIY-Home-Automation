package com.example.diyhomeautomation.models

import java.util.Date

/**
 * Data class representing notifications related to tasks and devices.
 *
 * @property id The unique identifier for the notification.
 * @property name The name of the notification.
 * @property description The description of the notification.
 * @property time The time the notification occurred.
 * @property visualizeState Indicates whether the notification should be visually represented.
 * @property taskId The ID of the associated task.
 * @property deviceId The ID of the associated device.
 *
 * @constructor Creates an instance of Notification with the provided properties.
 */
data class Notification(
    var id: Int,
    var name: String,
    var description: String,
    var time: Date,
    var visualizeState: Boolean,
    var taskId: Int,
    var deviceId: Int
)