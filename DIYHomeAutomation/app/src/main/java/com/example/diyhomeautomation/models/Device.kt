package com.example.diyhomeautomation.models

/**
 * Data class representing a device in the DIY Home Automation system.
 *
 * @property id The unique identifier for the device (nullable).
 * @property name The name of the device.
 * @property pinValue The pin value associated with the device (nullable).
 * @property state The state of the device (on/off) (nullable).
 * @property value The value associated with the device (nullable).
 * @property icon The icon representing the device (nullable).
 * @property roomId The ID of the room where the device is located.
 * @property typeDeviceId The ID of the device type (nullable).
 * @property taskDevices List of associated task devices (nullable).
 *
 * @constructor Creates an instance of Device with the provided properties.
 */
data class Device(
    var id: Int?,
    var name: String,
    var pinValue: String?,
    var state: Boolean?,
    var value: Double?,
    var icon: String?,
    var roomId: Int,
    var typeDeviceId: Int?,
    var taskDevices: List<TaskDevice>?
)