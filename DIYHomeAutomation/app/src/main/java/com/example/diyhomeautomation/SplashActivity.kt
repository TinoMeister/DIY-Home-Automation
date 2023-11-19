package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.schedule
import android.util.Pair as UtilPair

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val topAnim = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation)

        val logo = findViewById<ImageView>(R.id.splash_logo_iv)
        val name = findViewById<TextView>(R.id.splash_name_tv)
        val desc = findViewById<TextView>(R.id.splash_desc_tv)

        logo.animation = topAnim
        name.animation = bottomAnim
        desc.animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
                UtilPair.create(logo, "logo_tn"),
                UtilPair.create(name, "name_tn"))

            startActivity(intent, options.toBundle())
        }, 1000)
    }
}