package com.example.diyhomeautomation.homes

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.DeviceApi
import com.example.diyhomeautomation.api.RoomApi
import com.example.diyhomeautomation.customs.CustomHomeDevicesAdapter
import com.example.diyhomeautomation.customs.CustomHomeRoomsAdapter
import com.example.diyhomeautomation.models.Device
import com.example.diyhomeautomation.models.Room
import com.example.diyhomeautomation.sqlite.DeviceDAO
import com.example.diyhomeautomation.sqlite.RoomDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A [Fragment] representing the home screen of the application.
 *
 * This fragment displays a GridView of rooms or devices based on user interaction and provides
 * actions and navigation options.
 */
class HomeFragment : Fragment() {

    private lateinit var myView: View
    private lateinit var myActivity: FragmentActivity
    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var roomsList: List<Room>
    private lateinit var deviceList: List<Device>
    private lateinit var existingRooms: List<Room>
    private lateinit var existingDevices: List<Device>
    private lateinit var roomDAO: RoomDAO
    private lateinit var deviceDAO: DeviceDAO

    private val lst: GridView by lazy {
        myView.findViewById(R.id.home_fr_gv)
    }
    private val layoutTop: LinearLayout by lazy {
        myActivity.findViewById(R.id.main_ln)
    }
    private val mainText: TextView by lazy {
        myActivity.findViewById(R.id.main_mainText_tv)
    }
    private val subText: TextView by lazy {
        myActivity.findViewById(R.id.main_subtext_tv)
    }
    private val addBtn: ImageButton by lazy {
        myActivity.findViewById(R.id.main_add_btn)
    }
    private val notificationBtn: ImageButton by lazy {
        myActivity.findViewById(R.id.main_notifications_btn)
    }
    private val action1: Button by lazy {
        myActivity.findViewById(R.id.main_action1_btn)
    }
    private val action2: Button by lazy {
        myActivity.findViewById(R.id.main_action2_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiHelper = ApiHelper().getInstance().create(RoomApi::class.java)
        val apiHelperDevice = ApiHelper().getInstance().create(DeviceApi::class.java)
        myActivity = this.requireActivity()
        roomDAO = RoomDAO(myActivity)
        deviceDAO = DeviceDAO(myActivity)

        userId = arguments?.getString("userId") ?: return
        token = arguments?.getString("token") ?: return
        existingRooms = roomDAO.getAllRooms()
        existingDevices = deviceDAO.getAllDevices()

        Log.d("HomeFragment", "Token: $token")
        Log.d("HomeFragment", "UserID: $userId")

        layoutTop.visibility = View.VISIBLE
        mainText.text = getString(R.string.home_fg_mainText)
        subText.text = getString(R.string.home_fg_subText)

        addBtn.setOnClickListener {
            val intent = Intent(this.activity, RoomCUActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        notificationBtn.visibility = View.VISIBLE
        notificationBtn.setOnClickListener {
        }

        action1.text = getString(R.string.all_room)
        action1.setOnClickListener {
            if (isNetworkAvailable()) {
                updateListView(R.layout.custom_home_rooms_cardview)
            } else{
                myActivity.runOnUiThread {
                    Toast.makeText(
                        myActivity,
                        "No network connection. Showing cached data...",
                        Toast.LENGTH_LONG
                    ).show()

                    roomsList = roomDAO.getAllRooms()
                }
                updateListView(R.layout.custom_home_rooms_cardview)
            }
        }

        action2.text = getString(R.string.active_device)
        action2.setOnClickListener {
            if (isNetworkAvailable()) {
                updateListView(R.layout.custom_home_devices_cardview)
            } else {
                myActivity.runOnUiThread {
                    Toast.makeText(
                        myActivity,
                        "No network connection. Showing cached data...",
                        Toast.LENGTH_LONG
                    ).show()

                    deviceList = deviceDAO.getAllDevices()
                }
                updateListView(R.layout.custom_home_devices_cardview)
            }
        }
        // launching a new coroutine
        GlobalScope.launch {
            if (isNetworkAvailable()) {
                val resultD = apiHelperDevice.getDevicesEnabled("Bearer $token", userId)
                resultD.enqueue(object : Callback<List<Device>> {
                    override fun onResponse(
                        call: Call<List<Device>>,
                        response: Response<List<Device>>
                    ) {
                        if (response.isSuccessful) {
                            deviceList = response.body() ?: emptyList()
                            for(device in existingDevices){
                                Log.i("", "$device")
                            }
                            for (device in deviceList) {
                                if (existingDevices.contains(device)) {
                                    return
                                } else{
                                    deviceDAO.insertDevice(device)
                                }
                            }
                            updateListView(R.layout.custom_home_devices_cardview)
                        } else Log.e(
                            "HomeFragment-Active Device", "API call unsuccessful. " +
                                    "Code: ${response.code()}"
                        )
                    }

                    override fun onFailure(call: Call<List<Device>>, t: Throwable) {
                        Log.e("HomeFragment-Active Device", "API call failed", t)
                    }
                })

                val result = apiHelper.getAllRooms("Bearer $token", userId)
                result.enqueue(object : Callback<List<Room>> {
                    override fun onResponse(
                        call: Call<List<Room>>,
                        response: Response<List<Room>>
                    ) {
                        if (response.isSuccessful) {
                            roomsList = response.body() ?: emptyList()

                            for(room in existingRooms){
                                Log.i("", "$room")
                            }
                            for (room in roomsList) {
                                if (existingRooms.contains(room)) {
                                    return
                                }else{
                                    roomDAO.insertRoom(room)
                                }
                            }
                            updateListView(R.layout.custom_home_rooms_cardview)

                        } else Log.e(
                            "HomeFragment", "API call unsuccessful. " +
                                    "Code: ${response.code()}"
                        )
                    }

                    override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                        Log.e("HomeFragment", "API call failed", t)
                    }
                })
            } else {
                // Handle the case when there is no network connectivity
                myActivity.runOnUiThread {
                    Toast.makeText(
                        myActivity,
                        "No network connection. Showing cached data...",
                        Toast.LENGTH_LONG
                    ).show()

                    roomsList = existingRooms
                    deviceList = existingDevices
                    updateListView(R.layout.custom_home_rooms_cardview)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        if (isNetworkAvailable()) {
            myView = inflater.inflate(R.layout.fragment_home, container, false)
            updateListView(R.layout.custom_home_rooms_cardview)

        } else{
            myView = inflater.inflate(R.layout.fragment_home, container, false)
            roomsList = roomDAO.getAllRooms()
            deviceList = deviceDAO.getAllDevices()
        }
        return myView
    }


    /**
     * Updates the [ListView] with data based on the specified layout.
     *
     * If the data lists (roomsList and deviceList) are not initialized, it initializes them by
     * retrieving data from the corresponding DAOs (Data Access Objects).
     *
     * @param layout The layout resource ID to be used for creating the adapter.
     *               Should be either [R.layout.custom_home_rooms_cardview] or
     *               [R.layout.custom_home_devices_cardview].
     */
    private fun updateListView(layout: Int) {
        // Initialize data lists if not already initialized
        if (!::roomsList.isInitialized || !::deviceList.isInitialized) {
            roomsList = roomDAO.getAllRooms()
            deviceList = deviceDAO.getAllDevices()
        }

        // Create an adapter based on the specified layout and data list
        val adapter = if (layout == R.layout.custom_home_rooms_cardview)
            CustomHomeRoomsAdapter(myView.context, layout, roomsList.toMutableList())
        else
            CustomHomeDevicesAdapter(myView.context, layout, deviceList.toMutableList())

        // Set the adapter to the ListView
        lst.adapter = adapter

        // Set item click listener for rooms, navigating to RoomActivity with relevant data
        if (layout != R.layout.custom_home_rooms_cardview && layout != R.layout.custom_home_devices_cardview) return
        lst.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val currentRoom = roomsList[position]
            val intent = Intent(this.activity, RoomActivity::class.java)
            intent.putExtra("roomId", currentRoom.id.toString())
            intent.putExtra("roomName", currentRoom.name)
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
            myActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}