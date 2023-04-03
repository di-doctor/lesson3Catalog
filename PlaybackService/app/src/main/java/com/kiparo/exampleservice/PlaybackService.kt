package com.kiparo.exampleservice

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.media.session.MediaSession
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.kiparo.playbackservice.R
import com.kiparo.playbackservice.SimpleActivity

class PlaybackService : Service() {

    private val CHANNEL_ID = "MyPlaybackServiceChannel"
    private val NOTIFICATION_ID = 1234
    private val TAG = "PlaybackService"
    private lateinit var mediaSession: MediaSessionCompat

    private lateinit var notificationManager: NotificationManager

    companion object {
        const val ACTION_PLAY = "com.premiumitclub.exampleservice.action.PLAY"
        const val ACTION_PAUSE = "com.premiumitclub.exampleservice.action.PAUSE"
        const val ACTION_STOP = "com.premiumitclub.exampleservice.action.STOP"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        mediaSession = MediaSessionCompat(this, TAG)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        packageManager
        // Create a notification channel for the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "MyForegroundServiceChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")

        val play = NotificationCompat.Action.Builder(
            R.drawable.ic_play,
            null,
            PendingIntent.getBroadcast(this, 0, Intent(ACTION_PLAY), 0)
        ).build()

        val pause = NotificationCompat.Action.Builder(
            R.drawable.ic_pause,
            null,
            PendingIntent.getBroadcast(this, 0, Intent(ACTION_PAUSE), 0)
        ).build()

        val stop = NotificationCompat.Action.Builder(
            R.drawable.ic_stop,
            null,
            PendingIntent.getBroadcast(this, 0, Intent(ACTION_STOP), 0)
        ).build()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Playback Service")
            .setContentText("Running playback service")
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, SimpleActivity::class.java),
                    0
                )
            )
            .addAction(play)
            .addAction(pause)
            .addAction(stop)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.sessionToken))
            .setOnlyAlertOnce(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        // Register a BroadcastReceiver to handle custom broadcast intents
        registerReceiver(notificationControlsReceiver, IntentFilter().apply {
            addAction(ACTION_PLAY)
            addAction(ACTION_PAUSE)
            addAction(ACTION_STOP)
        })

        // Do some work in the foreground service...

        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        unregisterReceiver(notificationControlsReceiver)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val notificationControlsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(context == null) return
            Log.d(TAG, "Received action ${intent?.action}")

            when (intent?.action) {
                ACTION_PLAY -> {
                    // Handle play button click
                    // ...
                    Log.d(TAG, "Action PLAY")
                }
                ACTION_PAUSE -> {
                    // Handle pause button click
                    // ...
                    Log.d(TAG, "Action PAUSE")
                }
                ACTION_STOP -> {
                    // Handle stop button click
                    // ...
                    Log.d(TAG, "Action STOP")
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
    }
}
