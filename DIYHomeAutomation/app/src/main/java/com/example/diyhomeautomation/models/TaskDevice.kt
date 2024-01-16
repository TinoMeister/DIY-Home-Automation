package com.example.diyhomeautomation.models

/**
 * Data class representing the association between tasks and devices in DIY home automation.
 *
 * @property id The unique identifier for the task-device association.
 * @property taskId The ID of the associated task.
 * @property task The associated task object.
 * @property deviceId The ID of the associated device.
 * @property device The associated device object.
 *
 * @constructor Creates an instance of TaskDevice with the provided properties.
 */
data class TaskDevice(
    var id: Int,               // Property to store the task-device association ID
    var taskId: Int,           // Property to store the ID of the associated task
    var task: Task,            // Property to store the associated task object
    var deviceId: Int,         // Property to store the ID of the associated device
    var device: Device         // Property to store the associated device object
)