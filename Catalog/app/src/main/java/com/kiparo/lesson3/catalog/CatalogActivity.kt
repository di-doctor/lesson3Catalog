package com.kiparo.lesson3.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiparo.lesson2.R
import com.kiparo.lesson2.databinding.ActivityCatalogBinding
import com.kiparo.lesson3.catalog.data.DataSource

class CatalogActivity : AppCompatActivity() {

    private val dataSource = DataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSource.generate()

        val adapter = CatalogAdapter(productRemoveListener = {

        })
        adapter.items = dataSource.getData()

        binding.catalogRecyclerView.adapter = adapter
        binding.catalogRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.catalogRecyclerView.addItemDecoration(
            SpaceDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.padding_default
                )
            )
        )
    }
}