package com.example.diyhomeautomation.models

import java.io.Serializable

/**
 * Data class representing rooms in the DIY Home Automation system.
 *
 * @property id The unique identifier for the room.
 * @property name The name of the room.
 * @property icon The icon associated with the room (nullable).
 * @property userId The user ID associated with the room.
 * @property espId The ID of the associated ESP (Embedded System).
 *
 * @constructor Creates an instance of Room with the provided properties.
 */
data class Room(
    var id: Int,
    var name: String,
    var icon: String?,
    var userId: String,
    var espId: Int
)