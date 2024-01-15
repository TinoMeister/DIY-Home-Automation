package com.example.diyhomeautomation.sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.core.database.getStringOrNull
import com.example.diyhomeautomation.models.Room

/**
 * Class implementing [IRoom] interface for managing room data in a SQLite database.
 *
 * @property write Writable instance of the SQLite database.
 * @property read Readable instance of the SQLite database.
 */
class RoomDAO(context: Context) : IRoom {
    private val write = DatabaseHelper(context).writableDatabase
    private val read = DatabaseHelper(context).readableDatabase

    /**
     * Inserts a room into the database.
     *
     * @param room The room object to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    override fun insertRoom(room: Room): Boolean {
        val contents = ContentValues()
        contents.put(DatabaseHelper.ROOM_ID, room.id)
        contents.put(DatabaseHelper.ROOM_NAME, room.name)
        contents.put(DatabaseHelper.ROOM_ICON, room.icon)
        contents.put(DatabaseHelper.ROOM_USERID, room.userId)
        contents.put(DatabaseHelper.ROOM_ESPID, room.espId)

        try {
            write.insert(
                DatabaseHelper.TABLENAME_ROOM,
                null,
                contents
            )
            Log.i("RoomDAO", "Success inserting in database")
            return true
        } catch (e: Exception) {
            Log.e("RoomDAO", "Error adding in database")
            return false
        }
    }

    /**
     * Retrieves all rooms from the database.
     *
     * @return A list of [Room] objects representing all rooms in the database.
     */
    override fun getAllRooms(): List<Room> {
        val listRooms = mutableListOf<Room>()
        val sql = "SELECT * FROM ${DatabaseHelper.TABLENAME_ROOM}"
        val cursor = read.rawQuery(sql, null)
        val indexRoomId = cursor.getColumnIndex(DatabaseHelper.ROOM_ID)
        val indexRoomName = cursor.getColumnIndex(DatabaseHelper.ROOM_NAME)
        val indexRoomIcon = cursor.getColumnIndex(DatabaseHelper.ROOM_ICON)
        val indexRoomUserId = cursor.getColumnIndex(DatabaseHelper.ROOM_USERID)
        val indexRoomEspId = cursor.getColumnIndex(DatabaseHelper.ROOM_ESPID)

        while (cursor.moveToNext()) {
            val RoomId = cursor.getInt(indexRoomId)
            val RoomName = cursor.getString(indexRoomName)
            val RoomIcon = cursor.getStringOrNull(indexRoomIcon)
            val RoomUserId = cursor.getString(indexRoomUserId)
            val RoomEspId = cursor.getInt(indexRoomEspId)

            listRooms.add(
                Room(RoomId, RoomName, RoomIcon, RoomUserId, RoomEspId)
            )
        }
        return listRooms
    }
}
