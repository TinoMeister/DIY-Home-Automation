package com.example.diyhomeautomation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val email: TextInputLayout by lazy {
        findViewById(R.id.login_email_til)
    }
    val password: TextInputLayout by lazy {
        findViewById(R.id.login_password_til)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val bounds = ChangeBounds().setDuration(1000)
        window.sharedElementEnterTransition = bounds

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
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

    fun doLogin(v: View) {
        val email = email.editText!!.text.toString()
        val password = password.editText!!.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}