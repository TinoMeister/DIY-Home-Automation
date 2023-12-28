package com.example.diyhomeautomation.homes

import android.content.Intent
import android.os.Bundle
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
import com.example.diyhomeautomation.MainActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RoomApi
import com.example.diyhomeautomation.api.UserApi
import com.example.diyhomeautomation.customs.CustomHomeDevicesAdapter
import com.example.diyhomeautomation.customs.CustomHomeRoomsAdapter
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var myView: View
    private lateinit var myActivity: FragmentActivity
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

    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var roomsList: List<Room>

    val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
        "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiHelper = ApiHelper().getInstance().create(RoomApi::class.java)

        myActivity = this.requireActivity()

        userId = myActivity.intent.extras?.getString("userId")!!
        token = myActivity.intent.extras?.getString("token")!!

        layoutTop.visibility = View.VISIBLE
        mainText.text = getString(R.string.home_fg_mainText)
        subText.text = getString(R.string.home_fg_subText)

        addBtn.setOnClickListener {
            val intent = Intent(this.activity, RoomCUActivity::class.java)
            startActivity(intent)
        }

        notificationBtn.visibility = View.VISIBLE
        notificationBtn.setOnClickListener {
        }

        action1.text = getString(R.string.all_room)
        action1.setOnClickListener {
            updateListView(R.layout.custom_home_rooms_cardview)
        }

        action2.text = getString(R.string.active_device)
        action2.setOnClickListener {
            updateListView(R.layout.custom_home_devices_cardview)
        }

        // launching a new coroutine
        GlobalScope.launch {
            val result = apiHelper.getAllRooms(token, userId)

            result.enqueue(object: Callback<List<Room>> {
                override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                    roomsList = response.body()!!
                    updateListView(R.layout.custom_home_rooms_cardview)
                }

                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Toast.makeText(myActivity,
                        "Something went wrong",
                        Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.fragment_home, container, false)

        updateListView(R.layout.custom_home_rooms_cardview)

        // Inflate the layout for this fragment
        return myView
    }

    private fun updateListView(layout: Int) {
        val adapter = if (layout == R.layout.custom_home_rooms_cardview)
            CustomHomeRoomsAdapter(myView.context, layout, roomsList.toMutableList())
        else
            CustomHomeDevicesAdapter(myView.context, layout, roomsList.toMutableList())

        lst.adapter = adapter

        if (layout != R.layout.custom_home_rooms_cardview) return

        lst.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            //val str = (adapterView.adapter as CustomHomeRoomsGridView).getItem(position)
            val intent = Intent(this.activity, RoomActivity::class.java)
            startActivity(intent)
        }
    }
}