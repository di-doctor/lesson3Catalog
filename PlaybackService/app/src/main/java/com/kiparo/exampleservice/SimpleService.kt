package com.kiparo.exampleservice

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.kiparo.playbackservice.R
import com.kiparo.playbackservice.SimpleActivity

internal const val ONGOING_NOTIFICATION_ID = 1
internal const val CHANNEL_ID = "SIMPLE_SERVICE_CHANNEL_ID"
internal const val TAG = "SimpleService"

class SimpleService : Service() {
    private var startMode: Int = 0             // indicates how to behave if the service is killed
    private var binder: IBinder? = null        // interface for clients that bind
    private var allowRebind: Boolean = false   //

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        // The service is being created
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // The service is starting, due to a call to startService()
        // If the notification supports a direct reply action, use
        // PendingIntent.FLAG_MUTABLE instead.
        Log.d(TAG, "onStartCommand")

        val pendingIntent: PendingIntent =
            Intent(this, SimpleActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        val notification: Notification = createNotification(pendingIntent)

        // Notification ID cannot be 0.
        startForeground(ONGOING_NOTIFICATION_ID, notification)

        return startMode
    }

    private fun createNotification(pendingIntent: PendingIntent): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.baseline_airplanemode_active_24)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.baseline_airplanemode_active_24)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build()
        }
        return notification
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        // A client is binding to the service with bindService()
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind")

        // All clients have unbound with unbindService()
        return allowRebind
    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind")

        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        // The service is no longer used and is being destroyed
    }

}