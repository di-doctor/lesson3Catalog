package com.kiparo.lesson3.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kiparo.lesson2.R
import com.kiparo.lesson3.catalog.model.Advertisement
import com.kiparo.lesson3.catalog.model.CatalogItem
import com.kiparo.lesson3.catalog.model.Product
import com.kiparo.lesson3.catalog.model.Video

private val TAG = "CatalogAdapter"

class CatalogAdapter(
    private val productRemoveListener: (product: Product) -> Unit,
    private val generateNextVideoListener: (video: Video) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val ADVERTISEMENT_VIEW_TYPE = 1
        const val VIDEO_STREAM_VIEW_TYPE = 2
    }

    var items = emptyList<CatalogItem>()
        set(newItems) {
            val diffUtilCallback = CatalogDiffCallback(oldItems = field, newItems)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = newItems
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ADVERTISEMENT_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_catalog_adv,
                    parent,
                    false
                )
                AdvertisementViewHolder(view)
            }
            VIDEO_STREAM_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_catalog_video,
                    parent,
                    false
                )
                VideoStreamViewHolder(view,
                    generateNextVideoListener = generateNextVideoListener)
            }
            PRODUCT_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_catalog_product,
                    parent,
                    false
                )
                ProductViewHolder(view, removeListener = productRemoveListener)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Advertisement -> ADVERTISEMENT_VIEW_TYPE
            is Product -> PRODUCT_VIEW_TYPE
            is Video -> VIDEO_STREAM_VIEW_TYPE
            else -> throw IllegalArgumentException(
                "Unknown list type"
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ADVERTISEMENT_VIEW_TYPE -> {
                val item = items[position] as Advertisement
                val viewHolder = holder as AdvertisementViewHolder
                viewHolder.bind(item)
            }
            VIDEO_STREAM_VIEW_TYPE -> {
                val item = items[position] as Video
                val viewHolder = holder as VideoStreamViewHolder
                viewHolder.bind(item)
            }
            else -> {
                val item = items[position] as Product
                val viewHolder = holder as ProductViewHolder
                viewHolder.bind(item)
            }
        }
    }

    internal class ProductViewHolder(
        itemView: View,
        private val removeListener: (product: Product) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productTitle: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val productRemoveBtn: ImageButton = itemView.findViewById(R.id.product_remove_btn)

        fun bind(product: Product) {
            productTitle.text = product.title
            productPrice.text = product.price

            val placeHolder = AppCompatResources.getDrawable(
                productImage.context,
                R.drawable.ic_product_placeholder
            )

            val circularProgress = CircularProgressDrawable(productImage.context)
            circularProgress.strokeWidth = 5f
            circularProgress.centerRadius = 30f
            circularProgress.start()

            Glide
                .with(productImage.context)
                .load(product.imageUrl)
                .fitCenter()
                .placeholder(circularProgress)
                .error(placeHolder)
                .into(productImage)


            productRemoveBtn.setOnClickListener { removeListener(product) }
        }
    }


    internal class AdvertisementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val advertisementImage: ImageView = itemView.findViewById(R.id.ad_image)
        private val advertisementTitle: TextView = itemView.findViewById(R.id.ad_title)
        private val advertisementDescription: TextView =
            itemView.findViewById(R.id.ad_description)

        fun bind(advertisement: Advertisement) {
            advertisementDescription.tag
            advertisementTitle.text = advertisement.title
            advertisementDescription.text = advertisement.description
            val placeHolder = AppCompatResources.getDrawable(
                advertisementImage.context,
                R.drawable.ic_adv_placeholder
            )
            val circularProgress = CircularProgressDrawable(advertisementImage.context)
            circularProgress.strokeWidth = 5f
            circularProgress.centerRadius = 30f
            circularProgress.start()

            Glide
                .with(advertisementImage.context)
                .load(advertisement.imageUrl)
                .fitCenter()
                .placeholder(circularProgress)
                .error(placeHolder)
                .into(advertisementImage)

        }
    }

    internal class VideoStreamViewHolder(
        itemView: View,
        private val generateNextVideoListener: (video: Video) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val videoView: ImageView = itemView.findViewById(R.id.video_view)
        private val videoTitle: TextView = itemView.findViewById(R.id.video_title)
        private val videoDescription: TextView = itemView.findViewById(R.id.video_description)
        private val videoCloneButton: ImageButton = itemView.findViewById(R.id.btn_add_next)

        fun bind(video: Video) {
            videoTitle.text = video.title
            videoDescription.text = video.description

            val placeHolder = AppCompatResources.getDrawable(
                videoView.context,
                R.drawable.ic_video_placeholder
            )

            val circularProgress = CircularProgressDrawable(videoView.context)
            circularProgress.strokeWidth = 5f
            circularProgress.centerRadius = 30f
            circularProgress.start()

            Glide
                .with(videoView.context)
                .load(video.videoUri)
                .fitCenter()
                .placeholder(circularProgress)
                .error(placeHolder)
                .into(videoView)

            videoCloneButton.setOnClickListener{
                generateNextVideoListener(video)
            }
        }
    }

    class CatalogDiffCallback(
        private val oldItems: List<CatalogItem>,
        private val newItems: List<CatalogItem>
    ) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id() == newItems[newItemPosition].id()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

    }
}