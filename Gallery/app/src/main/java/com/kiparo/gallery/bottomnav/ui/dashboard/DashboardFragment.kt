package com.kiparo.gallery.bottomnav.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kiparo.gallery.R
import com.kiparo.gallery.bottomnav.Section
import com.kiparo.gallery.bottomnav.navigator.MainNavigator
import com.kiparo.gallery.bottomnav.ui.details.DetailsFragment
import com.kiparo.gallery.bottomnav.ui.details.Logger
import com.kiparo.gallery.databinding.FragmentDashboardBinding

private const val TAG = "DashboardFragment"

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.d(TAG, "onCreateView")

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.textDashboard.text = getString(R.string.title_dashboard)
        binding.previous.visibility = View.GONE
        val navigator = requireActivity() as MainNavigator
        val source = getString(R.string.title_dashboard)
        binding.next.setOnClickListener {
            navigator
                .subNavigateTo(
                    DetailsFragment.newInstance(
                        previous = true,
                        next = true,
                        id = navigator.generateId(),
                        source = source
                    ),

                    true
                )
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "onViewCreated")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.d(TAG, "onHiddenChanged $hidden")
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume")
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_dashboard)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Logger.d(TAG, "onViewStateRestored")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d(TAG, "onSaveInstanceState")
    }

    override fun onPause() {
        super.onPause()
        Logger.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d(TAG, "onDetach")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardFragment()
    }
}