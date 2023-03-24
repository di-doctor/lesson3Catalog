package com.kiparo.lesson3.catalog.model

data class Advertisement(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
) :
    Catalog {
    override fun id() = id
}