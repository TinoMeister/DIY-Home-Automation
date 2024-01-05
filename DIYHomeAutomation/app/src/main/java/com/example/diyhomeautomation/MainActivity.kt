package com.example.diyhomeautomation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.diyhomeautomation.homes.HomeFragment
import com.example.diyhomeautomation.restrictions.RestrictionFragment
import com.example.diyhomeautomation.schedules.ScheduleFragment
import com.example.diyhomeautomation.settings.SettingFragment
import com.example.diyhomeautomation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userID")

        replaceFragment(HomeFragment(), token, userId)

        binding.mainNavBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment(), token, userId)
                R.id.nav_schedule -> replaceFragment(ScheduleFragment(), token, userId)
                R.id.nav_restriction -> replaceFragment(RestrictionFragment(), token, userId)
                R.id.nav_setting -> replaceFragment(SettingFragment(), token, userId)
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment, token: String?, userId: String?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val args = Bundle()
        args.putString("token", token)
        args.putString("userId", userId)
        fragment.arguments = args

        fragmentTransaction.replace(R.id.main_fl, fragment)
        fragmentTransaction.commit()
    }
}