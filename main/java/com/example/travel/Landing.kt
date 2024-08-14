package com.example.travel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.appwrite.Client


class Landing : AppCompatActivity(){

    lateinit var Explicit : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)
        var next : Button = findViewById(R.id.button5)
        next.setOnClickListener {
            Explicit = Intent(applicationContext,Login::class.java)
            startActivity(Explicit)
        }
    }
}