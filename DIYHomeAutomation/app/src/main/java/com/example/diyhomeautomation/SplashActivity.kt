package com.example.diyhomeautomation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val topAnim = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation)

        findViewById<ImageView>(R.id.splash_logo_iv).animation = topAnim
        findViewById<TextView>(R.id.splash_name_tv).animation = bottomAnim
        findViewById<TextView>(R.id.splash_desc_tv).animation = bottomAnim

        Timer().schedule(2500) {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}