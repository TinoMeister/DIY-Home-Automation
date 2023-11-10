package com.example.diyhomeautomation.restrictions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.customs.CustomRestrictionListView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestrictionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestrictionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var myView: View
    lateinit var lst: ListView

    val testValues = listOf<String>("AA","BB","CC","DD","EE","FF","GG","HH","II","JJ","KK","LL",
        "MM","NN","OO","PP","QQ","RR","SS","TT","UU","VV")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        this.activity?.findViewById<LinearLayout>(R.id.main_ln)?.visibility = View.VISIBLE
        this.activity?.findViewById<TextView>(R.id.main_mainText_tv)?.text = "Restrictions"
        this.activity?.findViewById<TextView>(R.id.main_subtext_tv)?.text = "Create your Restrictions"

        val action1 = this.activity?.findViewById<Button>(R.id.main_action1_btn)
        action1?.text = "All"
        action1?.setOnClickListener {
            val adapter = CustomRestrictionListView(
                myView.context,
                R.layout.custom_restriction_cardview,
                testValues.toMutableList()
            )
            lst.adapter = adapter
        }

        val action2 = this.activity?.findViewById<Button>(R.id.main_action2_btn)
        action2?.text = "Active"
        action2?.setOnClickListener {
            val adapter = CustomRestrictionListView(
                myView.context,
                R.layout.custom_restriction_cardview,
                testValues.toMutableList()
            )
            lst.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_restriction, container, false)

        lst = myView.findViewById<ListView>(R.id.restriction_fr_lst)
        val adapter = CustomRestrictionListView(
            myView.context,
            R.layout.custom_restriction_cardview,
            testValues.toMutableList()
        )
        lst.adapter = adapter

        // Inflate the layout for this fragment
        return myView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestrictionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestrictionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}