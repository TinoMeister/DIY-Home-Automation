package com.example.diyhomeautomation

import android.os.Bundle
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
        replaceFragment(HomeFragment())

        binding.mainNavBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_schedule -> replaceFragment(ScheduleFragment())
                R.id.nav_restriction -> replaceFragment(RestrictionFragment())
                R.id.nav_setting -> replaceFragment(SettingFragment())
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fl, fragment)
        fragmentTransaction.commit()
    }
}