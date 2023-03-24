package com.kiparo.lesson3.catalog.model

data class Video(
    val id : String,
    val title: String,
    val description: String,
    val videoUri: String
 ) : Catalog {
    override fun id() = id
}
