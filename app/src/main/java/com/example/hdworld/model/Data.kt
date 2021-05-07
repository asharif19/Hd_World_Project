package com.example.hdworld.model

import com.google.gson.annotations.SerializedName


data class PhotoData(
    @SerializedName("likes")
    val Likes: String?,
    @SerializedName("alt_description")
    val Alt_description: String?,

    val user: User?,
    val urls: Urls?
)

data class User(

    val name: String?,
    val location: String?

)

data class Urls(

    val regular: String?
)