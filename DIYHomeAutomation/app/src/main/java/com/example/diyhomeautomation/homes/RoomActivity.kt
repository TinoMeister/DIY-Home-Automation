package com.example.diyhomeautomation.homes

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.customs.CustomRoomDevicesAdapter
import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.sqlite.DeviceDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for displaying and managing devices in a specific room.
 *
 * This activity allows users to view, add, and edit devices associated with a particular room.
 */
class RoomActivity : AppCompatActivity() {
    private lateinit var deviceList: MutableList<Device>
    private lateinit var token: String
    private lateinit var roomId: String
    private lateinit var roomName: String
    private lateinit var userId: String
    private lateinit var lst: RecyclerView
    private lateinit var deviceDAO: DeviceDAO
    private lateinit var sqliteDevices: MutableList<Device>

    private val addBtn: Button by lazy {
        findViewById(R.id.room_add_btn)
    }
    private val editBtn: Button by lazy{
        findViewById(R.id.edit_room_btn)
    }

    /**
     * Called when the activity is starting.
     *
     * Initializes UI elements, retrieves data from intents, and makes API calls to fetch
     * device data based on the network availability.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        // Initialize API helper and retrieve intent dat
        val apiHelper = ApiHelper().getInstance().create(DeviceApi::class.java)
        token = intent.getStringExtra("token") ?: return
        roomId = intent.getStringExtra("roomId") ?: return
        roomName = intent.getStringExtra("roomName") ?: return
        userId = intent.getStringExtra("userId") ?: return
        deviceDAO = DeviceDAO(this)
        sqliteDevices = deviceDAO.getAllDevicesByRoom(roomId.toInt())

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

        editBtn.setOnClickListener {
            val intent = Intent(this@RoomActivity, RoomEditActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            intent.putExtra("roomId", roomId)
            intent.putExtra("roomName", roomName)
            startActivity(intent)
        }



        // Use GlobalScope to launch a coroutine for handling API calls
        GlobalScope.launch {
            if (isNetworkAvailable()) {
                // Initialize deviceList and make API call to get device data
                deviceList = mutableListOf()
                val resultD = apiHelper.getDevice("Bearer $token", roomId.toInt())
                resultD.enqueue(object : Callback<List<Device>> {
                    override fun onResponse(
                        call: Call<List<Device>>,
                        response: Response<List<Device>>
                    ) {
                        if (response.isSuccessful) {
                            deviceList = (response.body() ?: emptyList()).toMutableList()

                            // Log SQLite cached devices for debugging
                            for(room in sqliteDevices){
                                Log.i("", "$room")
                            }

                            // Update the RecyclerView with device data
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
            } else {
                this@RoomActivity.runOnUiThread {
                    Toast.makeText(
                        this@RoomActivity,
                        "No network connection. Showing cached data...",
                        Toast.LENGTH_LONG
                    ).show()

                    deviceList = sqliteDevices
                    updateListView(R.layout.custom_room_devices_cardview)
                }
            }
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
            intent.putExtra("deviceName", clickedDevice.name)
            intent.putExtra("roomName", roomName)
            intent.putExtra("roomId", roomId)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }

    /**
     * Checks if the device has an active network connection.
     *
     * This function uses the [ConnectivityManager] to determine the network state.
     *
     * @return `true` if there is an active network connection, otherwise `false`.
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            this@RoomActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}