package com.example.diyhomeautomation.models

import java.time.LocalTime

/**
 * Data class representing tasks in DIY home automation.
 *
 * @property id The unique identifier for the task.
 * @property name The name of the task.
 * @property state The state of the task.
 * @property initHour The initial time of the task.
 * @property endHour The ending time of the task.
 * @property daysOfWeek The days of the week when the task is scheduled.
 * @property taskDevices List of devices associated with the task (nullable).
 *
 * @constructor Creates an instance of Task with the provided properties.
 */
data class Task(
    var id: Int,
    var name: String,
    var state: Boolean,
    var initHour: LocalTime,
    var endHour: LocalTime,
    var daysOfWeek: String,
    var taskDevices: List<TaskDevice>?
)