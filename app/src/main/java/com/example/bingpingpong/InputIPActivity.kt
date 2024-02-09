package com.example.bingpingpong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.Serializable


data class PingResult(val status: Boolean, val result: StringBuilder, val ipAddress: String): Serializable


class InputIPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_ipactivity)

        val backButton = findViewById<Button>(R.id.buttonPingBack)
        backButton.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        val submitButton = findViewById<Button>(R.id.buttonPingClick)
        submitButton.setOnClickListener {
            submitButton.isClickable = false
            onPingSubmit()
        }

    }

    private fun onPingSubmit() {
        val ipAddress = findViewById<EditText>(R.id.inputIP)
        // Todo: Check Valid IP or Not
        if (ipAddress.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter an IP Address to Ping", Toast.LENGTH_LONG).show()
            return
        }
        // Start a coroutine in the main thread
        val spinner = findViewById<ProgressBar>(R.id.progressBar)
        spinner.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            // Switch to the background thread
            val result = withContext(Dispatchers.IO) {
                pingIpAddress(ipAddress.text.toString())
            }
            println("Result is $result")
            spinner.visibility = View.GONE
            onTaskCompleted(result)
        }
    }

    private fun onTaskCompleted(result: PingResult) {
        val resultIntent = Intent(this, ResultActivity::class.java)
        resultIntent.putExtra("RESULT", result)
        startActivity(resultIntent)
    }


    private fun pingIpAddress(ipAddress: String): PingResult {
        return try {
            val command = "ping -c 5 $ipAddress"
            println("Command is $command")
            val execProcess = Runtime.getRuntime().exec(command)
            val streamInput = execProcess.inputStream
            val bufferedReader = streamInput.bufferedReader()
            val response = StringBuilder()
            var endLine: String?
            while (bufferedReader.readLine().also { endLine = it } != null) {
                response.append(endLine).append("\n")
            }
            val message = execProcess.waitFor()
            println("Message is $message")
            println("Result is ${response.capacity()}")
            return PingResult(true,  response, ipAddress)
        } catch (e: Exception) {
            PingResult(false, StringBuilder(e.message.toString()), ipAddress)
        }
    }

}