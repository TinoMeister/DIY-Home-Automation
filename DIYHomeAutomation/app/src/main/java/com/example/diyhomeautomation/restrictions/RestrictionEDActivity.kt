package com.example.diyhomeautomation.restrictions

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RestrictionApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for editing or deleting a restriction.
 */
class RestrictionEDActivity : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var userId: String
    private lateinit var restrictionId: String

    // UI element using lazy initialization
    private val delete: Button by lazy {
        findViewById(R.id.delete_Restrictionbtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edactivity)

        val apiHelper = ApiHelper().getInstance().create(RestrictionApi::class.java)

        // Retrieve data from the intent
        userId = intent.getStringExtra("userId") ?: return
        token = intent.getStringExtra("token") ?: return
        restrictionId = intent.getStringExtra("restrictionId") ?: return

        // Set up click listener for the "Delete" button
        delete.setOnClickListener {
            GlobalScope.launch {
                val result = apiHelper.deleteRestriction("Bearer $token", restrictionId.toInt())
                result.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // API call successful, finish the activity
                            finish()
                        } else {
                            Log.e(
                                "RestrictionEDActivity",
                                "API call unsuccessful. Code: ${response.code()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("RestrictionEDActivity", "API call failed", t)
                    }
                })
            }
        }
    }
}
