package com.kiparo.gallery.edit

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.kiparo.gallery.R


class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun navigateToItemsList() {
        supportFragmentManager.fragments
            .find { it is ContainerFragment }
            ?.run { this as ContainerFragment }
            ?.run {
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcv_edit_container, ItemListFragment.newInstance(1))
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
    }

    override fun navigateToEdit(item: Parcelable) {
        supportFragmentManager.fragments
            .find { it is ContainerFragment }
            ?.run { this as ContainerFragment }
            ?.run {
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcv_edit_container, EditFragment.newInstance(item = item))
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
    }
}