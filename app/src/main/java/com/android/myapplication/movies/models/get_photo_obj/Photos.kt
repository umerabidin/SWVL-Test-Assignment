package com.android.myapplication.movies.models.get_photo_obj


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("page")
    val page: Int = 0, // 1
    @SerializedName("pages")
    val pages: Int = 0, // 445
    @SerializedName("perpage")
    val perpage: Int = 0, // 10
    @SerializedName("photo")
    val photo: List<Photo>? = null,
    @SerializedName("total")
    val total: String? = null// 4450
)