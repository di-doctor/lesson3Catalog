package com.kiparo.lesson3.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiparo.lesson2.databinding.ActivityCatalogBinding
import com.kiparo.lesson3.catalog.data.DataSource

class CatalogActivity : AppCompatActivity() {

    private val dataSource = DataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSource.generate()

        //TODO Implement RecyclerView Adapter
    }
}