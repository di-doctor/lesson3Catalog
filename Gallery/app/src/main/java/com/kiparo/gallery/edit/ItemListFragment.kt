package com.kiparo.gallery.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiparo.gallery.R
import com.kiparo.gallery.edit.EditFragment.Companion.ARG_EDIT_PARAM
import com.kiparo.gallery.edit.placeholder.PlaceholderContent


class ItemListFragment : Fragment() {

    private var _columnCount = 1
    private lateinit var _adapter: ItemsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            _columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        setFragmentResultListener(EDIT_REQUEST_KEY) { requestKey, result ->
            val item =
                result.getParcelable<PlaceholderContent.SomeItem>(ARG_EDIT_PARAM)
            _adapter.updateItem(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            _adapter = ItemsRecyclerViewAdapter(PlaceholderContent.ITEMS,
                onClickListener = {
                    (requireActivity() as Navigator).navigateToEdit(it)
            })
            with(view) {
                layoutManager = when {
                    _columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, _columnCount)
                }
                adapter = _adapter
            }
        }
        return view
    }

    companion object {
        const val EDIT_REQUEST_KEY = "editItemRequest"
        private const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}