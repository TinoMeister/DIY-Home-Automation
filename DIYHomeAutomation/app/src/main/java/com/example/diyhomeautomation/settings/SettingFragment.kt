package com.example.diyhomeautomation.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.diyhomeautomation.MainActivity
import com.example.diyhomeautomation.R

class SettingFragment : Fragment() {
    private lateinit var myView: View
    private lateinit var myActivity: FragmentActivity

    private val layoutTop: LinearLayout by lazy {
        myActivity.findViewById(R.id.main_ln)
         }

    /*
    private val notifbtn: LinearLayout by lazy {
        myView.findViewById(R.id.Notification_LL)
    }
    private val logoutBtn: LinearLayout by lazy {
        myView.findViewById(R.id.LogOut_LL)
    }
    private val uzsettingbtn: Button by lazy {
        myView.findViewById(R.id.setting_btn)
    }

    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myActivity = this.requireActivity()

        layoutTop.visibility=View.INVISIBLE

        /*
        notifbtn.setOnClickListener{
            val intent = Intent(this.activity, NotificationFragment::class.java)
          startActivity(intent)
       }

        logoutBtn.setOnClickListener{
            val intent = Intent(this.activity, MainActivity::class.java)
            startActivity(intent)
        }
         */

        // para o usar NOT YET IMPLEMENTADO
       // uzsettingbtn.setOnClickListener {
       //     val intent = Intent(this.activity, UserActivity::class.java)
        //}

        this.activity?.findViewById<LinearLayout>(R.id.main_ln)?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myView = inflater.inflate(R.layout.fragment_setting, container, false)
        // Inflate the layout for this fragment
        return myView
    }
}
