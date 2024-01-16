package com.example.diyhomeautomation.homes

import android.os.Bundle
import android.util.Log
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


/**
 * Activity for creating or updating a room.
 */
class RoomCUActivity : AppCompatActivity() {

    private lateinit var roomDAO: RoomDAO
    private lateinit var userId: String
    private lateinit var token: String
    private var selectedButtonId: String = ""

    // UI components
    private val name: TextInputLayout by lazy {
        findViewById(R.id.room_name_til)
    }
    private val chair: ImageButton by lazy {
        findViewById(R.id.room_iconchair_btn)
    }
    private val bath: ImageButton by lazy {
        findViewById(R.id.room_iconbath_btn)
    }
    private val bed: ImageButton by lazy {
        findViewById(R.id.room_iconbed_btn)
    }
    private val tv: ImageButton by lazy {
        findViewById(R.id.room_icontv_btn)
    }
    private val kitchen: ImageButton by lazy {
        findViewById(R.id.room_iconkitchen_btn)
    }
    private val garage: ImageButton by lazy {
        findViewById(R.id.room_icongarage_btn)
    }
    private val other: ImageButton by lazy {
        findViewById(R.id.room_iconother_btn)
    }
    private val addBtn: Button by lazy {
        findViewById(R.id.room_create_btn)
    }

    /**
     * Called when the activity is first created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_cu)

        // Initialize SQLite database and obtain user information from the intent
        val apiHelper = ApiHelper().getInstance().create(RoomApi::class.java)
        roomDAO = RoomDAO(this)
        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return

        // Log user information for debugging
        Log.d("RoomCUActivity", "Token: $token")
        Log.d("RoomCUActivity", "UserID: $userId")

        // Set up click listeners for room icons
        chair.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bath.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bed.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        tv.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        kitchen.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        garage.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        other.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }

        // Set up click listener for the 'Create' button
        addBtn.setOnClickListener {
            val id = 0 //id.editText!!.text.toInt()
            val name = "test" //name.editText!!.text.toString()
            val iconId = selectedButtonId
            val espId = 1

            // Insert the room into SQLite database
            val roomInserted = roomDAO.insertRoom(Room(id, name, iconId, userId, espId))

            // If insertion into SQLite is successful, make an API call to insert on the server
            if (roomInserted) {
                GlobalScope.launch {
                    val result = apiHelper.postRoom("Bearer $token", Room(id, name, iconId, userId, espId))
                    result.enqueue(object : Callback<Room> {
                        override fun onResponse(call: Call<Room>, response: Response<Room>) {
                            if (response.isSuccessful) {
                                // API call successful, finish the activity
                                finish()
                            } else {
                                Log.e("RoomCUActivity", "API call unsuccessful." +
                                        " Code: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<Room>, t: Throwable) {
                            Log.e("RoomCUActivity", "API call failed", t)
                        }
                    })
                }
            } else {
                // Failed to insert into SQLite
                Log.e("RoomCUActivity", "Failed to insert room into SQLite")
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
            R.id.room_iconchair_btn -> {
                changeButtonColor(chair)
                return R.drawable.ic_chair.toString()
            }
            R.id.room_iconbath_btn -> {
                changeButtonColor(bath)
                return R.drawable.ic_bathtub.toString()
            }
            R.id.room_iconbed_btn -> {
                changeButtonColor(bed)
                return R.drawable.ic_bed.toString()
            }
            R.id.room_icontv_btn -> {
                changeButtonColor(tv)
                return R.drawable.ic_tv.toString()
            }
            R.id.room_iconkitchen_btn -> {
                changeButtonColor(kitchen)
                return R.drawable.ic_kitchen.toString()
            }
            R.id.room_icongarage_btn -> {
                changeButtonColor(garage)
                return R.drawable.ic_garage.toString()
            }
            R.id.room_iconother_btn -> {
                changeButtonColor(other)
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
