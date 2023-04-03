package com.kiparo.gallery.bottomnav.ui.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kiparo.gallery.bottomnav.navigator.MainNavigator
import com.kiparo.gallery.databinding.FragmentDetailsBinding

private const val NEXT_MESSAGE = "%s Details description %d"
private const val NEXT_TITLE = "%s Details title %d"
private const val TAG = "DetailsFragment"
private const val IMAGE_URL = "https://picsum.photos/200/300?grayscale&random=%s"

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var message: String? = null
    private var previous: Boolean? = null
    private var next: Boolean? = null
    private var title: String? = null
    private var fragmentId: String? = null
    private var source: String? = null
    private var imgUrl: String? = null
    private lateinit var lifecycleObserver: GalleryLifecycleObserver

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d(TAG, "====================")
        Logger.d(TAG, "onAttach $fragmentId")
        lifecycleObserver = GalleryLifecycleObserver(this, TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, "onCreate $fragmentId")
        arguments?.let {
            message = it.getString(ARG_MESSAGE)
            previous = it.getBoolean(ARG_PREVIOUS)
            next = it.getBoolean(ARG_NEXT)
            title = it.getString(ARG_TITLE)
            fragmentId = it.getString(ARG_ID)
            source = it.getString(ARG_SOURCE)
        }
        imgUrl = String.format(IMAGE_URL, fragmentId)
    }

    //implement this method
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.d(TAG, "onCreateView $fragmentId")
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navigator = requireActivity() as MainNavigator
        title = String.format(NEXT_TITLE, source, navigator.stackSize())
        binding.tvDetails.text = String.format(NEXT_MESSAGE, source, navigator.stackSize())
        binding.previous.visibility = if (previous == true) View.VISIBLE else View.GONE
        binding.next.visibility = if (next == true) View.VISIBLE else View.GONE
        binding.previous.setOnClickListener {
            navigator.back()
        }
        binding.next.setOnClickListener {
            navigator
                .subNavigateTo(
                    newInstance(
                        previous = true,
                        next = true,
                        id = navigator.generateId(),
                        source = source!!
                    ),
                    true
                )
        }
        val circularProgress = CircularProgressDrawable(binding.ivPhoto.context)
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()

        Glide.with(this)
            .load(imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .fitCenter()
            .placeholder(circularProgress)
            .into(binding.ivPhoto)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(TAG, "onViewCreated $fragmentId")
    }

    override fun onStart() {
        super.onStart()
        Logger.d(TAG, "onStart $fragmentId")
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.d(TAG, "onHiddenChanged $fragmentId")
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, "onResume $fragmentId")
    }

    override fun onPause() {
        super.onPause()
        Logger.d(TAG, "onPause $fragmentId")
    }

    override fun onStop() {
        super.onStop()
        Logger.d(TAG, "onStop  $fragmentId")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d(TAG, "onDetach $fragmentId")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(TAG, "onDestroyView $fragmentId")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(TAG, "onDestroy $fragmentId")
    }

    companion object {
        private const val ARG_MESSAGE = "message_param"
        private const val ARG_PREVIOUS = "previous_param"
        private const val ARG_NEXT = "next_param"
        private const val ARG_TITLE = "title_param"
        private const val ARG_ID = "number_param"
        private const val ARG_SOURCE = "source_param"

        @JvmStatic
        fun newInstance(
            previous: Boolean,
            next: Boolean,
            id: String,
            source: String
        ) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PREVIOUS, previous)
                    putBoolean(ARG_NEXT, next)
                    putString(ARG_ID, id)
                    putString(ARG_SOURCE, source)
                }
                fragmentId = id
            }
    }
}

class GalleryLifecycleObserver(owner: LifecycleOwner, private val tag: String) : LifecycleEventObserver {
    init {
        owner.lifecycle.addObserver(this)
    }
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//        Log.d(tag, "onStateChanged $tag $event")
    }
}

object Logger {
    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}

