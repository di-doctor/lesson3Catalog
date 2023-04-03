package com.kiparo.gallery.simple

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kiparo.gallery.R
import com.kiparo.gallery.bottomnav.ui.details.Logger
import com.kiparo.gallery.databinding.FragmentTestBinding

private const val TAG = "TestFragment"

class TestFragment : Fragment() {

    lateinit var _binding: FragmentTestBinding
    private var _id: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d(TAG, "onAttach $_id")
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        Logger.d(TAG, "onConfigurationChanged ")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this is the place for recovering state
        with(arguments) {
            _id = this?.getString(ARGS_ID)
        }
        Logger.d(TAG, "onCreate $_id")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.d(TAG, "onCreateView $_id")
        _binding = FragmentTestBinding.inflate(layoutInflater, container, false)
        _binding.tvValFragmentsTotal.text =
            (activity as ContainerActivity).fragmentsCount().toString()
        _binding.tvValBackstackTotal.text =
            (activity as ContainerActivity).backStackSize().toString()
        _binding.tvTest.text = _binding.tvTest.text.toString() + " " + _id
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "onViewCreated  $_id")
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart $_id")
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume $_id")
    }

    override fun onPause() {
        super.onPause()
        Logger.d(TAG, "onPause $_id")
    }

    override fun onStop() {
        super.onStop()
        Logger.d(TAG, "onStop $_id")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(TAG, "onDestroyView $_id")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(TAG, "onDestroy $_id")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d(TAG, "onDetach $_id")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d(TAG, "onSaveInstanceState $_id")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Logger.d(TAG, "onViewStateRestored $_id")
    }
//
//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        Logger.d(TAG, "onHiddenChanged $_id")
//    }

    companion object {

        private const val ARGS_ID = "args_id"

        @JvmStatic
        fun newInstance(
            id: String
        ) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_ID, id)
                }
            }
    }


}
