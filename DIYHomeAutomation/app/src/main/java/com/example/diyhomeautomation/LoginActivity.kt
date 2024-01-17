package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.util.Log
import android.util.Pair
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.R
import com.example.diyhomeautomation.RegisterActivity
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.UserApi
import com.example.diyhomeautomation.models.AuthRequest
import com.example.diyhomeautomation.models.AuthResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * LoginActivity handles user authentication and serves as the entry point for user login.
 *
 * This activity includes a shared element transition for a smooth animation and uses Retrofit
 * for making network calls to authenticate the user.
 *
 * @constructor Creates an instance of LoginActivity.
 */
class LoginActivity : AppCompatActivity() {

    // Lazily initialize the email and password TextInputLayout
    private val email: TextInputLayout by lazy {
        findViewById(R.id.login_email_til)
    }
    private val password: TextInputLayout by lazy {
        findViewById(R.id.login_password_til)
    }

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down, then this Bundle contains the data it most
     *     recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set shared element transition for a smooth transition animation
        val bounds = ChangeBounds().setDuration(1000)
        window.sharedElementEnterTransition = bounds

        // Initialize the UserApi using ApiHelper
        val apiHelper = ApiHelper().getInstance().create(UserApi::class.java)

        val logo = findViewById<ImageView>(R.id.login_logo_iv)
        val name = findViewById<TextView>(R.id.login_welcome_tv)
        val desc = findViewById<TextView>(R.id.login_desc_tv)
        val btnLogin = findViewById<TextView>(R.id.login_login_btn)
        val btnSignUp = findViewById<Button>(R.id.login_register_btn)

        // Set a click listener for the SignUp button
        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            // Define shared element transitions for the animation
            val options = ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity,
                Pair.create(logo, "logo_tn"),
                Pair.create(name, "name_tn"),
                Pair.create(desc, "desc_tn"),
                Pair.create(email, "email_tn"),
                Pair.create(password, "password_tn"),
                Pair.create(btnLogin, "button_tn"),
                Pair.create(btnSignUp, "signin_signup_tn"))

            // Start the RegisterActivity with the defined options
            startActivity(intent, options.toBundle())
        }

        // Find the Login button and set a click listener
        btnLogin.setOnClickListener {
            val email = "teste69@gmail.com"//email.editText!!.text.toString()
            val password = "test69" //password.editText!!.text.toString()

            // launching a new coroutine
            GlobalScope.launch {
                // Make a network call using Retrofit to authenticate the user
                val result = apiHelper.getUser(AuthRequest(email, password))

                result.enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            // If the authentication is successful, navigate to the MainActivity
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("token", response.body()?.token)
                            intent.putExtra("userID", response.body()?.userID)
                            intent.putExtra("email", email)
                            intent.putExtra("password", password)
                            startActivity(intent)
                        } else {
                            // Log an error message if the API call is unsuccessful
                            Log.e("LoginActivity", "API call unsuccessful. Code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        // Log an error message if the API call fails
                        Log.e("LoginActivity", "API call failed", t)
                    }
                })
            }
        }
    }

    /**
     * Deprecated override to finish the activity on back press.
     *
     * @suppress("DEPRECATION")
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}
