package com.android.myapplication.movies.repository

import androidx.lifecycle.LiveData
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.api.responses.ApiEmptyResponse
import com.android.myapplication.movies.api.responses.ApiErrorResponse
import com.android.myapplication.movies.api.responses.ApiResponse
import com.android.myapplication.movies.api.responses.ApiSuccessResponse
import com.android.myapplication.movies.models.get_photo_obj.GetPhotoRS
import com.android.myapplication.movies.models.get_photo_obj.Photos
import com.android.myapplication.movies.util.NetworkBoundResourceNoCaching
import com.android.myapplication.movies.util.Resource

class MovieDetailRepository(
    private val movieApi: MoviesApi
) {

    fun getMovieDetail(title: String, pageNumber: Int): LiveData<Resource<Photos>> {
        return object : NetworkBoundResourceNoCaching<Photos, GetPhotoRS>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<GetPhotoRS>) {
                val detailsResponse = response.body
                val page = detailsResponse.photos.page
                val pages = detailsResponse.photos.pages
                val perpage = detailsResponse.photos.perpage
                val photo = detailsResponse.photos.photo
                val total = detailsResponse.photos.total
                result.value = Resource.Success(Photos(page, pages, perpage, photo, total))
            }

            override fun handleApiEmptyResponse(response: ApiEmptyResponse<GetPhotoRS>) {
                //MovieDetails,has empty videos,casts,reviews
                result.value = Resource.Success(Photos())
            }

            override fun handleApiErrorResponse(response: ApiErrorResponse<GetPhotoRS>) {
                result.value = Resource.Error(response.errorMessage, null)
            }

            override fun createCall(): LiveData<ApiResponse<GetPhotoRS>> {






                return movieApi.getMovieDetail(title, pageNumber)

            }

        }.asLiveData()
    }
}