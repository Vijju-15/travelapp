package com.example.travel

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var usernameEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var registerButton: Button
    lateinit var animationDrawable: AnimationDrawable
    lateinit var backgroundImage: ImageView
    lateinit var auth: FirebaseAuth
    lateinit var explicit: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        loginButton = findViewById(R.id.login)
        usernameEditText = findViewById(R.id.editText)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        registerButton = findViewById(R.id.reg)
        backgroundImage = findViewById(R.id.background_image)
        animationDrawable = resources.getDrawable(R.drawable.background_change) as AnimationDrawable
        backgroundImage.setImageDrawable(animationDrawable)
        animationDrawable.start()

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            login()
        }
        registerButton.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = usernameEditText.text.toString().trim() // Trim leading/trailing whitespaces
        val password = passwordEditText.text.toString().trim()

        // Check for empty fields and password length
        if (email.isEmpty()) {
            usernameEditText.setError("Please enter your email address")
            usernameEditText.requestFocus()
            return
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter your password")
            passwordEditText.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            usernameEditText.setError("Please enter valid email address")
            usernameEditText.requestFocus()
        }
        if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {

                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()

                // Send email (username) to Home activity
                val intent = Intent(applicationContext, Home::class.java)
                intent.putExtra("email", email) // Pass email as login detail
                startActivity(intent)
            } else {
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




