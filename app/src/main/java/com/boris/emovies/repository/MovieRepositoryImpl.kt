package com.boris.emovies.repository

import com.boris.emovies.data.model.MovieList
import com.boris.emovies.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource) : MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList {
        return dataSource.getUpcomingMovies()
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return dataSource.getTopRatedMovies()
    }
}