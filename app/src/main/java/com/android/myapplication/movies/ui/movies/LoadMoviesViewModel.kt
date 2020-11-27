package com.android.myapplication.movies.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.myapplication.movies.models.read_movies.Movy
import com.android.myapplication.movies.models.read_movies.ReadMoviesFromFile
import com.google.gson.Gson
import readHospitalJsonFromAssets

class LoadMoviesViewModel(val app: Application) :
    AndroidViewModel(app) {


    fun getMovies(): List<Movy> {

        val movies : ReadMoviesFromFile = Gson().fromJson(app.readHospitalJsonFromAssets(),ReadMoviesFromFile::class.java)
        return movies.movies

    }


}