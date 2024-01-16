package com.example.diyhomeautomation.homes

import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.sqlite.DeviceDAO
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for editing device details.
 *
 * This activity allows users to edit the details of a device, including its name, icon,
 * pin value, state, and value. Users can also delete the device from this activity.
 *
 * @property deviceId The ID of the device to be edited.
 * @property roomId The ID of the room to which the device belongs.
 * @property roomName The name of the room to which the device belongs.
 * @property token The authentication token for making API requests.
 * @property selectedButtonId The ID of the selected button/icon for the device.
 * @property isDeviceOn The state of the device (on/off).
 * @property autoCompleteTextView AutoCompleteTextView for displaying and editing the room name.
 * @property roomTv TextInputLayout for the room name.
 * @property name TextInputLayout for the device name.
 * @property pinvalue TextInputLayout for the pin value.
 * @property state ToggleButton for toggling the device state.
 * @property valueEdit TextInputLayout for the device value.
 * @property chairEdit ImageButton for selecting the chair icon.
 * @property bathEdit ImageButton for selecting the bath icon.
 * @property bedEdit ImageButton for selecting the bed icon.
 * @property tvEdit ImageButton for selecting the TV icon.
 * @property kitchenEdit ImageButton for selecting the kitchen icon.
 * @property garageEdit ImageButton for selecting the garage icon.
 * @property otherEdit ImageButton for selecting the other icon.
 * @property put Button for updating device details.
 * @property delete Button for deleting the device.
 */
class DeviceEditActivity: AppCompatActivity() {

    private lateinit var deviceDAO: DeviceDAO
    private lateinit var deviceId: String
    private lateinit var roomId: String
    private lateinit var roomName: String
    private lateinit var token: String
    private lateinit var deviceName: String
    private var selectedButtonId: String = ""
    private var isDeviceOn: Boolean = false
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private val roomTv: TextInputLayout by lazy{
        findViewById(R.id.room_nameEdit_actv)
    }
    private val name: TextInputLayout by lazy{
        findViewById(R.id.device_nameEdit_tv)
    }
    private val pinvalue: TextInputLayout by lazy{
        findViewById(R.id.device_pinValue_tv)
    }
    private val state: ToggleButton by lazy{
        findViewById(R.id.device_state)
    }
    private val valueEdit: TextInputLayout by lazy{
        findViewById(R.id.device_value_tv)
    }
    private val chairEdit: ImageButton by lazy{
        findViewById(R.id.device_chairEdit_btn)
    }
    private val bathEdit: ImageButton by lazy{
        findViewById(R.id.device_bathEdit_btn)
    }
    private val bedEdit: ImageButton by lazy{
        findViewById(R.id.device_bedEdit_btn)
    }
    private val tvEdit: ImageButton by lazy{
        findViewById(R.id.device_tvEdit_btn)
    }
    private val kitchenEdit: ImageButton by lazy{
        findViewById(R.id.device_kitchenEdit_btn)
    }
    private val garageEdit: ImageButton by lazy{
        findViewById(R.id.device_garageEdit_btn)
    }
    private val otherEdit: ImageButton by lazy{
        findViewById(R.id.device_otherEdit_btn)
    }
    private val put: Button by lazy{
        findViewById(R.id.device_addEdit_btn)
    }
    private val delete: Button by lazy{
        findViewById(R.id.device_delete_btn)
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_edit)

        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)
        roomName = intent.getStringExtra("roomName") ?: return
        roomId = intent.getStringExtra("roomId") ?: return
        token = intent.getStringExtra("token") ?: return
        deviceId = intent.getStringExtra("deviceId") ?: return
        deviceName = intent.getStringExtra("deviceName") ?: return
        deviceDAO = DeviceDAO(this@DeviceEditActivity)

        Log.d("DeviceEditActivity", "Token: $token")
        Log.d("DeviceEditActivity", "DeviceId: $deviceId")
        Log.d("DeviceEditActivity", "RoomId: $roomId")
        Log.d("DeviceEditActivity", "RoomName: $roomName")

        autoCompleteTextView = roomTv.editText as AutoCompleteTextView
        roomName.let {
            autoCompleteTextView.setText(roomName)
        }

        chairEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        chairEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bedEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        tvEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        kitchenEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        garageEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        otherEdit.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }

        isDeviceOn = state.isChecked
        state.setOnCheckedChangeListener { buttonView, isChecked ->
            isDeviceOn = isChecked
        }

        put.setOnClickListener {
            val name = name.editText!!.text.toString()
            val icon = selectedButtonId
            val pinValue = pinvalue.editText!!.text.toString()
            val state = isDeviceOn
            val value = valueEdit.editText!!.text.toString()

            val deviceIdSqlite = deviceDAO.getSQLiteDeviceIdByName(deviceName)
            val deviceUpdated = deviceDAO.updateDevice(Device(deviceIdSqlite, name, pinValue,
                state, value.toDoubleOrNull(), icon, roomId.toInt(), null, null))

            if(deviceUpdated) {
                // Use GlobalScope to launch a coroutine for handling API calls
                GlobalScope.launch {
                    val result = apiHelper.putDevice(
                        "Bearer $token", deviceId.toInt(),
                        Device(
                            deviceId.toInt(), name, pinValue, state, value.toDoubleOrNull(),
                            icon, roomId.toInt(), null, null
                        )
                    )
                    result.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                finish()
                            } else Log.e(
                                "DeviceEditActivity", "API call unsuccessful." +
                                        "Code: ${response.code()}"
                            )
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("DeviceEditActivity", "API call failed", t)
                        }
                    })
                }
            } else {
                Log.e("DeviceEditActivity", "Failed to update device from SQLite")
            }
        }

        delete.setOnClickListener {
            val deviceIdSqlite = deviceDAO.getSQLiteDeviceIdByName(deviceName)
            val deviceRemoved = deviceDAO.removeDevice(deviceIdSqlite)
            if(deviceRemoved) {
                GlobalScope.launch {
                    val result = apiHelper.deleteDevice("Bearer $token", deviceId.toInt())
                    result.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                finish()
                            } else Log.e(
                                "DeviceEditActivity", "API call unsuccessful." +
                                        "Code: ${response.code()}"
                            )
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("DeviceEditActivity", "API call failed", t)
                        }
                    })
                }
            } else {
                Log.e("DeviceEditActivity", "Failed to remove device from SQLite")
            }
        }

    }

    /**
     * Handles the click event for image buttons in the activity to select an icon for the device.
     *
     * This function is invoked when an image button is clicked to select an icon for the device. It
     * changes the color of the clicked button and returns the resource ID of the selected icon.
     *
     * @param buttonId The ID of the clicked image button.
     * @return The resource ID of the selected icon as a String.
     */
    private fun onImageButtonClicked(buttonId: Int): String {
        when (buttonId) {
            R.id.device_chairEdit_btn -> {
                changeButtonColor(chairEdit)
                return R.drawable.ic_chair.toString()
            }
            R.id.device_bathEdit_btn -> {
                changeButtonColor(bathEdit)
                return R.drawable.ic_bathtub.toString()
            }
            R.id.device_bedEdit_btn -> {
                changeButtonColor(bedEdit)
                return R.drawable.ic_bed.toString()
            }
            R.id.device_tvEdit_btn -> {
                changeButtonColor(tvEdit)
                return R.drawable.ic_tv.toString()
            }
            R.id.device_kitchenEdit_btn -> {
                changeButtonColor(kitchenEdit)
                return R.drawable.ic_kitchen.toString()
            }
            R.id.device_garageEdit_btn -> {
                changeButtonColor(garageEdit)
                return R.drawable.ic_garage.toString()
            }
            R.id.device_otherEdit_btn -> {
                changeButtonColor(otherEdit)
                return R.drawable.ic_other_house.toString()
            }
        }
        return ""
    }

    /**
     * Changes the background color of the provided image button.
     *
     * This function is used to visually indicate the selection of an image button by changing its
     * background color.
     *
     * @param button The image button whose color needs to be changed.
     */
    private fun changeButtonColor(button: ImageButton) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSecondary))
    }

}