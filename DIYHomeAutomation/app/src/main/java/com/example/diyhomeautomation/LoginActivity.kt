package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.util.Pair
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.UserApi
import com.example.diyhomeautomation.models.AuthRequest
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.User
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val email: TextInputLayout by lazy {
        findViewById(R.id.login_email_til)
    }
    private val password: TextInputLayout by lazy {
        findViewById(R.id.login_password_til)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bounds = ChangeBounds().setDuration(1000)
        window.sharedElementEnterTransition = bounds

        val apiHelper = ApiHelper().getInstance().create(UserApi::class.java)

        val logo = findViewById<ImageView>(R.id.login_logo_iv)
        val name = findViewById<TextView>(R.id.login_welcome_tv)
        val desc = findViewById<TextView>(R.id.login_desc_tv)
        val btnLogin = findViewById<TextView>(R.id.login_login_btn)

        val btnSignUp = findViewById<Button>(R.id.login_register_btn)
        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity,
                Pair.create(logo, "logo_tn"),
                Pair.create(name, "name_tn"),
                Pair.create(desc, "desc_tn"),
                Pair.create(email, "email_tn"),
                Pair.create(password, "password_tn"),
                Pair.create(btnLogin, "button_tn"),
                Pair.create(btnSignUp, "signin_signup_tn"))

            startActivity(intent, options.toBundle())
        }

        btnLogin.setOnClickListener {
            val email = email.editText!!.text.toString()
            val password = password.editText!!.text.toString()

            // launching a new coroutine
            GlobalScope.launch {
                val result = apiHelper.getUser(AuthRequest(email, password))

                result.enqueue(object: Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.extras?.putString("token", response.body()?.token)
                        intent.extras?.putString("userID", response.body()?.userID)
                        startActivity(intent)
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,
                            "User/Password Wrong",
                            Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}
