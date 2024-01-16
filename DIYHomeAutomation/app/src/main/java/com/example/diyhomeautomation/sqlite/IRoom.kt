package com.example.diyhomeautomation.sqlite

import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.models.Room

/**
 * Interface defining operations for managing rooms in a database.
 */
interface IRoom {
    /**
     * Inserts a room into the database.
     *
     * @param room The room object to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    fun insertRoom(room: Room): Boolean

    /**
     * Retrieves a list of all rooms from the database.
     *
     * @return A list of rooms stored in the database.
     */
    fun getAllRooms(): List<Room>

    fun removeRoom(roomId: Int): Boolean

    fun updateRoom(room: Room): Boolean
}
