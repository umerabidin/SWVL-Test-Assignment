package com.android.myapplication.movies.models.read_movies


import com.google.gson.annotations.SerializedName

data class ReadMoviesFromFile(
    @SerializedName("movies")
    val movies: List<Movy>
)