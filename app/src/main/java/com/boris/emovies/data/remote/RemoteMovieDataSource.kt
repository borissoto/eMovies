package com.boris.emovies.data.remote

import com.boris.emovies.data.model.MovieList
import com.boris.emovies.repository.WebService
import com.boris.emovies.utils.AppConstants

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getTopRatedMovies(): MovieList {
        return webService.getTopRatedMovies(AppConstants.API_KEY)
    }
}