package com.android.myapplication.movies.ui.detail.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.myapplication.movies.models.get_photo_obj.Photos
import com.android.myapplication.movies.models.read_movies.Movy
import com.android.myapplication.movies.repository.MovieDetailRepository
import com.android.myapplication.movies.util.Resource

class DetailFragmentViewModel(app: Application, private val repository: MovieDetailRepository, val movie: Movy) : AndroidViewModel(app) {


    private val _movieDetails = MediatorLiveData<Resource<Photos>>()
    val movieDetails: LiveData<Resource<Photos>>
        get() = _movieDetails


    private var cancelRequest = false
    private var isPerformingQuery: Boolean = false

    fun getMovieDetails(page: Int) {
        if (!isPerformingQuery) {
            isPerformingQuery = true
            executeRequest(page)
        }
    }

    private fun executeRequest(page: Int) {
        val repositorySource = repository.getMovieDetail(movie.title!!, page)
        registerMediatorLiveData(repositorySource)
    }

    fun registerMediatorLiveData(repositorySource: LiveData<Resource<Photos>>) {
        _movieDetails.addSource(repositorySource) { resourceMovieDetails ->
            if (!cancelRequest) {
                if (resourceMovieDetails != null) {
                    _movieDetails.value = resourceMovieDetails
                    if (resourceMovieDetails is Resource.Success || resourceMovieDetails is Resource.Error) {
                        //if response is reached error or success (no more loading)
                        unregisterMediatorLiveData(repositorySource)
                    }
                } else {
                    //if response is null
                    unregisterMediatorLiveData(repositorySource)
                }
            } else {
                //if request is canceled
                unregisterMediatorLiveData(repositorySource)
            }
        }
    }

    //unregister when whole response is null or when response ==Success or Error
    private fun unregisterMediatorLiveData(repositorySource: LiveData<Resource<Photos>>) {
        isPerformingQuery = false
        _movieDetails.removeSource(repositorySource)
    }


    fun cancelRequest() {
        cancelRequest = true
    }
}