package com.kiparo.stack

import android.app.ActivityManager
import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kiparo.playbackservice.databinding.ActivityMenuBinding

private val TAG = "MenuActivity"
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding){
            btnStandard.setOnClickListener {
                val stdIntent = Intent(this@MenuActivity, SecondActivity::class.java)
                startActivity(stdIntent)
            }

            btnNewTask.setOnClickListener {
                val newTaskIntent = Intent(this@MenuActivity, SecondActivity::class.java)
                // do not forget to add android:taskAffinity="com.premiumitclub.secondTask"
                // otherwise this flag will not work
                // after this you can watch a new task in task bar
                newTaskIntent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(newTaskIntent)
                //Use cases
                /**
                * Sharing data: If an application needs to share data with another application,
                * the application can start an activity in a new task. This will help ensure
                * that the data is not mixed up with the previous data in the task.

                * Multi-window support: If an application supports multi-window mode,
                * the application can start an activity in a new task when the user
                * opens the application in a separate window. This will help ensure
                    that the activity is independent of the activity in the previous window.
                 */
            }

            btnSingleTop.setOnClickListener {
                // Do not forget to check the clear top in second activity
                val sglTopIntent = Intent(this@MenuActivity, MenuActivity::class.java)
                sglTopIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(sglTopIntent)
            }

            btnSingleInstance.setOnClickListener {
                val sglInstanceIntent = Intent(this@MenuActivity, MenuActivity::class.java)
                sglInstanceIntent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_SINGLE_TOP
                //if we add Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS then it is how permissions from settings are called
                startActivity(sglInstanceIntent)
            }

            btnSingleInstancePerTask.setOnClickListener {
                // always root of the new task
                val sglInstanceIntent = Intent(this@MenuActivity, SecondActivity::class.java)
                sglInstanceIntent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_MULTIPLE_TASK or FLAG_ACTIVITY_NEW_DOCUMENT
                startActivity(sglInstanceIntent)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "ON NEW INTENT")
    }

    override fun onResume() {
        super.onResume()
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appTasks = activityManager.appTasks
        Log.d(TAG, "ON RESUME")
        Log.d(TAG, "number of tasks: ${appTasks.size}\n")
        appTasks.forEach{
            Log.d(TAG,
                "number of activities: ${it?.taskInfo?.numActivities.toString()}\n" +
                        "top activity ${it?.taskInfo?.topActivity}\n" +
                        "base activity ${it?.taskInfo?.baseActivity}")
        }
    }
}