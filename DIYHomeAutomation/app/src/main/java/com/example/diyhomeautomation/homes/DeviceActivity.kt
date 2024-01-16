package com.example.diyhomeautomation.homes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.customs.CustomDevicesAdapter
import com.example.diyhomeautomation.models.Device
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * [DeviceActivity] is responsible for managing and displaying devices in a specific room.
 *
 * @property deviceList List of devices in the room.
 * @property token Authentication token for API requests.
 * @property roomId Identifier of the current room.
 * @property userId Identifier of the current user.
 */
class DeviceActivity : AppCompatActivity() {

    private lateinit var deviceList: MutableList<Device>
    private lateinit var token: String
    private lateinit var roomId: String
    private lateinit var userId: String

    private val lst: GridView by lazy {
        findViewById(R.id.device_gv)
    }
    private val btnEdit: Button by lazy{
        findViewById(R.id.cardDev_edit_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)

        roomId = intent.getStringExtra("roomId") ?: return
        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return

        Log.d("DeviceActivity", "Token: $token")
        Log.d("DeviceActivity", "UserID: $userId")
        Log.d("DeviceEditActivity", "RoomId: $roomId")

        // Use GlobalScope to launch a coroutine for handling API calls
        GlobalScope.launch {
            deviceList = mutableListOf()

            val result = apiHelper.getAllDevices("Bearer $token", userId)

            result.enqueue(object: Callback<List<Device>> {
                override fun onResponse(call: Call<List<Device>>, response: Response<List<Device>>) {
                    if (response.isSuccessful){
                        deviceList = (response.body() ?: emptyList()).toMutableList()
                        updateListView(R.layout.custom_devices_cardview)
                    }
                    else Log.e("DeviceActivity", "API call unsuccessful. Code: ${response.code()}")
                }

                override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                    Log.e("DeviceActivity", "API call failed", t)
                }
            })
        }

        btnEdit.setOnClickListener {
            updateListView(R.layout.custom_devices_cardview)
            val intent = Intent(this@DeviceActivity, DeviceEditActivity::class.java)
            intent.putExtra("roomId", roomId)
            intent.putExtra("token", token)
        }
    }

    /**
     * Updates the device list view based on the provided layout.
     *
     * @param layout The layout resource ID to be used for the adapter.
     */
    private fun updateListView(layout: Int){
        val adapter = if (layout == R.layout.custom_devices_cardview)
            CustomDevicesAdapter(this, layout, deviceList.toMutableList())
        else
            return

        lst.adapter = adapter

        if(layout != R.layout.custom_devices_cardview) return
    }
}
