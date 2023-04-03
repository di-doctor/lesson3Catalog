package com.kiparo.stack

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kiparo.playbackservice.databinding.ActivitySecondBinding

private const val TAG = "SecondActivity"

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAction1.setOnClickListener {
            val intent = Intent(this@SecondActivity, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.btnAction2.setOnClickListener {
            val intent = Intent(this@SecondActivity, MenuActivity::class.java)
            // old activity with new intent
            // clear until this one
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
            startActivity(intent)
        }

        binding.btnAction3.setOnClickListener {
            val intent = Intent(this@SecondActivity, SecondActivity::class.java)
            // old activity with new intent
            // clear until this one
            // trying to start same activity in a new instance will remain the same if this one is
            // started as a single instance
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "ON NEW INTENT")
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks
        Log.d(TAG, "number of tasks: ${appTasks.size}\n")
        appTasks.forEach{
            Log.d(TAG,
                        "number of activities: ${it?.taskInfo?.numActivities.toString()}\n" +
                        "top activity ${it?.taskInfo?.topActivity}\n " +
                "base activity ${it?.taskInfo?.baseActivity}\n")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ON RESUME")
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks
        Log.d(TAG, "ON CREATE")
        Log.d(TAG, "number of tasks: ${appTasks.size}\n")
        appTasks.forEach{
            Log.d(TAG,
                "number of activities: ${it?.taskInfo?.numActivities.toString()}\n" +
                        "top activity ${it?.taskInfo?.topActivity}\n" +
                        "base activity ${it?.taskInfo?.baseActivity}\n")
        }
    }
}