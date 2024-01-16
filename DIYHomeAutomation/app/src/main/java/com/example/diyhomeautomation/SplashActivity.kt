package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Pair as UtilPair

/**
 * SplashActivity serves as the splash screen for the application.
 *
 * This activity displays a logo and app name with animations before transitioning to the LoginActivity.
 *
 * @constructor Creates an instance of SplashActivity.
 */
class SplashActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down, then this Bundle contains the data it most
     *     recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Load animations for the logo and text views
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation)

        // Find views by ID
        val logo = findViewById<ImageView>(R.id.splash_logo_iv)
        val name = findViewById<TextView>(R.id.splash_name_tv)
        val desc = findViewById<TextView>(R.id.splash_desc_tv)

        // Apply animations to views
        logo.animation = topAnim
        name.animation = bottomAnim
        desc.animation = bottomAnim

        // Use Handler to delay the transition to LoginActivity by 1000 milliseconds (1 second)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)

            // Define shared element transitions for the animation
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@SplashActivity,
                UtilPair.create(logo, "logo_tn"),
                UtilPair.create(name, "name_tn")
            )

            // Start the LoginActivity with the defined options
            startActivity(intent, options.toBundle())
        }, 1000)
    }
}