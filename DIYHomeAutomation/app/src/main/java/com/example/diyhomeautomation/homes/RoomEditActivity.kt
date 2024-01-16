package com.example.diyhomeautomation.homes

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.AbsSavedState
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RoomApi
import com.example.diyhomeautomation.models.Room
import com.example.diyhomeautomation.sqlite.RoomDAO
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RoomEditActivity: AppCompatActivity() {

    private lateinit var roomDAO: RoomDAO
    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var roomId: String
    private lateinit var roomName: String
    private var selectedButtonId: String = ""

    private val name: TextInputLayout by lazy{
        findViewById(R.id.room_nameEdit_til)
    }
    private val editchair: ImageButton by lazy {
        findViewById(R.id.room_Editiconchair_btn)
    }
    private val editbath: ImageButton by lazy{
        findViewById(R.id.room_Editiconbath_btn)
    }
    private val editbed: ImageButton by lazy{
        findViewById(R.id.room_Editiconbed_btn)
    }
    private val edittv: ImageButton by lazy{
        findViewById(R.id.room_Editicontv_btn)
    }
    private val editkitchen: ImageButton by lazy{
        findViewById(R.id.room_Editiconkitchen_btn)
    }
    private val editgarage: ImageButton by lazy{
        findViewById(R.id.room_Editicongarage_btn)
    }
    private val editother: ImageButton by lazy{
        findViewById(R.id.room_Editiconother_btn)
    }
    private val editBtn: Button by lazy{
        findViewById(R.id.room_put_btn)
    }
    private val deleteBtn: Button by lazy {
        findViewById(R.id.room_delete_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_edit)

        val apiHelper = ApiHelper().getInstance().create(RoomApi::class.java)
        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return
        roomId = intent.getStringExtra("roomId") ?: return
        roomName = intent.getStringExtra("roomName") ?: return
        roomDAO = RoomDAO(this)

        // Set up click listeners for room icons
        editchair.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        editbath.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        editbed.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        edittv.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        editkitchen.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        editgarage.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        editother.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }

        editBtn.setOnClickListener {
            val roomNametb = name.editText!!.text.toString()
            Log.e("Test", roomName)
            val icon = selectedButtonId
            Log.e("Test", icon)
            val espId = 1
            Log.e("Test", roomId)
            Log.e("Test", userId)
            val roomIdSQLite = roomDAO.getSQLiteRoomIdByName(roomName)
            val roomUpdated = roomDAO.updateRoom(Room(roomIdSQLite, roomNametb,
                icon, userId, espId))

            if (roomUpdated) {
                GlobalScope.launch {
                    val result = apiHelper.putRoom(
                        "Bearer $token", roomId.toInt(),
                        Room(roomId.toInt(), roomNametb, icon, userId, espId)
                    )
                    result.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                finish()
                            } else {
                                Log.e(
                                    "RoomEditActivity", "API call unsuccessful." +
                                            " Code: ${response.code()}"
                                )
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("RoomEditActivity", "API call failed", t)
                        }
                    })
                }
            } else{
                Log.e("RoomEditActivity", "Failed to update room from SQLite")
            }
        }

        deleteBtn.setOnClickListener {
            val roomIdSQLite = roomDAO.getSQLiteRoomIdByName(roomName)
            val roomRemoved = roomDAO.removeRoom(roomIdSQLite)
            if (roomRemoved) {
                GlobalScope.launch {
                    val result = apiHelper.deleteRoom("Bearer $token", roomId.toInt())
                    result.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                finish()
                            } else Log.e(
                                "RoomEditActivity", "API call unsuccessful." +
                                        "Code: ${response.code()}"
                            )
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("RoomEditActivity", "API call failed", t)
                        }
                    })
                }
            } else {
                Log.e("RoomEditActivity", "Failed to remove room from SQLite")
            }
        }
    }

    /**
     * Handles the click event for room icons and updates the selected button ID.
     *
     * @param buttonId The resource ID of the clicked icon button.
     * @return The ID of the selected icon.
     */
    private fun onImageButtonClicked(buttonId: Int): String {
        when (buttonId) {
            R.id.room_Editiconchair_btn -> {
                changeButtonColor(editchair)
                return R.drawable.ic_chair.toString()
            }
            R.id.room_Editiconbath_btn -> {
                changeButtonColor(editbath)
                return R.drawable.ic_bathtub.toString()
            }
            R.id.room_Editiconbed_btn -> {
                changeButtonColor(editbed)
                return R.drawable.ic_bed.toString()
            }
            R.id.room_Editicontv_btn -> {
                changeButtonColor(edittv)
                return R.drawable.ic_tv.toString()
            }
            R.id.room_Editiconkitchen_btn -> {
                changeButtonColor(editkitchen)
                return R.drawable.ic_kitchen.toString()
            }
            R.id.room_Editicongarage_btn -> {
                changeButtonColor(editgarage)
                return R.drawable.ic_garage.toString()
            }
            R.id.room_Editiconother_btn -> {
                changeButtonColor(editother)
                return R.drawable.ic_other_house.toString()
            }
        }
        return ""
    }

    /**
     * Changes the background color of the clicked button to indicate selection.
     *
     * @param button The ImageButton clicked.
     */
    private fun changeButtonColor(button: ImageButton) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSecondary))
    }
}