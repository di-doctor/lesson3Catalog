package com.kiparo.gallery.edit

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.kiparo.gallery.R
import com.kiparo.gallery.databinding.FragmentEditBinding
import com.kiparo.gallery.edit.ItemListFragment.Companion.EDIT_REQUEST_KEY
import com.kiparo.gallery.edit.placeholder.PlaceholderContent



class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private var item: PlaceholderContent.SomeItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable(ARG_EDIT_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("EditFragment", "onCreateView with parameters: $item")
        binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.tvText.text = String.format("%s, %s",item?.content, item?.details)
        binding.btnCancel.setOnClickListener {
            (requireActivity() as Navigator).navigateToItemsList()
        }
        binding.btnSave.setOnClickListener {
            item?.let {
                val result = PlaceholderContent.SomeItem(it.id, "changed for ${it.id}", it.details)
                setFragmentResult(EDIT_REQUEST_KEY, bundleOf(ARG_EDIT_PARAM to result))
                (requireActivity() as Navigator).navigateToItemsList()
            }
        }
        return binding.root
    }

    companion object {
        const val ARG_EDIT_PARAM = "EDIT_PARAM"
        @JvmStatic
        fun newInstance(item: Parcelable) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_EDIT_PARAM, item)
                }
            }
    }
}