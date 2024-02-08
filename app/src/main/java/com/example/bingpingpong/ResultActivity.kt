package com.example.bingpingpong

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val result = intent.getSerializableExtra("RESULT") as PingResult
         println("Props is $result")
        renderUI(result)
        val backButton = findViewById<Button>(R.id.backResultButton)
        val mainMenuButton = findViewById<Button>(R.id.mainMenubutton)
        mainMenuButton.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        backButton.setOnClickListener {
            val inputIntent = Intent(this, InputIPActivity::class.java)
            startActivity(inputIntent)
        }

    }

    private fun renderUI(output: PingResult) {
        val headingText = findViewById<TextView>(R.id.resultTitle)
        headingText.text = "Ping Results for: ${output.ipAddress.toString()}"
        val resultText = findViewById<TextView>(R.id.pingResultTexts)
        resultText.text = output.result.toString()
    }
}