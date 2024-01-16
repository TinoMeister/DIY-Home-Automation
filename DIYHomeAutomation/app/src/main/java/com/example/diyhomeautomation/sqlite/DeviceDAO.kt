package com.example.diyhomeautomation.sqlite

import IDevice
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getStringOrNull
import com.example.diyhomeautomation.models.Device
import com.google.gson.Gson

/**
 * Implementation of the [IDevice] interface for CRUD operations on the Device table in the database.
 *
 * @property context The application context.
 */
class DeviceDAO(context: Context) : IDevice {
    private val write = DatabaseHelper(context).writableDatabase
    private val read = DatabaseHelper(context).readableDatabase

    /**
     * Inserts a new device into the Device table.
     *
     * @param device The device to be inserted.
     * @return True if the insertion was successful, false otherwise.
     */
    override fun insertDevice(device: Device): Boolean {
        val contents = ContentValues()
        contents.put(DatabaseHelper.DEVICE_ID, device.id)
        contents.put(DatabaseHelper.DEVICE_NAME, device.name)
        contents.put(DatabaseHelper.DEVICE_PINVALUE, device.pinValue)
        contents.put(DatabaseHelper.DEVICE_STATE, device.state)
        contents.put(DatabaseHelper.DEVICE_VALUE, device.value)
        contents.put(DatabaseHelper.DEVICE_ICON, device.icon)
        contents.put(DatabaseHelper.DEVICE_ROOMID, device.roomId)
        contents.put(DatabaseHelper.DEVICE_TYPEDEVICEID, device.typeDeviceId)
        contents.put(DatabaseHelper.DEVICE_TASKDEVICES, device.taskDevices.toString())

        try {
            write.insert(
                DatabaseHelper.TABLENAME_DEVICE,
                null,
                contents
            )
            Log.i("DeviceDAO", "Success inserting in database")
            return true
        } catch (e: Exception) {
            Log.e("DeviceDAO", "Error adding in database")
            return false
        }
    }

    /**
     * Retrieves all devices from the Device table.
     *
     * @return List of devices.
     */
    override fun getAllDevices(): List<Device> {
        val gson = Gson()
        val listDevices = mutableListOf<Device>()
        val sql = "SELECT * FROM ${DatabaseHelper.TABLENAME_DEVICE}"
        val cursor = read.rawQuery(sql, null)
        val indexDeviceId = cursor.getColumnIndex(DatabaseHelper.DEVICE_ID)
        val indexDeviceName = cursor.getColumnIndex(DatabaseHelper.DEVICE_NAME)
        val indexDevicePinValue = cursor.getColumnIndex(DatabaseHelper.DEVICE_PINVALUE)
        val indexDeviceState = cursor.getColumnIndex(DatabaseHelper.DEVICE_STATE)
        val indexDeviceValue = cursor.getColumnIndex(DatabaseHelper.DEVICE_VALUE)
        val indexDeviceIcon = cursor.getColumnIndex(DatabaseHelper.DEVICE_ICON)
        val indexDeviceRoomId = cursor.getColumnIndex(DatabaseHelper.DEVICE_ROOMID)
        //val indexDeviceTypeDeviceId = cursor.getColumnIndex(DatabaseHelper.DEVICE_TYPEDEVICEID)
        //val indexDeviceTaskDevice = cursor.getColumnIndex(DatabaseHelper.DEVICE_TASKDEVICES)

        while (cursor.moveToNext()) {
            val DeviceId = cursor.getInt(indexDeviceId)
            val DeviceName = cursor.getString(indexDeviceName)
            val DevicePinValue = cursor.getString(indexDevicePinValue)
            val DeviceState = cursor.getInt(indexDeviceState) == 1
            val DeviceValue = cursor.getDoubleOrNull(indexDeviceValue)
            val DeviceIcon = cursor.getStringOrNull(indexDeviceIcon)
            val DeviceRoomId = cursor.getInt(indexDeviceRoomId)
            //val DeviceTypeDeviceId = cursor.getIntOrNull(indexDeviceTypeDeviceId)
            //val DeviceTaskDeviceJson = cursor.getString(indexDeviceTaskDevice)
            //val DeviceTaskDevice = gson.fromJson(DeviceTaskDeviceJson, object : TypeToken<List<TaskDevice>>() {}.type)

            listDevices.add(
                Device(DeviceId, DeviceName, DevicePinValue, DeviceState, DeviceValue,
                    DeviceIcon, DeviceRoomId, null, null)
            )
        }
        return listDevices
    }

    /**
     * Removes a device from the Device table.
     *
     * @param deviceId The ID of the device to be removed.
     * @return True if the removal was successful, false otherwise.
     */
    override fun removeDevice(deviceId: Int): Boolean {
        // Create an array with the deviceId as a string
        val args = arrayOf(deviceId.toString())

        try {
            // Attempt to delete the device from the database
            write.delete(
                DatabaseHelper.TABLENAME_DEVICE,
                "${DatabaseHelper.DEVICE_ID} = ?",
                args
            )
            Log.i("DeviceDAO", "Successfully removed device data")
        } catch (e: Exception) {
            // Log an error message if an exception occurs during device removal
            Log.e("DeviceDAO", "Error removing device data")
            return false
        }
        return true
    }

    /**
     * Updates device information in the Device table.
     *
     * @param device The updated device.
     * @return True if the update was successful, false otherwise.
     */
    override fun updateDevice(device: Device): Boolean {
        // Create an array with the deviceId as a string
        val args = arrayOf(device.id.toString())

        // Create a ContentValues object to store the updated data
        val content = ContentValues()
        content.put(DatabaseHelper.DEVICE_NAME, device.name)
        content.put(DatabaseHelper.DEVICE_VALUE, device.value)

        try {
            // Attempt to update the device information in the database
            write.update(
                DatabaseHelper.TABLENAME_DEVICE,
                content,
                "${DatabaseHelper.DEVICE_ID} = ?",
                args
            )
            Log.i("DeviceDAO", "Successfully updated device data")
        } catch (e: Exception) {
            // Log an error message if an exception occurs during device update
            Log.e("DeviceDAO", "Error updating device data")
            return false
        }

        return true
    }

    fun getSQLiteDeviceIdByName(deviceName: String): Int {
        val args = arrayOf(deviceName)

        val cursor = read.query(
            DatabaseHelper.TABLENAME_DEVICE,
            arrayOf(DatabaseHelper.DEVICE_ID),
            "${DatabaseHelper.DEVICE_NAME} = ?",
            args,
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(DatabaseHelper.DEVICE_ID)
            val sqliteDeviceId = cursor.getInt(columnIndex)

            Log.d("DeviceDAO", "Device Name: $deviceName, SQLite Room ID: $sqliteDeviceId")

            if (columnIndex != -1) {
                sqliteDeviceId
            } else {
                Log.w("DeviceDAO", "Column index for DEVICE_ID not found in the cursor")
                -1
            }
        } else {
            Log.w("DeviceDAO", "No matching device found for Device Name: $deviceName")
            -1
        }
    }

    /**
     * Retrieves a list of devices associated with a specific room from the SQLite database.
     *
     * @param roomId The ID of the room for which devices are to be retrieved.
     * @return A mutable list of [Device] objects associated with the specified room.
     */
    fun getAllDevicesByRoom(roomId: Int): MutableList<Device> {
        val gson = Gson()
        val listDevices = mutableListOf<Device>()

        // SQL query to select devices belonging to the specified room
        val sql = "SELECT * FROM ${DatabaseHelper.TABLENAME_DEVICE}" +
                " WHERE ${DatabaseHelper.DEVICE_ROOMID} = ?"

        // Execute the query with the room ID as a parameter
        val cursor = read.rawQuery(sql, arrayOf(roomId.toString()))

        // Column indices in the cursor
        val indexDeviceId = cursor.getColumnIndex(DatabaseHelper.DEVICE_ID)
        val indexDeviceName = cursor.getColumnIndex(DatabaseHelper.DEVICE_NAME)
        val indexDevicePinValue = cursor.getColumnIndex(DatabaseHelper.DEVICE_PINVALUE)
        val indexDeviceState = cursor.getColumnIndex(DatabaseHelper.DEVICE_STATE)
        val indexDeviceValue = cursor.getColumnIndex(DatabaseHelper.DEVICE_VALUE)
        val indexDeviceIcon = cursor.getColumnIndex(DatabaseHelper.DEVICE_ICON)
        val indexDeviceRoomId = cursor.getColumnIndex(DatabaseHelper.DEVICE_ROOMID)

        // Iterate through the cursor to create Device objects and add them to the list
        while (cursor.moveToNext()) {
            val deviceId = cursor.getInt(indexDeviceId)
            val deviceName = cursor.getString(indexDeviceName)
            val devicePinValue = cursor.getString(indexDevicePinValue)
            val deviceState = cursor.getInt(indexDeviceState) == 1
            val deviceValue = cursor.getDoubleOrNull(indexDeviceValue)
            val deviceIcon = cursor.getStringOrNull(indexDeviceIcon)
            val deviceRoomId = cursor.getInt(indexDeviceRoomId)

            listDevices.add(
                Device(
                    deviceId, deviceName, devicePinValue, deviceState, deviceValue,
                    deviceIcon, deviceRoomId, null, null
                )
            )
        }
        cursor.close()
        return listDevices
    }

}