package com.kiparo.gallery.bottomnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.kiparo.gallery.R
import com.kiparo.gallery.bottomnav.navigator.MainNavigator
import com.kiparo.gallery.bottomnav.navigator.SubNavigator
import com.kiparo.gallery.bottomnav.ui.dashboard.DashboardFragment
import com.kiparo.gallery.bottomnav.ui.details.Logger
import com.kiparo.gallery.bottomnav.ui.home.HomeFragment
import com.kiparo.gallery.bottomnav.ui.notifications.NotificationsFragment
import com.kiparo.gallery.databinding.FragmentMainNavBinding

enum class Section {
    HOME,
    DASHBOARD,
    NOTIFICATIONS,
}

private const val TAG = "NavigatorFragment"

class NavigatorFragment : Fragment(), SubNavigator {

    private var _binding: FragmentMainNavBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d(TAG, "====================")
        Logger.d(TAG, "onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.d(TAG, "onCreateView")
        _binding = FragmentMainNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "onViewCreated")
        (requireActivity() as MainNavigator).navigateTo(Section.HOME)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d(TAG, "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Logger.d(TAG, "onViewStateRestored")
    }


    private fun createTabFragment(section: Section): Fragment = when (section) {
        Section.HOME -> HomeFragment()
        Section.DASHBOARD -> DashboardFragment.newInstance()
        Section.NOTIFICATIONS -> NotificationsFragment.newInstance()
    }

    /****************** SubNavigator section ***************************/
    override fun startSection(section: Section) {
        val currentSectionFragment = createTabFragment(section)
        childFragmentManager.commitNow {
            replace(R.id.main_flow_fragment_container, currentSectionFragment)
        }
    }

    override fun startSection(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.main_flow_fragment_container, fragment)
        }
    }

    override fun navigateTo(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.main_flow_fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun back() {
        (requireActivity() as MainNavigator).back()
    }

    override fun canGoBack(): Boolean {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        }
        return childFragmentManager.backStackEntryCount > 0
    }

    override fun isFullScreen(): Boolean {
        return childFragmentManager.backStackEntryCount > 1
    }

    override fun totalFragments(): Int {
        return childFragmentManager.backStackEntryCount
    }

}