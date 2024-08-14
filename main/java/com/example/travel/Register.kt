package com.example.travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        init()

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Find view references
        usernameEditText = findViewById(R.id.edituser)
        emailEditText = findViewById(R.id.editemail)
        passwordEditText = findViewById(R.id.editpassword)
        signUpButton = findViewById(R.id.sign)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate input fields
            if (validateInput(username, email, password)) {
                createUserAccount(username, email, password)
            }
        }
    }

    private fun init() {
        // Initialize Firebase
        Firebase.initialize(context = this)
        // Initialize Firebase App Check
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        if (username.isEmpty()) {
            showToast("Please enter your username")
            return false
        }

        if (email.isEmpty()) {
            showToast("Please enter your email address")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address")
            return false
        }

        if (password.isEmpty()) {
            showToast("Please enter a password")
            return false
        }

        if (password.length < 6) {
            showToast("Password must be at least 6 characters long")
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createUserAccount(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Account creation successful
                    val user = auth.currentUser!!
                    val userId = user.uid
                    val userData = hashMapOf(
                        "username" to username,
                        "email" to email
                    )

                    // Add user data to Firestore (users collection)
                    db.collection("users").document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            showToast("Account created successfully!")
                            val intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error adding user to Firestore", exception)
                            showToast("Failed to create account!")
                        }
                } else {
                    // Account creation failed
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                    showToast("Authentication failed.")
                }
            }
    }

    companion object {
        private val TAG = "Register"
    }
}
