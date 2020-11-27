package com.android.myapplication.movies.models.get_photo_obj


import com.google.gson.annotations.SerializedName

data class GetPhotoRS(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("stat")
    val stat: String // ok
)