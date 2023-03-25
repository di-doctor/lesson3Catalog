package com.kiparo.lesson3.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.kiparo.lesson2.R
import com.kiparo.lesson3.catalog.model.Catalog
import com.kiparo.lesson3.catalog.model.Product

private val TAG = "CatalogAdapter"

class CatalogAdapter(
    private val productRemoveListener: (product: Product) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = emptyList<Catalog>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalog_product, parent, false)

        Log.d(TAG, "onCreateViewHolder")

        return ProductViewHolder(productView, productRemoveListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val productViewHolder = holder as ProductViewHolder
        val product = items[position] as Product
        productViewHolder.bind(product)

        Log.d(TAG, "onBindViewHolder $position")
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
}