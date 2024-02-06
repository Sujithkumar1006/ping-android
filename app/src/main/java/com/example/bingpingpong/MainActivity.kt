package com.example.bingpingpong

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pingButton = findViewById<Button>(R.id.buttonPing)
        pingButton.setOnClickListener {
            val intent = Intent(this, InputIPActivity::class.java)
            startActivity(intent)
        }


    }
}