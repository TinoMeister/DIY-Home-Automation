package com.example.diyhomeautomation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.databinding.ActivityMainBinding
import com.example.diyhomeautomation.homes.HomeFragment
import com.example.diyhomeautomation.restrictions.RestrictionFragment
import com.example.diyhomeautomation.schedules.ScheduleFragment
import com.example.diyhomeautomation.settings.SettingFragment

/**
 * MainActivity serves as the main entry point for the application.
 * It manages the navigation between different fragments using BottomNavigationView.
 *
 * @constructor Creates an instance of MainActivity.
 */
class MainActivity : AppCompatActivity() {

    // View binding instance
    private lateinit var binding: ActivityMainBinding

    // Variables to store user credentials
    private lateinit var email: String
    private lateinit var password: String

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down, then this Bundle contains the data it most
     *     recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve user credentials and token from the intent
        val token = intent.getStringExtra("token")
        val userId = intent.getStringExtra("userID")
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()

        // Initialize the main fragment on startup
        replaceFragment(HomeFragment(), token, userId)

        // Set up the BottomNavigationView item selection listener
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

    /**
     * Replaces the current fragment with the specified fragment.
     *
     * @param fragment The fragment to be displayed.
     * @param token The authentication token.
     * @param userId The user ID.
     */
    private fun replaceFragment(fragment: Fragment, token: String?, userId: String?) {
        // Get fragment manager and start a fragment transaction
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Pass token and userId to the fragment using arguments
        val args = Bundle()
        args.putString("token", token)
        args.putString("userId", userId)
        fragment.arguments = args

        // Replace the current fragment with the new one
        fragmentTransaction.replace(R.id.main_fl, fragment)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}
