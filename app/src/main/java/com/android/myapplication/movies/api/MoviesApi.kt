package com.android.myapplication.movies.api

import androidx.lifecycle.LiveData
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.models.get_photo_obj.GetPhotoRS

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {



    @GET("?method=flickr.photos.search")
    fun getMovieDetail(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String? = "a04930c38aed578c9fde8ecca13036c5",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("per_page") per_page: Int = 10
    ): LiveData<ApiResponse<GetPhotoRS>>

/*format=json&nojsoncallback=1&text="(500) Days of Summer"&page=1&per_page=10*/
}