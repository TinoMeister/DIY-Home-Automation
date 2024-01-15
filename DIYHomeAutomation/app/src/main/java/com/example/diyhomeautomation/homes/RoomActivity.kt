package com.example.diyhomeautomation.homes

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

class RoomActivity : AppCompatActivity() {
    private lateinit var deviceList: MutableList<Device>
    private lateinit var token: String
    private lateinit var roomId: String
    private lateinit var roomName: String
    private lateinit var userId: String
    private lateinit var lst: RecyclerView

    private val addBtn: Button by lazy {
        findViewById(R.id.room_add_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)
        token = intent.getStringExtra("token") ?: return
        roomId = intent.getStringExtra("roomId") ?: return
        roomName = intent.getStringExtra("roomName") ?: return
        userId = intent.getStringExtra("userId") ?: return

        Log.d("RoomActivity", "Token: $token")
        Log.d("RoomActivity", "UserID: $userId")
        Log.d("RoomActivity", "roomId: $roomId")
        Log.d("RoomActivity", "roomName: $roomName")

        addBtn.setOnClickListener {
            val intent = Intent(this@RoomActivity, DeviceAddActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            intent.putExtra("roomName", roomName)
            intent.putExtra("roomId", roomId)
            startActivity(intent)
        }

        GlobalScope.launch {
            deviceList = mutableListOf()
            val resultD = apiHelper.getDevice("Bearer $token", roomId.toInt())
            resultD.enqueue(object : Callback<List<Device>> {
                override fun onResponse(
                    call: Call<List<Device>>,
                    response: Response<List<Device>>
                ) {
                    if (response.isSuccessful) {
                        deviceList = (response.body() ?: emptyList()).toMutableList()
                        updateListView(R.layout.custom_room_devices_cardview)
                    } else Log.e(
                        "RoomActivity", "API call unsuccessful. " +
                                "Code: ${response.code()}"
                    )
                }

                override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                    Log.e("RoomActivity", "API call failed", t)
                }
            })
        }

    }

    /**
     * Updates the RecyclerView in the RoomActivity with the specified layout.
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
        lst = findViewById(R.id.room_rv)

        // Configure the RecyclerView
        lst.setHasFixedSize(true)
        lst.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        lst.adapter = adapter

        // If the layout is not recognized, return without further actions
        if (layout != R.layout.custom_room_devices_cardview) return

        // Set up click listener for items in the RecyclerView
        adapter.onItemClick = { clickedDevice ->
            // Launch DeviceEditActivity when a device item is clicked
            val intent = Intent(this@RoomActivity, DeviceEditActivity::class.java)
            intent.putExtra("deviceId", clickedDevice.id.toString())
            intent.putExtra("roomName", roomName)
            intent.putExtra("roomId", roomId)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}