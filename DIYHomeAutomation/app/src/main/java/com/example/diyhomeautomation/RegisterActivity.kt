package com.example.diyhomeautomation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diyhomeautomation.api.ApiHelper
import com.example.diyhomeautomation.api.UserApi
import com.example.diyhomeautomation.models.AuthResponse
import com.example.diyhomeautomation.models.RegistrationRequest
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * RegisterActivity handles user registration and serves as the entry point for user sign-up.
 *
 * This activity uses Retrofit for making network calls to register a new user.
 *
 * @constructor Creates an instance of RegisterActivity.
 */
class RegisterActivity : AppCompatActivity() {

    // Lazily initialize the name, email, password, and username TextInputLayout
    private val name: TextInputLayout by lazy {
        findViewById(R.id.register_name_til)
    }
    private val email: TextInputLayout by lazy {
        findViewById(R.id.register_email_til)
    }
    private val password: TextInputLayout by lazy {
        findViewById(R.id.register_password_til)
    }
    private val username: TextInputLayout by lazy{
        findViewById(R.id.register_username_til)
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
        setContentView(R.layout.activity_register)

        // Initialize the UserApi using ApiHelper
        val apiHelper = ApiHelper().getInstance().create(UserApi::class.java)

        val btn = findViewById<TextView>(R.id.register_register_btn)
        btn.setOnClickListener {
            val name = "test1" //name.editText!!.text.toString()
            val email = "test1@gmail.com" //email.editText!!.text.toString()
            val username = "testname" //username.editText!!.text.toString()
            val pass = "test1_pass" //password.editText!!.text.toString()

            // launching a new coroutine
            GlobalScope.launch {
                // Make a network call using Retrofit to register a new user
                val result = apiHelper.postUser(RegistrationRequest(name, email, username, pass))

                result.enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            // If registration is successful, navigate to the MainActivity
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            intent.putExtra("token", response.body()?.token)
                            intent.putExtra("userID", response.body()?.userID)
                            startActivity(intent)
                        } else {
                            // Log an error message if the API call is unsuccessful
                            Log.e("RegisterActivity", "API call unsuccessful. Code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        // Log an error message if the API call fails
                        Log.e("RegisterActivity", "API call failed", t)
                    }
                })
            }
        }

        // Find the Login button and set a click listener to go back to the login screen
        val btnLogin = findViewById<Button>(R.id.register_login_btn)
        btnLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}