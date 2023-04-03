package com.kiparo.gallery.edit

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kiparo.gallery.databinding.FragmentItemBinding

import com.kiparo.gallery.edit.placeholder.PlaceholderContent.SomeItem

class ItemsRecyclerViewAdapter(
    private val values: MutableList<SomeItem>,
    private val onClickListener: (SomeItem) -> Unit
) : RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item, onClickListener)
    }

    override fun getItemCount(): Int = values.size

    fun updateItem(item: SomeItem?) {
        item?.let {
            val index = values.indexOfFirst { it.id == item.id }
            if (index != -1) {
                values[index] = item
                notifyItemChanged(index)
            }
        }
    }

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val rootView: View = binding.root

        init {
            binding.root.setOnClickListener {
                onClickListener(values[adapterPosition])
            }
        }

        fun bind(item: SomeItem, onClickListener: (SomeItem) -> Unit) {
            idView.text = item.id
            contentView.text = item.content
            rootView.setOnClickListener {
                onClickListener(item)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}