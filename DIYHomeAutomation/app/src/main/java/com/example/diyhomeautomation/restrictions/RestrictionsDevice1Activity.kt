package com.example.diyhomeautomation.restrictions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.customs.CustomRoomDevicesAdapter
import com.example.diyhomeautomation.models.Device
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for selecting devices in the first step of creating restrictions.
 */
class RestrictionsDevice1Activity : AppCompatActivity() {

    private lateinit var deviceList: MutableList<Device>
    private lateinit var token: String
    private lateinit var deviceId: String
    private lateinit var deviceState: String
    private lateinit var deviceValue : String
    private lateinit var userId: String
    private lateinit var lst: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device1restrictions)

        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)
        token = intent.getStringExtra("token") ?: return
        deviceId = intent.getStringExtra("deviceId") ?: return
        deviceState = intent.getStringExtra("deviceName") ?: return
        deviceValue = intent.getStringExtra("deviceValue") ?:return
        userId = intent.getStringExtra("userId") ?: return
        deviceList = (intent.getStringExtra("deviceList") ?: return) as MutableList<Device>

        Log.d("RestrictionsDevice1Activity", "Token: $token")
        Log.d("RestrictionsDevice1Activity", "UserID: $userId")

        // Fetch the list of devices from the API
        GlobalScope.launch {
            deviceList = mutableListOf()
            val resultD = apiHelper.getDevice("Bearer $token", userId.toInt())
            resultD.enqueue(object : Callback<List<Device>> {
                override fun onResponse(
                    call: Call<List<Device>>,
                    response: Response<List<Device>>
                ) {
                    if (response.isSuccessful) {
                        deviceList = (response.body() ?: emptyList()).toMutableList()
                        updateListView(R.layout.custom_room_devices_cardview)
                    } else Log.e(
                        "RestrictionsDevice1Activity", "API call unsuccessful. " +
                                "Code: ${response.code()}"
                    )
                }

                override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                    Log.e("RestrictionsDevice1Activity", "API call failed", t)
                }
            })
        }
    }

    /**
     * Updates the RecyclerView in the RestrictionsDevice1Activity with the specified layout.
     *
     * @param layout The layout resource ID to determine the type of view to display.
     */
    private fun updateListView(layout: Int) {
        // Create an adapter based on the specified layout
        val adapter = if (layout == R.layout.custom_room_devices_cardview) {
            CustomRoomDevicesAdapter(deviceList!!.toMutableList())
        } else {
            // If the layout is not recognized, return without setting up the adapter
            return
        }

        // Find the RecyclerView in the layout
        lst = findViewById(R.id.restriction_rv)

        // Configure the RecyclerView
        lst.setHasFixedSize(true)
        lst.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        lst.adapter = adapter

        // If the layout is not recognized, return without further actions
        if (layout != R.layout.custom_room_devices_cardview) return

        // Set up click listener for items in the RecyclerView
        adapter.onItemClick = { clickedDevice ->
            // Launch DeviceEditActivity when a device item is clicked
            val intent = Intent(this@RestrictionsDevice1Activity, RestrictionCUActivity::class.java)
            intent.putExtra("deviceId", clickedDevice.id.toString())
            intent.putExtra("deviceId", deviceId)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}
