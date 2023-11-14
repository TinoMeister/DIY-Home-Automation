package com.example.diyhomeautomation.restrictions

import android.os.Bundle
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
import com.example.diyhomeautomation.customs.CustomRestrictionAdapter

class RestrictionFragment : Fragment() {
    private lateinit var myView: View
    private lateinit var myActivity: FragmentActivity
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


    val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
        "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myActivity = this.requireActivity()

        layoutTop.visibility = View.VISIBLE
        mainText.text = getString(R.string.restrictions)
        subText.text = getString(R.string.create_your_restrictions)

        addBtn.setOnClickListener {
            //val intent = Intent(this.activity, RoomCUActivity::class.java)
            //startActivity(intent)
        }
        notificationBtn.setOnClickListener {
        }

        action1.text = "All"
        action1.setOnClickListener {
            updateListView(R.layout.custom_restriction_cardview)
        }

        action2.text = "Active"
        action2.setOnClickListener {
            updateListView(R.layout.custom_restriction_cardview)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.fragment_restriction, container, false)

        updateListView(R.layout.custom_restriction_cardview)

        // Inflate the layout for this fragment
        return myView
    }

    private fun updateListView(layout: Int) {
        val adapter = CustomRestrictionAdapter(myView.context, layout, testValues.toMutableList())
        lst.adapter = adapter

        lst.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            //val str = (adapterView.adapter as CustomHomeRoomsGridView).getItem(position)
        }
    }
}