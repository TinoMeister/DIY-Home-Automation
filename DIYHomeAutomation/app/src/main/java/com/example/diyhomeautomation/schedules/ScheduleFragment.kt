package com.example.diyhomeautomation.schedules

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.customs.CustomScheduleAdapter

class ScheduleFragment : Fragment() {
    private lateinit var myView: View
    private lateinit var myActivity: FragmentActivity
    private val lst: ListView by lazy {
        myView.findViewById(R.id.schedule_fr_lst)
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
        mainText.text = getString(R.string.schedule)
        subText.text = getString(R.string.create_your_routine_schedule)

        addBtn.setOnClickListener {
            val intent = Intent(myActivity, ScheduleCUActivity::class.java)
            startActivity(intent)
        }
        notificationBtn.visibility = View.GONE

        action1.text = getString(R.string.all_day)
        action1.setOnClickListener {
            updateListView(R.layout.custom_schedule_cardview)
        }

        action2.text = getString(R.string.today)
        action2.setOnClickListener {
            updateListView(R.layout.custom_schedule_cardview)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.fragment_schedule, container, false)

        updateListView(R.layout.custom_schedule_cardview)

        // Inflate the layout for this fragment
        return myView
    }

    private fun updateListView(layout: Int) {
        val adapter = CustomScheduleAdapter(myView.context, layout, testValues.toMutableList())
        lst.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(myActivity, ScheduleCUActivity::class.java)
            startActivity(intent)
        }
    }
}