package com.kiparo.gallery.bottomnav

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kiparo.gallery.R
import com.kiparo.gallery.bottomnav.navigator.MainNavigator
import com.kiparo.gallery.bottomnav.navigator.SubNavigator
import com.kiparo.gallery.bottomnav.ui.details.GalleryLifecycleObserver
import com.kiparo.gallery.bottomnav.ui.details.Logger
import com.kiparo.gallery.databinding.ActivityBottomNavBinding
import java.security.MessageDigest

private const val TAG = "BottomNavActivity"

class BottomNavActivity : AppCompatActivity(), MainNavigator {

    private lateinit var binding: ActivityBottomNavBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var lifecycleObserver: GalleryLifecycleObserver

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Logger.d(TAG, "onAttachedToWindow")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, "onCreate")
        lifecycleObserver = GalleryLifecycleObserver(this, TAG)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.main_container, NavigatorFragment())
            }
        }

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                supportFragmentManager.fragments
                    .find { it is SubNavigator }
                    ?.run { this as SubNavigator }
                    ?.run {
                        if (this.canGoBack()) {
                            if (!this.isFullScreen()) {
                                binding.navView.visibility = View.VISIBLE
                            }
                            return
                        }
                    }
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener(this::onMenuItemSelected)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume")
    }

    override fun onPostResume() {
        super.onPostResume()
        Logger.d(TAG, "onPostResume")
    }


    override fun navigateTo(section: Section) {
        supportFragmentManager.fragments
            .find { it is NavigatorFragment }
            ?.run { this as NavigatorFragment }
            ?.run { startSection(section) }
    }

    override fun subNavigateTo(fragment: Fragment, fullScreen: Boolean) {
        if (fullScreen) {
            binding.navView.visibility = View.GONE
        }
        supportFragmentManager.fragments
            .find { it is SubNavigator }?.run {
                (this as SubNavigator).navigateTo(fragment)
            }
    }

    override fun back() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun stackSize(): Int {
        val subNavigator = supportFragmentManager.fragments
            .find { it is SubNavigator }
            ?.run { this as SubNavigator }
        return subNavigator?.totalFragments() ?: 0
    }

    override fun generateId(): String {
        val md = MessageDigest.getInstance("MD5")
        val hash = md.digest(System.currentTimeMillis().toString().toByteArray())
        return hash.joinToString("") { "%02x".format(it) }.substring(0, 4)
    }

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.navigation_home -> {
                navigateTo(Section.HOME)
                true
            }
            R.id.navigation_dashboard -> {
                navigateTo(Section.DASHBOARD)
                true
            }
            R.id.navigation_notifications -> {
                navigateTo(Section.NOTIFICATIONS)
                true
            }
            else -> {
                throw IllegalStateException("Unknown app section selected")
            }
        }
    }
}
