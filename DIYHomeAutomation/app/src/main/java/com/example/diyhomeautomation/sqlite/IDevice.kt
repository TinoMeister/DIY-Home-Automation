import com.example.diyhomeautomation.models.Device

/**
 * Interface defining operations for managing devices in a database.
 */
interface IDevice {
    /**
     * Inserts a device into the database.
     *
     * @param device The device object to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    fun insertDevice(device: Device): Boolean

    /**
     * Updates the information of a device in the database.
     *
     * @param device The updated device object.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateDevice(device: Device): Boolean

    /**
     * Removes a device from the database based on its ID.
     *
     * @param deviceId The ID of the device to be removed.
     * @return `true` if the removal is successful, `false` otherwise.
     */
    fun removeDevice(deviceId: Int): Boolean

    /**
     * Retrieves a list of all devices from the database.
     *
     * @return A list of devices stored in the database.
     */
    fun getAllDevices(): List<Device>
}
