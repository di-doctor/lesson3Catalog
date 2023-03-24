package com.kiparo.lesson3.catalog.model

data class Product(
    val id : String,
    val title: String,
    val price: String,
    val imageUrl: String
) : Catalog {
    override fun id() = id
}