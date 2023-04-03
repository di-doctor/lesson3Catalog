package com.kiparo.gallery.bottomnav.navigator

import androidx.fragment.app.Fragment
import com.kiparo.gallery.bottomnav.Section

interface MainNavigator {
    fun navigateTo(section: Section)
    fun subNavigateTo(fragment: Fragment, fullScreen: Boolean)
    fun back()
    fun stackSize(): Int
    fun generateId(): String
}

interface SubNavigator {
    fun navigateTo(fragment: Fragment)
    fun back()
    fun canGoBack(): Boolean
    fun isFullScreen(): Boolean
    fun totalFragments(): Int
    fun startSection(section: Section)
    fun startSection(fragment: Fragment)
}