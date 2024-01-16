package com.example.diyhomeautomation.restrictions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RestrictionApi
import com.example.diyhomeautomation.homes.RoomActivity
import com.example.diyhomeautomation.models.Restriction
import com.example.diyhomeautomation.models.Room
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for creating or updating restrictions.
 */
class RestrictionCUActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var token: String
    private var isRestrictionOn: Boolean = false

    // UI elements using lazy initialization
    private val name: TextInputLayout by lazy {
        findViewById(R.id.restriction_Name)
    }

    private val addRestriction: Button by lazy {
        findViewById(R.id.add_restrictionbtn)
    }

    private val ifValueLayout: TextInputLayout by lazy {
        findViewById(R.id.if_Value_Restriction)
    }

    private val ifValueAutoComplete: AutoCompleteTextView by lazy {
        ifValueLayout.editText as AutoCompleteTextView
    }

    private val resValue: TextInputLayout by lazy {
        findViewById(R.id.restriction_Value)
    }

    private val targetDevice: Button by lazy {
        findViewById(R.id.restriction_Device_Target)
    }

    private val state: ToggleButton by lazy {
        findViewById(R.id.statebtn)
    }

    private val savebtn: Button by lazy {
        findViewById(R.id.save_Restrictionbtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restriction_cu)

        // Example data for suggestions
        val conditions = arrayOf("EQUAL", "LOWER", "HIGHER", "LOWER_EQUAL", "HIGHER_EQUAL")

        // Create an ArrayAdapter and set it to the AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, conditions)
        ifValueAutoComplete.setAdapter(adapter)

        val apiHelper = ApiHelper().getInstance().create(RestrictionApi::class.java)
        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return

        Log.d("RestrictionCUActivity", "Token: $token")
        Log.d("RestrictionCUActivity", "UserID: $userId")

        // Set up click listener for the "Add Restriction" button
        addRestriction.setOnClickListener {
            val intent = Intent(this, RestrictionsDevice1Activity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        // Set up click listener for the "Save" button
        savebtn.setOnClickListener {
            val id = 0 // id.editText!!.text.toInt()
            val name = "test" // name.editText!!.text.toString()
            val ifValue = ifValueAutoComplete.text.toString()

            isRestrictionOn = state.isChecked
            state.setOnCheckedChangeListener { buttonView, isChecked ->
                // Update the state when ToggleButton state changes
                isRestrictionOn = isChecked

                /*  GlobalScope.launch {
                      val result = apiHelper.postRestriction(
                          "Bearer $token",
                          Restriction(
                              null,
                              name,
                              isRestrictionOn,
                              ifValue,
                              primaryDeviceId,
                              primaryDeviceState,
                              primaryDeviceValue,
                              secondaryDeviceId,
                              secondaryDeviceState,
                              secondaryDeviceValue
                          )
                      )
                      result.enqueue(object : Callback<Restriction> {
                          override fun onResponse(
                              call: Call<Restriction>,
                              response: Response<Restriction>
                          ) {
                              if (response.isSuccessful) {
                                  // API call successful, finish the activity
                                  finish()
                              } else {
                                  Log.e(
                                      "RestrictionCUActivity",
                                      "API call unsuccessful. Code: ${response.code()}"
                                  )
                              }
                          }

                          override fun onFailure(call: Call<Restriction>, t: Throwable) {
                              Log.e("RestrictionCUActivity", "API call failed", t)
                          }
                      })
                  }*/
            }
        }
    }
}
