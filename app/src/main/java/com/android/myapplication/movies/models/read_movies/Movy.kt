package com.android.myapplication.movies.models.read_movies


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class Movy(
    @SerializedName("cast")
    val cast: ArrayList<String>?,
    @SerializedName("genres")
    val genres: ArrayList<String>?,
    @SerializedName("rating")
    val rating: Int, // 1
    @SerializedName("title")
    val title: String?, // (500) Days of Summer
    @SerializedName("year")
    val year: Int // 2009
)