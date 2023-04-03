package com.kiparo.gallery.edit

import android.os.Parcelable

interface Navigator {
    fun navigateToItemsList()

    fun navigateToEdit(item: Parcelable)
}