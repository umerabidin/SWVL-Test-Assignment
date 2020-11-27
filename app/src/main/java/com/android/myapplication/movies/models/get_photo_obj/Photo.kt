package com.android.myapplication.movies.models.get_photo_obj


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("farm")
    val farm: Int, // 66
    @SerializedName("id")
    val id: String, // 50527677461
    @SerializedName("isfamily")
    val isfamily: Int, // 0
    @SerializedName("isfriend")
    val isfriend: Int, // 0
    @SerializedName("ispublic")
    val ispublic: Int, // 1
    @SerializedName("owner")
    val owner: String, // 80806509@N04
    @SerializedName("secret")
    val secret: String, // 4eddb2cc51
    @SerializedName("server")
    val server: String, // 65535
    @SerializedName("title")
    val title: String // BBC: 100 greatest films of the 21st Century (2000-2020)
)