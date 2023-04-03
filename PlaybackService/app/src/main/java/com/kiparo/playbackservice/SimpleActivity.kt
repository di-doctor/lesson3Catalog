package com.kiparo.playbackservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kiparo.exampleservice.PlaybackService

class SimpleActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        val intent = Intent(this, PlaybackService::class.java)

        findViewById<Button>(R.id.btn_start_service).apply {
            this.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent)
                } else {
                    startService(intent)
                }
            }
        }

        findViewById<Button>(R.id.btn_stop_service).apply {
            this.setOnClickListener {
                stopService(intent)
            }
        }
    }
}