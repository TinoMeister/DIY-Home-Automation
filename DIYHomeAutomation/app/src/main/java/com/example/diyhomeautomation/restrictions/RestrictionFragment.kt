package com.example.diyhomeautomation.restrictions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.RestrictionApi
import com.example.diyhomeautomation.customs.CustomRestrictionAdapter
import com.example.diyhomeautomation.homes.DeviceEditActivity
import com.example.diyhomeautomation.models.Restriction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Fragment for managing and displaying restrictions.
 */
class RestrictionFragment : Fragment() {

    private lateinit var myView: View
    private lateinit var userId: String
    private lateinit var token: String
    private lateinit var restrictionList: List<Restriction>
    private lateinit var activeRestriction: List<Restriction>
    private lateinit var myActivity: FragmentActivity

    // UI elements using lazy initialization
    private val lst: ListView by lazy {
        myView.findViewById(R.id.restriction_fr_lst)
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

        val apiHelper = ApiHelper().getInstance().create(RestrictionApi::class.java)
        myActivity = this.requireActivity()

        // Retrieve data from arguments
        userId = arguments?.getString("userId") ?: return
        token = arguments?.getString("token") ?: return

        // Set up UI and visibility
        layoutTop.visibility = View.VISIBLE
        mainText.text = getString(R.string.restrictions)
        subText.text = getString(R.string.create_your_restrictions)

        restrictionList = emptyList()
        activeRestriction = emptyList()

        // Set up click listener for the "Add" button
        addBtn.setOnClickListener {
            val intent = Intent(this.activity, RestrictionCUActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("token", token)
            startActivity(intent)
        }

        notificationBtn.visibility = View.GONE

        // Set up click listeners for the action buttons
        action1.text = "All"
        action1.setOnClickListener {
            updateListView(R.layout.custom_restriction_cardview)
        }

        action2.text = "Active"
        action2.setOnClickListener {
            // You may want to update the layout ID for the active restrictions
            updateListView(R.layout.custom_restriction_cardview)
        }

        // Fetch data from the API using coroutines
        GlobalScope.launch {
            val result = apiHelper.getAllRestrictions("Bearer $token", userId)
            result.enqueue(object : Callback<List<Restriction>> {
                override fun onResponse(
                    call: Call<List<Restriction>>,
                    response: Response<List<Restriction>>
                ) {
                    if (response.isSuccessful) {
                        restrictionList = response.body() ?: emptyList()
                        // You may want to update the layout ID for the active restrictions
                        updateListView(R.layout.custom_restriction_cardview)
                    } else Log.e(
                        "RestrictionFragment", "API call unsuccessful. " +
                                "Code: ${response.code()}"
                    )
                }

                override fun onFailure(call: Call<List<Restriction>>, t: Throwable) {
                    Log.e("RestrictionFragment", "API call failed", t)
                }
            })

            val resultB = apiHelper.getAllRestrictions("Bearer $token", userId)
            resultB.enqueue(object : Callback<List<Restriction>> {
                override fun onResponse(
                    call: Call<List<Restriction>>,
                    response: Response<List<Restriction>>
                ) {
                    if (response.isSuccessful) {
                        activeRestriction = response.body() ?: emptyList()
                        // You may want to update the layout ID for the active restrictions
                        updateListView(R.layout.custom_restriction_cardview)
                    } else Log.e(
                        "RestrictionFragment", "API call unsuccessful. " +
                                "Code: ${response.code()}"
                    )
                }

                override fun onFailure(call: Call<List<Restriction>>, t: Throwable) {
                    Log.e("RestrictionFragment", "API call failed", t)
                }
            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.fragment_restriction, container, false)
        updateListView(R.layout.custom_restriction_cardview)
        return myView
    }

    private fun updateListView(layout: Int) {
        val adapter = if (layout == R.layout.custom_restriction_cardview)
            CustomRestrictionAdapter(myView.context, layout, restrictionList.toMutableList())
        else
            CustomRestrictionAdapter(myView.context, layout, activeRestriction.toMutableList())

        lst.adapter = adapter

        if (layout != R.layout.custom_home_rooms_cardview && layout !=
            R.layout.custom_home_devices_cardview
        ) return

        // Set up click listener for the list items
        lst.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val currentRestriction = restrictionList[position]
            val intent = Intent(myActivity, RestrictionCUActivity::class.java)
            intent.putExtra("restrictionId", currentRestriction.id.toString())
            intent.putExtra("restrictionName", currentRestriction.name)
            intent.putExtra("token", token)
            intent.putExtra("userId", userId)
            startActivity(intent)

            val intent1 = Intent(myActivity, RestrictionEDActivity::class.java)
            intent1.putExtra("restrictionId", currentRestriction.id)
            intent1.putExtra("token", token)
            intent1.putExtra("userId", userId)
        }
    }
}
