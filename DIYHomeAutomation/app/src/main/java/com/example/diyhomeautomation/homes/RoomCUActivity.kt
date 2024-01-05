package com.example.diyhomeautomation.homes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RoomApi
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomCUActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var token: String
    private var selectedButtonId: String = ""
    private val name: TextInputLayout by lazy{
        findViewById(R.id.room_name_til)
    }

    private val chair: ImageButton by lazy{
        findViewById(R.id.room_iconchair_btn)
    }

    private val bath: ImageButton by lazy{
        findViewById(R.id.room_iconbath_btn)
    }

    private val bed: ImageButton by lazy{
        findViewById(R.id.room_iconbed_btn)
    }

    private val tv: ImageButton by lazy{
        findViewById(R.id.room_icontv_btn)
    }

    private val kitchen: ImageButton by lazy{
        findViewById(R.id.room_iconkitchen_btn)
    }

    private val garage: ImageButton by lazy{
        findViewById(R.id.room_icongarage_btn)
    }

    private val other: ImageButton by lazy{
        findViewById(R.id.room_iconother_btn)
    }

    private val addBtn: Button by lazy{
        findViewById(R.id.room_create_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_cu)

        val apiHelper = ApiHelper().getInstance().create(RoomApi::class.java)

        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return

        Log.d("RoomCUActivity", "Token: $token")
        Log.d("RoomCUActivity", "UserID: $userId")

        chair.setOnClickListener {
            selectedButtonId = onImageButtonClicked(it.id)
        }
        bath.setOnClickListener {
            selectedButtonId =  onImageButtonClicked(it.id)
        }
        bed.setOnClickListener{
            selectedButtonId =  onImageButtonClicked(it.id)
        }
        tv.setOnClickListener{
            selectedButtonId =  onImageButtonClicked(it.id)
        }
        kitchen.setOnClickListener {
            selectedButtonId =  onImageButtonClicked(it.id)
        }
        garage.setOnClickListener {
            selectedButtonId =  onImageButtonClicked(it.id)
        }
        other.setOnClickListener {
            selectedButtonId =  onImageButtonClicked(it.id)
        }

        addBtn.setOnClickListener{
            val name = "test" //name.editText!!.text.toString()
            val iconId = selectedButtonId
            val espId = 1

            GlobalScope.launch{
                val result = apiHelper.postRoom("Bearer $token", Room(null, name, iconId, userId, espId))
                result.enqueue(object: Callback<Room> {
                    override fun onResponse(call: Call<Room>, response: Response<Room>){
                        if(response.isSuccessful){
                            Intent(this@RoomCUActivity, HomeFragment::class.java)
                        }
                        else Log.e("RoomCUActivity", "API call unsuccessful. Code: ${response.code()}")
                    }
                    override fun onFailure(call: Call<Room>, t: Throwable) {
                        Log.e("RoomCUActivity", "API call failed", t)
                    }
                })
            }
        }
    }

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

    private fun changeButtonColor(button: ImageButton) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSecondary))
    }
}