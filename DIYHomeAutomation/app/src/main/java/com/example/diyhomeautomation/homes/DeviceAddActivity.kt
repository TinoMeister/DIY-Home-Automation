package com.example.diyhomeautomation.homes

import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.models.Device
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * [DeviceAddActivity] is responsible for adding a new device to a room.
 *
 * @property roomName The name of the room to which the device will be added.
 * @property roomId The identifier of the room.
 * @property token Authentication token for API requests.
 * @property selectedButtonId The identifier of the currently selected button representing the device icon.
 * @property autoCompleteTextView AutoCompleteTextView for displaying and editing the room name.
 * @property roomNameTv TextInputLayout for the room name.
 * @property deviceName TextInputLayout for the device name.
 * @property bedDevice ImageButton for selecting a bed device icon.
 * @property chairDevice ImageButton for selecting a chair device icon.
 * @property bathDevice ImageButton for selecting a bath device icon.
 * @property tvDevice ImageButton for selecting a TV device icon.
 * @property kitchenDevice ImageButton for selecting a kitchen device icon.
 * @property garageDevice ImageButton for selecting a garage device icon.
 * @property otherDevice ImageButton for selecting an other device icon.
 * @property addBtn Button for adding the new device.
 */
class DeviceAddActivity : AppCompatActivity() {

    private lateinit var roomName: String
    private lateinit var roomId: String
    private lateinit var token: String
    private var selectedButtonId: String = ""
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private val roomNameTv: TextInputLayout by lazy {
        findViewById(R.id.room_name_actv)
    }
    private val deviceName: TextInputLayout by lazy{
        findViewById(R.id.device_name_tv)
    }
    private val bedDevice: ImageButton by lazy{
        findViewById(R.id.device_bed_btn)
    }
    private val chairDevice: ImageButton by lazy{
        findViewById(R.id.device_chair_btn)
    }
    private val bathDevice: ImageButton by lazy{
        findViewById(R.id.device_bath_btn)
    }
    private val tvDevice: ImageButton by lazy{
        findViewById(R.id.device_tv_btn)
    }
    private val kitchenDevice: ImageButton by lazy{
        findViewById(R.id.device_kitchen_btn)
    }
    private val garageDevice: ImageButton by lazy{
        findViewById(R.id.device_garage_btn)
    }
    private val otherDevice: ImageButton by lazy{
        findViewById(R.id.device_other_btn)
    }
    private val addBtn: Button by lazy{
        findViewById(R.id.device_add_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_add)

        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)
        roomName = intent.getStringExtra("roomName") ?: return
        roomId = intent.getStringExtra("roomId") ?: return
        token = intent.getStringExtra("token") ?: return

        Log.d("DeviceAddActivity", "RoomId: $roomId")
        Log.d("DeviceAddActivity", "RoomName: $roomName")
        Log.d("DeviceAddActivity", "Token: $token")

        autoCompleteTextView = roomNameTv.editText as AutoCompleteTextView
        roomName.let {
            autoCompleteTextView.setText(roomName)
        }

        chairDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bathDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bedDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        tvDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        kitchenDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        garageDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        otherDevice.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }

        addBtn.setOnClickListener {
            val name = deviceName.editText!!.text.toString()
            val icon = selectedButtonId

            GlobalScope.launch{
                val result = apiHelper.postDevice("Bearer $token", Device(null, name,
                    "",false,0.0, icon, roomId.toInt(),
                    null, null)
                )
                result.enqueue(object: Callback<Device> {
                    override fun onResponse(call: Call<Device>, response: Response<Device>){
                        if(response.isSuccessful){
                            finish()
                        }
                        else Log.e("DeviceAddActivity", "API call unsuccessful." +
                                "Code: ${response.code()}")
                    }
                    override fun onFailure(call: Call<Device>, t: Throwable) {
                        Log.e("DeviceAddActivity", "API call failed", t)
                    }
                })

            }
        }
    }

    /**
     * Handles the click event of image buttons representing different device icons.
     *
     * @param buttonId The identifier of the clicked image button.
     * @return The resource identifier of the selected device icon.
     */
    private fun onImageButtonClicked(buttonId: Int): String {
        when (buttonId) {
            R.id.device_chair_btn -> {
                changeButtonColor(chairDevice)
                return R.drawable.ic_chair.toString()
            }
            R.id.device_bath_btn -> {
                changeButtonColor(bathDevice)
                return R.drawable.ic_bathtub.toString()
            }
            R.id.device_bed_btn -> {
                changeButtonColor(bedDevice)
                return R.drawable.ic_bed.toString()
            }
            R.id.device_tv_btn -> {
                changeButtonColor(tvDevice)
                return R.drawable.ic_tv.toString()
            }
            R.id.device_kitchen_btn -> {
                changeButtonColor(kitchenDevice)
                return R.drawable.ic_kitchen.toString()
            }
            R.id.device_garage_btn -> {
                changeButtonColor(garageDevice)
                return R.drawable.ic_garage.toString()
            }
            R.id.device_other_btn -> {
                changeButtonColor(otherDevice)
                return R.drawable.ic_other_house.toString()
            }
        }
        return ""
    }

    /**
     * Changes the background color of the selected image button.
     *
     * @param button The image button to change the background color.
     */
    private fun changeButtonColor(button: ImageButton) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSecondary))
    }
}
