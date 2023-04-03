package com.kiparo.gallery.bottomnav.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kiparo.gallery.R
import com.kiparo.gallery.databinding.FragmentNotificationsBinding

private const val TAG = "NotificationsFragment"
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_notifications)
        binding.textNotifications.text = getString(R.string.title_notifications)
        root.tag = "NotificationsFragment"
        return root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().supportFragmentManager.findFragmentById(R.id.main_container)?.let {
            it.childFragmentManager.run {
                Log.i(TAG, "Notifications: Total fragments: " + this.backStackEntryCount)

                for (entry in 0 until this.backStackEntryCount) {
                    Log.i(
                        TAG,
                        "Notifications: Found fragment id: " + this.getBackStackEntryAt(entry).id
                    )
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NotificationsFragment()
    }
}