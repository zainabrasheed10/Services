package com.example.constraintlayout

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var receiver: AirplaneModeChangedReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Broadcast Receivers
        receiver = AirplaneModeChangedReceiver()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }

        //Intent Services and Services
        val stopButton = findViewById<Button>(R.id.btnStop)
        val startButton = findViewById<Button>(R.id.btnStart)
        val serviceTv = findViewById<TextView>(R.id.tvService)
        val sendData = findViewById<Button>(R.id.btnSend)
        val textData = findViewById<EditText>(R.id.etData)

        startButton.setOnClickListener {
            Intent (this, MyIntentService::class.java) .also {
                startService(it)
                serviceTv.text = "Service Running"
            }
        }

        stopButton.setOnClickListener {
            Intent (this, MyIntentService::class.java).also {
                stopService(it)
                serviceTv.text = "Service Stopped"
            }
        }

        sendData.setOnClickListener{
            Intent (this, MyService::class.java).also {
                val dataString = textData.text.toString()
                it.putExtra("EXTRA_DATA", dataString)
                startService(it)
            }
        }

        val move = findViewById<Button>(R.id.btnMove)
        move.setOnClickListener{
            Intent(this, MainActivity2::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}