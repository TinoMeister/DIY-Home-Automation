package com.example.diyhomeautomation.models

/**
 * Data class representing types of devices in DIY home automation.
 *
 * @property id The unique identifier for the device type.
 * @property name The name of the device type.
 *
 * @constructor Creates an instance of TypeDevice with the provided properties.
 */
data class TypeDevice(
    var id: Int,          // Property to store the device type ID
    var name: String      // Property to store the name of the device type
)